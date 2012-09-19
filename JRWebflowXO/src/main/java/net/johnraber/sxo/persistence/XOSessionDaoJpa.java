package net.johnraber.sxo.persistence;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
// import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.johnraber.sxo.persistence.model.XOSession;




/**
 * annotate our DAO with @Respository so that
 * PersistenceExceptionTranslationPostProcessor will know that this is one
 * of those beans for whom exceptions should be translated into one of
 * Spring's unified access data exceptions.
 * 
 * Ensure PersistenceExceptionTranslationPostProcessor is configured
 * in Spring context
 */
@Repository("xoSessionDao")
@Transactional
public class XOSessionDaoJpa implements XOSessionDao
{
	
	@PersistenceContext
	private EntityManager em;
	
	public void addXOSession(XOSession xoSession)
	{
		em.persist(xoSession);
	}
	
	public XOSession getXOSessionById(long id)
	{
		return em.find(XOSession.class,id);
	}
	
	public void saveXOSession(XOSession xoSession)
	{
		em.merge(xoSession);
	}
}
