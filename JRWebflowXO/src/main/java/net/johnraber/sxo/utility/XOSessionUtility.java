package net.johnraber.sxo.utility;

import net.johnraber.sxo.model.XOSession;

public class XOSessionUtility {

	public static XOSession createDomainModel(net.johnraber.sxo.model.json.XOSession xoSession)
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
	
	public static net.johnraber.sxo.model.json.XOSession createJsonModel(XOSession xoSession)
	{
		net.johnraber.sxo.model.json.XOSession jsonXOSession = new net.johnraber.sxo.model.json.XOSession();
		jsonXOSession.setXosessionId( xoSession.getXosessionId());
		jsonXOSession.setBuyer( xoSession.getBuyer() );
		jsonXOSession.setItem( xoSession.getItem() );
		jsonXOSession.setMerchant( xoSession.getMerchant() );
		jsonXOSession.setPrice( xoSession.getPrice() );
		jsonXOSession.setPurchaseDate( xoSession.getPurchaseDate() );
		jsonXOSession.setShippingAddress( xoSession.getShippingAddress() );
		return jsonXOSession;
	}
}
