package net.johnraber.sxo.service;

//import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import net.johnraber.sxo.model.XOSession;


/**
 * Transactional attributes are placed on methods
 * for documentation purposes for the clients that
 * use the methods.  The txn attributes should be
 * annotated in the Impl class as well.
 * @author jraber
 *
 */
public interface XOService 
{
	@Transactional(propagation=Propagation.REQUIRED )
	XOSession createXOSession(String merchantID);
	
	@Transactional(propagation=Propagation.REQUIRED)
	XOSession getXOSession(Long xoSessionID);
	
	@Transactional(propagation=Propagation.REQUIRED)
	XOSession updateXOSession(XOSession xoSession);
	
	@Transactional(propagation=Propagation.REQUIRED)
	public boolean commitXOSession(Long xoSessionID);
	
	@Transactional(propagation=Propagation.REQUIRED)
	XOSession getArchivedXOSession(Long xoSessionID);
}
