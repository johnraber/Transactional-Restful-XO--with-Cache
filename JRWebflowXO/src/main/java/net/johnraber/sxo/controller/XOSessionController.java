package net.johnraber.sxo.controller;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.web.servlet.ModelAndView;

import net.johnraber.sxo.model.json.XOSession;
import net.johnraber.sxo.service.XOService;
import net.johnraber.sxo.utility.XOSessionUtility;



/**
 * XOSessionController is responsible for handling JSON requests from any 
 * checkout view and interacting with the services. 
 * 
 * This controller is a first attempt at pragmatic RESTful application.
 * Rather than using a standard action-based application and providing
 * page navigation, I'm only providing CRUD support for a XO session 
 * resource.  NOTE:  The idea between conversation and committed/persisted
 * state is supported to support the naturalness for the nominal XO
 * Use Case.  This is done by creating, modifying, deleting a XO session
 * resource in a conversational cache until such time as to commit it where
 * other biz services will then come into play ( actually shipping,
 * moving of money, ...).
 * 
 * 
 * Note the RESTful methods follow 
 *  GET IS safe and IS idempontent
 *  DELETE is NOT safe but it IS idempontent
 *  PUT is NOT safe but it IS idempontent
 *  POST is NOT safe and it is NOT idempontent
 *  
 * @author John Raber
 * 
 */
@Controller
// If using a stand-alone transacton manager, (Bitronix or Atomikis)
// then start transaction at the controller OR the Spring
// dispatcher servlet registered in the web.xml 
//@Transactional(propagation=Propagation.REQUIRED) 
public class XOSessionController {
	
	private static final Logger log = LoggerFactory.getLogger(XOSessionController.class);
	
//	@Inject
	@Autowired
	private XOService xoService;


	public XOSessionController()
	{
		
	}
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index()
	{
		log.info("serving up the logical xo view");
		
		Map<String, Object> returnModel = new HashMap<String, Object>();		
	    return new ModelAndView("xo", returnModel);
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	@RequestMapping(value = "/merchant/{merchantID}", method = RequestMethod.POST)
	public @ResponseBody XOSession createXOSession(@PathVariable String merchantID,
			HttpServletResponse response)
	{
		XOSession newXOSession = XOSessionUtility.createJsonModel(
			xoService.createXOSession(merchantID) );
		
		log.info("Created new XO session: " + newXOSession.getDisplayString() );	
		
		// Return newly created resource location for XO session 
		response.setHeader("Location","/xoSession/"+ newXOSession.getXosessionId() );
		
		return newXOSession;
	}
	
	
	/**
	 * Checkout session already exists
	 * @param xoSessionID
	 * @return
	 */
	@Transactional(propagation=Propagation.SUPPORTS)
	@RequestMapping(value = "/xoSession/{xoSessionID}", method = RequestMethod.GET)
	public @ResponseBody XOSession getXOSession(
		@PathVariable Long xoSessionID)
	{
		return XOSessionUtility.createJsonModel(
			xoService.getXOSession(xoSessionID) );
	}
	
	/**
	 * Checkout session does NOT exist anymore in conversational state but
	 * rather in persistent state
	 * @param xoSessionID
	 * @return
	 */
	@Transactional(propagation=Propagation.SUPPORTS)
	@RequestMapping(value = "/archivedXoSession/{xoSessionID}", method = RequestMethod.GET)
	public @ResponseBody XOSession getArchivedXOSession(
		@PathVariable Long xoSessionID)
	{
		log.info("Retrieving XO Session: " + xoSessionID + " from persistent storage."  );
		return XOSessionUtility.createJsonModel(
			xoService.getArchivedXOSession(xoSessionID) );
	}
	
	
		
	/**
	 * Modifying existing 'conversational' XO session 
	 *
	 * @param xoSessionID
	 * @param xoSession
	 * @return XOSession which represents the current state on the server
	 * which may be different than the XOSession passed into the call due
	 * to business rule known by the server-side logic.  Note this is highly
	 * unlikely as updateXOSession is only operating on a newly created 
	 * resource in cache that has not be persisted to the DB so no other 
	 * session should likely have a handle to it.
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	@RequestMapping(value = "/xoSession/{xoSessionID}", method = RequestMethod.PUT)
	public @ResponseBody XOSession updateXOSession(@PathVariable Long xoSessionID, @RequestBody XOSession xoSession) 
	{
		log.info("updating XO session: " + xoSession.getDisplayString() );	
		return XOSessionUtility.createJsonModel(
			xoService.updateXOSession( XOSessionUtility.createDomainModel(xoSession) ) );
	}
	

	/**
	 * Haven't figured out a good way to distinguish resource vs resource cached 
	 * in the pathing so using /'<resource>'/id for caller version and /'<resource>Cached'/id 
	 * for server version as the standard for now
	 * @param xoSessionID
	 * @return version of XO session that was persisted
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	@RequestMapping(value = "/xoSessionCached/{xoSessionID}", method = RequestMethod.POST)
	public @ResponseBody XOSession commitXOSession(@PathVariable Long xoSessionID)
	{
		log.info("committing cached XO session with id : " + xoSessionID );	
		 return XOSessionUtility.createJsonModel( xoService.commitXOSession(
				 xoSessionID) );
	}
	
	/**
	 * 
	 * @param xoSessionID
	 * @param xoSession  version of XO session that will be persisted even if a different
	 * version of the same resource exists in cache.  XO session will be purged from 
	 * conversational cache to persisted storage in a more archived state.
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	@RequestMapping(value = "/xoSession/{xoSessionID}", method = RequestMethod.POST)
	public @ResponseBody XOSession commitXOSession(@PathVariable Long xoSessionID, @RequestBody XOSession xoSession,
			HttpServletResponse response)
	{
		log.info("committing XO session with ID: " +  xoSessionID );	
		log.info("committing XO session: " + xoSession.getDisplayString() );	
		if( xoService.commitXOSession( XOSessionUtility.createDomainModel(
				xoSession) ) )
		{
			// Return newly created resource location for XO session 
			response.setHeader("Location","/xoSessionArchived/"+ xoSessionID );
			
			//TODO change return from this controller to Boolean or something else
			return xoSession;
		}
		return null;
	}
	
	
	/**
	 * 
	 * @param xoSessionID
	 * @param xoSession  deletes the XO session that is currently stored
	 * in conversational cache
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	@RequestMapping(value = "/xoSession/{xoSessionID}", method = RequestMethod.DELETE)
	public Boolean deleteXOSession(@PathVariable Long xoSessionID)
	{
		log.info("deleting XO session with ID: " +  xoSessionID );	
		return xoService.deleteXOSession(xoSessionID);
	}
	
	
}