package net.johnraber.sxo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;


import net.johnraber.sxo.model.XOSession;

public class RESTfulSXOClient
{
	private static final Logger log = LoggerFactory.getLogger(RESTfulSXOClient.class);
	
	private StringBuffer XOOSessionUrl = new StringBuffer("http://localhost:8080/xo/xoSession/");
	
	public RestTemplate restTemplate = new RestTemplate();
	
	public static void main(String[] args) 
	{
		RESTfulSXOClient doIt = new RESTfulSXOClient();
		
		// Merchant ID
		XOSession xoSession = doIt.createXOSession("12345");
		
		long xoSessionId = xoSession.getXosessionId();
		
		
		XOSession xoSession2 = doIt.getXOSession(String.valueOf(xoSessionId) );
		
		assert( xoSession2.equals( xoSession ) );

//		xoSession.setBuyer("JR");
//		xoSession.setItem("KTM  EXC-F 350");
//		
//		doIt.updateXOSession( String.valueOf(xoSessionId ), xoSession);
		
		doIt.commitXOSession(String.valueOf( xoSession.getXosessionId() )  );
	}
	
	public RESTfulSXOClient()
	{
		
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
		  String xoSessUrl = this.XOOSessionUrl.append( xoSessionID ).toString();
		  
		  xoSession = restTemplate.getForObject(xoSessUrl, XOSession.class );
		
		log.info("get XOSession returning: " + xoSession.getDisplayString() );
	
	  }
	  catch (Exception e)
	  {
	    e.printStackTrace();
	  } 
	  
	  return xoSession;
	}
	
	public XOSession updateXOSession(String xoSessionID, XOSession clientXOSession)
	{
		XOSession serverUpdatedXOSession = null;
			
		  try
		  {
			  String xoSessUrl = this.XOOSessionUrl.append( xoSessionID ).toString();
			  
			  restTemplate.put(xoSessUrl, clientXOSession);
			
			//  serverUpdatedXOSession = getXOSession(xoSessionID);
			  
			 // log.info("updateXOSession returning: " + serverUpdatedXOSession.getDisplayString() );
			
		  }
		  catch (Exception e)
		  {
		    e.printStackTrace();
		  } 
		  
		  return serverUpdatedXOSession;
	}
	
	
	public void commitXOSession(String xoSessionID)
	{
		
		  try
		  {
			  String xoSessUrl = this.XOOSessionUrl.append( xoSessionID ).toString();
			  
			   restTemplate.postForEntity(xoSessUrl, null, Boolean.class);
				
			  log.info("Successfully commited XO session with id: " + xoSessionID);
			
		  }
		  catch (Exception e)
		  {
		    e.printStackTrace();
		  } 
		  
	}	
	
}
