package net.johnraber.sxo.persistence.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "`jr_sxo_xosession`", schema = "sxo")
public class XOSession
{
	  private static final long serialVersionUID = 1L;
	  
	    public XOSession()
	    {
	    	
	    }
	    
	    
	    @Id
	    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="xosession_seq_gen") 
	    @SequenceGenerator(name="xosession_seq_gen", sequenceName="sxo.xosession_seq") 
	    @Column(name = "xosession_id", unique = true, nullable = false, insertable = true, updatable = true)
	    private long xosessionId ;     
	  
//		@OneToOne
//		@JoinColumn(name = "merchant_id")
//	    private MerchantProfile merchant;
		
		@Column(name = "buyer", unique = false, nullable = true, insertable = true, updatable = true)
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
		
		@Column(name = "merchant", unique = false, nullable = true, insertable = true, updatable = true)
		private String merchant;
		@Column(name = "item", unique = false, nullable = true, insertable = true, updatable = true)
		private String item;
		@Column(name = "price", unique = false, nullable = true, insertable = true, updatable = true)
		private String price;
		@Column(name = "purchaseDate", unique = false, nullable = true, insertable = true, updatable = true)
		private Date purchaseDate;
		@Column(name = "shippingAddress", unique = false, nullable = true, insertable = true, updatable = true)
		private Date shippingAddress;
		
		
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public boolean equals(Object o) {
			// TODO Auto-generated method stub
			return false;
		}
		@Override
		public int hashCode() {
			// TODO Auto-generated method stub
			return 0;
		}
}
