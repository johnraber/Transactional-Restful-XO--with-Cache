package net.johnraber.sxo;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;


import net.johnraber.sxo.model.json.XOSession;

public class RESTfulSXOClient
{
	private static final Logger log = LoggerFactory.getLogger(RESTfulSXOClient.class);
	
	private String paramXoSessUrl = "http://localhost:8080/xo/xoSession/{xoSessionID}";
	
	public RestTemplate restTemplate = new RestTemplate();
	
	
	public static void main(String[] args) 
	{
		RESTfulSXOClient doIt = new RESTfulSXOClient();
		
		// Merchant ID
		XOSession xoSession = doIt.createXOSession("12345");
		
		XOSession xoSession2 = doIt.getXOSession(
				String.valueOf( xoSession.getXosessionId() ) );
		
		assert( xoSession2.equals( xoSession ) );

		xoSession.setBuyer("JR");
		xoSession.setItem("KTM  EXC-F 350");		
		
		doIt.updateXOSession(xoSession);
		
		doIt.commitXOSession(xoSession);
	}
	
	public RESTfulSXOClient()
	{
		// restTemplate.setMessageConverters(messageConverters)
	}
	
	public XOSession createXOSession(String merchantID)
	{
	  XOSession xoSession = null;
		 
	  try
	  {
		// Assemble URL
		String createXOSessionUrl = "http://localhost:8080/xo/merchant/" + merchantID;
			
		xoSession = restTemplate.postForObject(createXOSessionUrl, null, XOSession.class );
		
		log.info("createXOSession() returning: " + xoSession.getDisplayString() );
	  }
	  catch (Exception e)
	  {
	    e.printStackTrace();
	  } 
	  
	  return xoSession;
	}
	
	
	public XOSession getXOSession(String xoSessionID)
	{
	  XOSession xoSession = null;
		
	  try
	  {
		 xoSession = restTemplate.getForObject(paramXoSessUrl, XOSession.class, xoSessionID );

		log.info("get XOSession returning: " + xoSession.getDisplayString() );
	
	  }
	  catch (Exception e)
	  {
	    e.printStackTrace();
	  } 
	  
	  return xoSession;
	}
	
	
	public void updateXOSession(XOSession clientXOSession)
	{
		  try
		  {
			  restTemplate.put(paramXoSessUrl, clientXOSession, clientXOSession.getXosessionId() );
			  
			  log.info("Updated XO session: " + clientXOSession.getDisplayString() );
		  }
		  catch (Exception e)
		  {
		    e.printStackTrace();
		  } 
	}	
	
	
	public void commitXOSession(String xoSessionID)
	{
		XOSession xoSession = null;
		
		  try
		  {
			  xoSession = restTemplate.postForObject(paramXoSessUrl, null, XOSession.class, xoSessionID);
				
			  log.info("Successfully commited XO session: " + xoSession.getDisplayString() );
		  }
		  catch (Exception e)
		  {
		    e.printStackTrace();
		  } 
	}	
	
	
	public void commitXOSession(XOSession xoSession)
	{
		  try
		  {
			  restTemplate.postForObject(paramXoSessUrl, xoSession, XOSession.class, String.valueOf(
					  xoSession.getXosessionId() ) );
				
			  log.info("Successfully commited XO session: " + xoSession.getDisplayString() );
		  }
		  catch (Exception e)
		  {
		    e.printStackTrace();
		  } 
	}	
	
}
