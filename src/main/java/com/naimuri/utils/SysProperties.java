package com.naimuri.utils;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.apache.commons.lang3.StringUtils;


/**
 * A singleton class to provide read access to the src/main/resources/config.properties resource file
 * @author Paul
 */
public class SysProperties {

	static Logger log = Logger.getLogger(SysProperties.class.getName());
	
	// object to hold the singleton instance of this class
	private static SysProperties sysProperties;
	
	// store the properties in a map
	private Map<String, String> propVals = new HashMap<String, String>();
		
	private SysProperties() throws IOException {
		load();
	}
	
	
	public static SysProperties getInstance() throws IOException {
		if (sysProperties == null) {
			return new SysProperties();
		}
		
		return sysProperties;
	}
	
	
	private void load() throws IOException {
		Properties prop = new Properties();
		InputStream input = new FileInputStream("src/main/resources/config.properties");
		Exception failure = null;
		
		try {
			prop.load(input);
			Enumeration<?> e = prop.propertyNames();
			while (e.hasMoreElements()) {
				String key = (String) e.nextElement();
				String value = prop.getProperty(key);
				propVals.put(key, value);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			failure = ex;
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
					failure = e;
				}
			}
		}
		
		if (failure != null) {
			throw new IOException("Error retrieving data from config.properties");
		}
	}
	
	
	public static String getProperty(String key) {
		SysProperties instance = null;
		try {
			instance = SysProperties.getInstance();
		} catch(IOException e) {
			log.info("Exception reading properties file", e);
		}
		if (instance.getPropVals() == null || instance.getPropVals().get(key) == null) {
			log.info("No value found from properties file for: " + key);
			return StringUtils.EMPTY;
		}
		
		return instance.getPropVals().get(key);
	}


	public Map<String, String> getPropVals() {
		return propVals;
	}
	
	
	
	
}
