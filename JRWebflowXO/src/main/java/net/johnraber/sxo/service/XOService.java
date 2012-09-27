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
	
	
	/**
	 * Currently, last in wins but could build in merge
	 * rules based on biz logic
	 * 
	 * @param xoSession
	 * @return
	 * @throws Exception is txn is rolled-back
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	XOSession updateXOSession(XOSession xoSession) throws Exception;
	
	/**
	 * This method assumes caller knows the current state ( server version )
	 * of the XOSession and wants to persist it.  Providing this method so 
	 * the entire XOSession object does not have to be passed through 
	 * for performance ( speed, network bandwidth, ...
	 * no data movement or processing needed ).  Use 
	 * commitXOSession(XOSession xoSession) if you need to pass in 
	 * changes and commit the resource.
	 * @throws Exception if XO session does not exist in the 
	 *         cache and transaction is rolled-back
	 *  
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public XOSession commitXOSession(Long xoSessionID) throws Exception;
	
	
	/**
	 * This version of the xoSession will be committed with no
	 * merge/biz rules run of the XO session.  LAST in WINS!
	 * @param xoSession
	 * @return
	 * @throws Exception if XO session is null or does not exist in the 
	 *         cache and transaction is rolled-back
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public boolean commitXOSession(XOSession xoSession) throws Exception;
	
	@Transactional(propagation=Propagation.REQUIRED)
	XOSession getArchivedXOSession(Long xoSessionID);
	
	@Transactional(propagation=Propagation.REQUIRED)
	public Boolean deleteXOSession(Long xoSessionID);
}
