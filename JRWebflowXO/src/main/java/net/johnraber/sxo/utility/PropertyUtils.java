package net.johnraber.sxo.utility;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * 
 * @author John Raber
 *
 */
public class PropertyUtils {	

	/**
	 * This method will load a property file located in the Maven directory 
	 * resources/properties. 
	 * The propertyFileName param should be the simple name with ".properties" 
	 * attached to the end of the file name. 
	 * This method will attempt to load the property file contents into Properties 
	 * Object p, returning true if successful and false if unsuccessful.
	 * For example, "foo.properties".
	 * @param p
	 * @param propertyFileName
	 * 
	 */
	public static boolean loadProperties(Properties p, String propertyFileName) {
		StringBuilder sb = new StringBuilder("/properties/");
		sb.append(propertyFileName);
		try {
			p.load(PropertyUtils.class.getResourceAsStream(sb.toString()));
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * This method will load a property file located in the Maven directory 
	 * resources/properties. 
	 * The propertyFileName param should be the simple name with ".properties" 
	 * attached to the end of the file name. 
	 * This method will return a Properties object representing the contents of
	 * the loaded property file. 
	 * For example, "foo.properties".
	 * 	 
	 * @param p
	 * @param propertyFileName
	 * 
	 */
	public static Properties loadProperties(String propertyFileName) {
		Properties p = new Properties();
		StringBuilder sb = new StringBuilder("/properties/");
		sb.append(propertyFileName);
		try {
			p.load(PropertyUtils.class.getResourceAsStream(sb.toString()));
			return p;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	
	/**
	 * 
	 * @param p
	 * @param key
	 * @return A list of values associated with the given key. If the key is not found, 
	 * then null is returned. Properties are stored as a Hashtable, and 'null' is not
	 * a valid value as a key in a Hashtable.
	 */
	public static List<String> getValues(Properties p, String key) {
		String value = p.getProperty(key);
		if (value == null) return null;
		String[] list = value.trim().split(",");
		return Arrays.asList(list);
	}
	
	/**
	 * 
	 * @param p
	 * @param key
	 * @param regex
	 * @return A list of values associated with the given key. The values are separated by
	 * the given regex. If the key is not found, then null is returned. If the user is not 
	 * found, then null is returned. Properties are stored as a Hashtable, and 'null' is not
	 * a valid value as a key in a Hashtable.
	 */
	public static List<String> getValues(Properties p, String key, String regex) {
		String value = p.getProperty(key);
		if (value == null) return null;
		String[] list = value.trim().split(regex);
		return Arrays.asList(list);
	}
	
}
