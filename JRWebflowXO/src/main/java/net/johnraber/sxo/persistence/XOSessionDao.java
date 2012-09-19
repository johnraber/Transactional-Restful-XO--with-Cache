package net.johnraber.sxo.persistence;


import org.springframework.transaction.annotation.Transactional;
import net.johnraber.sxo.persistence.model.XOSession;


@Transactional
public interface XOSessionDao 
{
	public void addXOSession(XOSession xoSession);
	
	public XOSession getXOSessionById(long id);
	
	public void saveXOSession(XOSession xoSession);
}
