package net.johnraber.sxo.utility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 
 * @author John Raber
 * @version 0.1
 * Written July 11, 2012
 *
 */
public class SecurityUtils {
	
	private static final Logger log = LoggerFactory.getLogger(SecurityUtils.class);
	
	/**
	 * This method gets the current pre-authenticated user principal from the Spring
	 * SecurityContextHolder and returns the name as a String. If the user principal 
	 * is not an instance of UserDetails, toString() is called on the principal and 
	 * returned.
	 * @return the name of the current pre-authenticated user
	 */
	public static String getUser() {
		String username;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
		} 
		else {
			username = principal.toString();
			log.debug("User Principal was not an instance of UserDetails; returning userPrincipal.toString(): " + username);
		}
		return username;
	}
	/**
	 * This method grabs user and associated authorities from the SecurityContextHolder
	 * and returns them as a List of Strings.
	 * If the user principal is not an instance of UserDetails, this method returns null.
	 * @return the current pre-authenticated user's authorities/groups/roles
	 */
	public static List<String> getAuthorities() {
		Collection<? extends GrantedAuthority> gas;
		List<String> output = new ArrayList<String>();
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			gas = ((UserDetails)principal).getAuthorities();
			for (GrantedAuthority ga: gas) output.add(ga.getAuthority());
		} 
		else {
			log.debug("User Principal was not an instance of UserDetails; returning null.");
			return null;
		}
		return output;
	}
}
