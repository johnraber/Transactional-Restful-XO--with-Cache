package net.johnraber.sxo.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;




/**
 * Cannonical view of XOSession at the service/domain level, NOT a 
 * representation for persistence or view level
 * 
 * @author jraber
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class XOSession implements Serializable
{
	  private static final long serialVersionUID = 1L;
	  
	    public XOSession()
	    {
	    	
	    }
	  
	    private long xosessionId ;     
	  

		private String buyer;
		
		public long getXosessionId() {
			return xosessionId;
		}
		public void setXosessionId(long xosessionId) {
			this.xosessionId = xosessionId;
		}
		public String getBuyer() {
			return buyer;
		}
		public void setBuyer(String buyer) {
			this.buyer = buyer;
		}
		public String getMerchant() {
			return merchant;
		}
		public void setMerchant(String merchant) {
			this.merchant = merchant;
		}
		public String getItem() {
			return item;
		}
		public void setItem(String item) {
			this.item = item;
		}
		public String getPrice() {
			return price;
		}
		public void setPrice(String price) {
			this.price = price;
		}
		public Date getPurchaseDate() {
			return purchaseDate;
		}
		public void setPurchaseDate(Date purchaseDate) {
			this.purchaseDate = purchaseDate;
		}
		public Date getShippingAddress() {
			return shippingAddress;
		}
		public void setShippingAddress(Date shippingAddress) {
			this.shippingAddress = shippingAddress;
		}
		
	
		private String merchant;
		
		private String item;
		
		private String price;
		
		private Date purchaseDate;
		
		private Date shippingAddress;
		
	
		public String getDisplayString()
		{
			// return ObjectUtils.getDisplayString(this);
			return ReflectionToStringBuilder.toString(this);
		}
		
		@Override
		public String toString()
		{
			return super.toString();
		}
		
		
		@Override
		public boolean equals(Object o) {
			if( o instanceof XOSession )
			{
				return ((XOSession)o).getXosessionId() == this.xosessionId;
			}
			return false;
		}
		
		
		@Override
		public int hashCode() {
			// TODO Auto-generated method stub
			return 0;
		}
		
		

}
