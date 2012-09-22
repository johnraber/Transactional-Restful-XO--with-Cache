package net.johnraber.sxo.utility;

import net.johnraber.sxo.persistence.model.XOSession;
//import net.johnraber.sxo.model.XOSession;

public class XOSessionDataUtility {

//	public XOSessionDataUtility() {
//		// TODO Auto-generated constructor stub
//	}
	
	public static XOSession createPersistentModel(net.johnraber.sxo.model.XOSession xoSession)
	{
		XOSession persistentXOSession = new XOSession();
		persistentXOSession.setXosessionId( xoSession.getXosessionId());
		persistentXOSession.setBuyer( xoSession.getBuyer() );
		persistentXOSession.setItem( xoSession.getItem() );
		persistentXOSession.setMerchant( xoSession.getMerchant() );
		persistentXOSession.setPrice( xoSession.getPrice() );
		persistentXOSession.setPurchaseDate( xoSession.getPurchaseDate() );
		persistentXOSession.setShippingAddress( xoSession.getShippingAddress() );
		return persistentXOSession;
	}
}
