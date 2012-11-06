package net.johnraber.sxo.model.json;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;




/**
 *  Model at view or external boundary of a XO session entity,
 *  NOT to be used at the service/domain or persistence/storage levels
 * 
 * @author jraber
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class XOSession implements Serializable
{
	  private static final long serialVersionUID = 1L;
	  
//	    public XOSession()
//	    {
//	    	
//	    }
	  
	    @JsonProperty
	    private long xosessionId ;     
	  
	    @JsonProperty
		private String buyer;
	    
	    @JsonProperty
	    private String merchant;
		
	    @JsonProperty
		private String item;
		
	    @JsonProperty
		private BigDecimal price;
		
	    @JsonProperty
		private Date purchaseDate;
		
	    @JsonProperty
		private String shippingAddress;
		
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
		public BigDecimal getPrice() {
			return price;
		}
		public void setPrice(BigDecimal price) {
			this.price = price;
		}
		public Date getPurchaseDate() {
			return purchaseDate;
		}
		public void setPurchaseDate(Date purchaseDate) {
			this.purchaseDate = purchaseDate;
		}
		public String getShippingAddress() {
			return shippingAddress;
		}
		public void setShippingAddress(String shippingAddress) {
			this.shippingAddress = shippingAddress;
		}
		
	
		public String getDisplayString()
		{
			// return ObjectUtils.getDisplayString(this);
			return ReflectionToStringBuilder.toString(this);
		}
		
		@Override
		public String toString()
		{
			return ReflectionToStringBuilder.toString(this);
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
