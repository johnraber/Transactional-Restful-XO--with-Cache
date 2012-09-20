package net.johnraber.sxo.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.ehcache.EhCacheCache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import net.johnraber.sxo.model.XOSession;
import net.johnraber.sxo.persistence.XOSessionDao;
import net.johnraber.sxo.utility.XOSessionDataUtility;


@Service
public class XOServiceImpl implements XOService
{
	private static final Logger log = LoggerFactory.getLogger(XOServiceImpl.class);
	
	@Autowired
	@Qualifier("springEhCacheManager")
	private EhCacheCacheManager  cacheMgr;
	
//	@Autowired
//	@Qualifier("xoCache")
	private EhCacheCache   xoCache;
	
	@Autowired
	XOSessionDao xoSessionDao;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED )
	public XOSession createXOSession(String merchantID)
	{
		XOSession xoSession = new XOSession();
		xoSession.setXosessionId( createFauxXOGuid() );
		xoSession.setMerchant(merchantID);
		
		getXoCache().put(xoSession.getXosessionId(), xoSession);
		
		return xoSession;
	}
	
	
	@Transactional(propagation=Propagation.REQUIRED)
	public XOSession getXOSession(Long xoSessionID)
	{
		XOSession xoSession = null;
		
		ValueWrapper cachedXOSession = getXoCache().get(xoSessionID);
		
		if( cachedXOSession != null )
		{
			xoSession = (XOSession) cachedXOSession.get();
			log.info("Retrieving XO Session from cache: " + xoSession.getDisplayString()  );
		}
		
		return xoSession; 
		
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public XOSession getArchivedXOSession(Long xoSessionID)
	{
		//TODO  retrieve dao 
		
		XOSession xoSession = new XOSession();
		xoSession.setXosessionId(xoSessionID);
		return xoSession;
	}
	
	
	
	@Transactional(propagation=Propagation.REQUIRED)
	public XOSession updateXOSession(XOSession xoSession)
	{
		ValueWrapper cachedXOSessionVW = xoCache.get(xoSession.getXosessionId());
		
		if( cachedXOSessionVW != null )
		{
			XOSession cachedXOSession = (XOSession) cachedXOSessionVW.get();
	
			//TODO  if service version is differently from passed in version
			// then either perform a merge based on rules or server wins and
			// client ( aka UI ) needs to adjust

			xoCache.put(xoSession.getXosessionId(), xoSession); // this is JSR107 method
		}
	   
		// this is either merged or what caller sent in
		return xoSession;
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public boolean commitXOSession(Long xoSessionID)
	{
		ValueWrapper cachedXOSessionVW = xoCache.get(xoSessionID);
		
		if( cachedXOSessionVW != null )
		{
			XOSession cachedXOSession = (XOSession) cachedXOSessionVW.get();
		
			return this.commitXOSession(cachedXOSession);
		}
		else 
		{
			log.debug("XO Session: " + xoSessionID + " does NOT exists in cache so can NOT be committed!");
			return false;
		}
	}
	
	
	@Transactional(propagation=Propagation.REQUIRED)
	protected boolean commitXOSession(XOSession xoSession)
	{
		//TODO use canonical/service level POJO 
		// for XOSession and persist into database
		// using the domain model representation
		net.johnraber.sxo.persistence.model.XOSession domainXOSession =
				XOSessionDataUtility.createDomainModel(xoSession);
		
		xoSessionDao.saveXOSession( domainXOSession );
		xoCache.evict( xoSession.getXosessionId() );
		log.debug("Persisting XO Session: " + xoSession + " and removing from cache.");
		
		return true;
	}
	
	private long createFauxXOGuid()
	{
		return (long) 1234567890;
	}
	
	// using get method because am not able to
	// init  when this class is loaded or in
	// the constructor due to some ordering/timing of
	// dependent resources in ecache init
	public EhCacheCache getXoCache()
	{
		if( xoCache == null)
		{
			xoCache = (EhCacheCache) cacheMgr.getCache("xoCache");
		}
		return xoCache;
	}

}
