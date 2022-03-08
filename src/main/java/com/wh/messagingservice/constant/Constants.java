package com.wh.messagingservice.constant;

public class Constants
{

	// 25[0-5]        = 250-255
	// (2[0-4])[0-9]  = 200-249
	// (1[0-9])[0-9]  = 100-199
	// ([1-9])[0-9]   = 10-99
	// [0-9]          = 0-9
	// (\.(?!$))      = can't end with a dot
	public static final String IPV4_PATTERN_SHORTEST =
			"^((25[0-5]|(2[0-4]|1[0-9]|[1-9]|)[0-9])(\\.(?!$)|$)){4}$";
}
