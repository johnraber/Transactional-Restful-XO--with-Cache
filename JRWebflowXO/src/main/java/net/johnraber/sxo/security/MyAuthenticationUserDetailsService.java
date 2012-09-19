package net.johnraber.sxo.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import net.johnraber.sxo.utility.PropertyUtils;

/**
 * 
 * @author John Raber
 * June.12.2012
 * 
 * This class enables the use of file based authorization with SiteMinder pre-authentication.
 * This class grabs a userProperties files with user=roles mappings and assigns those roles to
 * the user authenticated by SiteMinder.
 * 
 * Properties userProperties: properties file set declaratively in spring-security.xml.
 * format for file is user=role[,role]
 * 
 */
public class MyAuthenticationUserDetailsService implements UserDetailsService {

	private static final Logger log = LoggerFactory.getLogger(MyAuthenticationUserDetailsService.class);
	private Properties userProperties;

	@Override
	public UserDetails loadUserByUsername(String user) throws UsernameNotFoundException {
		log.debug("enter loadUserDetails");	
		if (user != null) {
			log.debug("Principal Found! User is: " + user);
			
			List<String> userRoles = PropertyUtils.getValues(userProperties, user);
			if ( userRoles == null) throw new UsernameNotFoundException("User " + user + " not found.");
			
			log.debug("userRoles: " + userRoles);
			
			Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			for (String s: userRoles) authorities.add(new SimpleGrantedAuthority(s));
			
			return new User(user, "none", true, true, true, true, authorities);
		} 
		else {
			log.info("In loadUserByUsername(String user) user is null");
			return null;
		}
	}

	public Properties getUserProperties() {
		return userProperties;
	}

	public void setUserProperties(Properties userProperties) {
		this.userProperties = userProperties;
	}
}
