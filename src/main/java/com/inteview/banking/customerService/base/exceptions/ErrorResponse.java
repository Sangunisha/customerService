package com.inteview.banking.customerService.base.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@JsonInclude(Include.NON_NULL)
public class ErrorResponse {

	private AppException.AppExceptionErrorCode errorCode;
	private String errorMessage;
	private Boolean status = false;
	private String err;
	private String code;

	public ErrorResponse() {
	}

	public AppException.AppExceptionErrorCode getErrorCode() {
		return this.errorCode;
	}

	public void setErrorCode(AppException.AppExceptionErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return this.errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Boolean getStatus() {
		return this.status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getErr() {
		return this.err;
	}

	public void setErr(String err) {
		this.err = err;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
