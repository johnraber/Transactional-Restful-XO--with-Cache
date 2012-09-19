package net.johnraber.sxo.controller;

import java.util.HashMap;
import java.util.Map;

// import javax.validation.Valid;


//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestClientException;
import org.springframework.web.servlet.ModelAndView;

import net.johnraber.sxo.model.XOSession;
import net.johnraber.sxo.service.XOService;


import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.ehcache.EhCacheCache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
//import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;

//import net.sf.ehcache.CacheManager;
//import net.sf.ehcache.Cache;
//import net.sf.ehcache.Element;


//import org.springframework.webflow.persistence.JpaFlowExecutionListener;


/**
 * XOSessionController is responsible for handling JSON requests from any 
 * checkout view and interacting with the services. 
 * 
 * @author John Raber
 * 
 */
@Controller
//@Transactional(propagation=Propagation.REQUIRED)
public class XOSessionController {
	
	private static final Logger log = LoggerFactory.getLogger(XOSessionController.class);
	
	
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
	public @ResponseBody XOSession createXOSession(@PathVariable String merchantID)
	{
		XOSession newXOSession = xoService.createXOSession(merchantID);
		
		log.info("Created new XO session: " + newXOSession.getDisplayString() );	
		
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
		return xoService.getXOSession(xoSessionID);
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
		return xoService.getArchivedXOSession(xoSessionID);
	}
	
	
		
	/**
	 * Modifying existing 'conversational' XO session 
	 * @param xoSessionID
	 * @param xoSession
	 * @return XOSession which represents the current state on the server
	 * which may be different than the XOSession passed into the call due
	 * to business rule known by the server-side logic
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	@RequestMapping(value = "/xoSession/{xoSessionID}", method = RequestMethod.PUT)
	public @ResponseBody XOSession updateXOSession(@PathVariable Long xoSessionID, XOSession xoSession) 
	{
		return xoService.updateXOSession(xoSession);
	}
	

	@Transactional(propagation=Propagation.REQUIRED)
	@RequestMapping(value = "/xoSession/{xoSessionID}", method = RequestMethod.POST)
	public @ResponseBody boolean commitXOSession(@PathVariable Long xoSessionID)
	{
		 return new Boolean(
				 xoService.commitXOSession(xoSessionID) );
	}
	
	
}