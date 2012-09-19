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
 * If testing security with SiteMinder, you can use this class instead of RequestHeaderAuthenticationFilter
 * to get more detailed logging.
 * 
 */
public class RHAFImpl extends org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter{
	
	private static final Logger log = LoggerFactory.getLogger(RHAFImpl.class.getName());
	
	protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
		
		log.debug("Your request is: " + request);
		if (request != null) {
			log.debug("Your Principal is: " + request.getUserPrincipal());
			if (request.getUserPrincipal() != null) log.debug("Your Principal Name is: " + request.getUserPrincipal().getName());
			log.debug("Your Session is: " + request.getSession());
			log.debug("Your Header names are: " + request.getHeaderNames());
			log.debug("Your request Cookies are: " + request.getCookies());
		}
		
		return super.getPreAuthenticatedPrincipal(request);
    }
}
