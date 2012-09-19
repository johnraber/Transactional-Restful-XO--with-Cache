package net.johnraber.sxo.security;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author John Raber
 *
 * June.13.2012
 * 
 * If testing locally, without SiteMinder to provide a header with "SM_USER", class
 * RequestHeaderAuthenticationFilter will throw an exception in method 
 * getPreAuthenticatedPrincipal(request). This class returns a user name for that method and
 * thus should be used instead of RequestHeaderAuthenticationFilter when testing locally.
 * 
 */
public class RHAFImpl2 extends org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter{
  
	private static final Logger log = LoggerFactory.getLogger(RHAFImpl2.class.getName());
	
	@Override
	protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
		
		log.debug("request: " + request);
		
		if (request != null) {
			log.info("request is not null. ");
			log.debug("request auth type: " + request.getAuthType());
			log.debug("request attribute names: " + request.getAttributeNames());
			log.debug("request user principals: " + request.getUserPrincipal());
			log.debug("request session: " + request.getSession());
			log.debug("request header names: " + request.getHeaderNames());
			log.debug("request cookies: " + request.getCookies());
		} 

		log.info("Setting principal to X129969. ");
        return "X129969";
    }
}
