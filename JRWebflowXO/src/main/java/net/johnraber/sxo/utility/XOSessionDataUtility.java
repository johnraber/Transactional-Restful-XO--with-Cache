package net.johnraber.sxo.utility;

import net.johnraber.sxo.persistence.model.XOSession;
//import net.johnraber.sxo.model.XOSession;

public class XOSessionDataUtility {

//	public XOSessionDataUtility() {
//		// TODO Auto-generated constructor stub
//	}
	
	public static XOSession createDomainModel(net.johnraber.sxo.model.XOSession xoSession)
	{
		XOSession domainXOSession = new XOSession();
		domainXOSession.setXosessionId( xoSession.getXosessionId());
		domainXOSession.setBuyer( xoSession.getBuyer() );
		domainXOSession.setItem( xoSession.getItem() );
		domainXOSession.setMerchant( xoSession.getMerchant() );
		domainXOSession.setPrice( xoSession.getPrice() );
		domainXOSession.setPurchaseDate( xoSession.getPurchaseDate() );
		domainXOSession.setShippingAddress( xoSession.getShippingAddress() );
		return domainXOSession;
	}

	

}
