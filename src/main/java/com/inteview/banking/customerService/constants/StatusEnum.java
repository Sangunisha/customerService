package com.inteview.banking.customerService.constants;

public enum StatusEnum {
	ACTIVE("active", 0), INACTIVE("inactive", -1), ARCHIVED("archived", -2), EXPIRED("expired", -3), CANCELLED("cancelled", -4);
	private final String name;
	private final int code;
	
	private StatusEnum(String name, int code){
		this.name = name;
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public int getCode() {
		return code;
	}
	
}
