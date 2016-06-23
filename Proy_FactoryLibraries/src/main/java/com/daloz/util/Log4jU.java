package com.daloz.util;

import org.apache.log4j.Logger;

public class Log4jU
{
	public static Logger logger = null;


	public static void setLogger(Class<?> clazz)
	{
		logger = Logger.getLogger(clazz);
	}
	
	
}
