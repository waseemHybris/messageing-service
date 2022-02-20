package com.unity.messagingservice.constant;

public class Constants
{
	public static final String IP4ADDRESS_REG_EXP =  "^(([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])(\\.(?!$)|$)){4}$" ;

	// 25[0-5]        = 250-255
	// (2[0-4])[0-9]  = 200-249
	// (1[0-9])[0-9]  = 100-199
	// ([1-9])[0-9]   = 10-99
	// [0-9]          = 0-9
	// (\.(?!$))      = can't end with a dot
	public static final String IPV4_PATTERN_SHORTEST =
			"^((25[0-5]|(2[0-4]|1[0-9]|[1-9]|)[0-9])(\\.(?!$)|$)){4}$";
}
