package com.api.utils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class DateTimeUtil {
	
	private DateTimeUtil()
	{
		
	}
	
	public static String getTimeWithDaysAge(int days)
	{	
		return Instant.now().minus(days,ChronoUnit.DAYS).toString();
		
	}
	
	
	
}
