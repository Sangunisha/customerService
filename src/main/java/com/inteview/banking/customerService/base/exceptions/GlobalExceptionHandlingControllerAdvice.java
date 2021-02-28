package com.inteview.banking.customerService.base.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 *
 */
@ControllerAdvice("com.inteview.banking.customerService")
public class GlobalExceptionHandlingControllerAdvice {

	protected Logger logger;
	
	@Autowired
	private MessageSource messageSource;

	public GlobalExceptionHandlingControllerAdvice() {
		logger = LoggerFactory.getLogger(getClass());
	}


	/**
	 * Convert a predefined exception to an HTTP Status code
	 * @param exception
	 * @return
	 */
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public @ResponseBody
    ErrorResponse handleGeneralError(Exception exception) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setErrorCode(AppException.AppExceptionErrorCode.api_exception);
		errorResponse.setErrorMessage(exception.getMessage());
		logger.debug("General Exception", exception);
		return errorResponse;
	}

	/**
	 * Convert a AppException to an HTTP Status code
	 * @param appException
	 * @return
	 */
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(AppException.class)
	public @ResponseBody
    ErrorResponse handleAppError(AppException appException) {
		ErrorResponse errorResponse = new ErrorResponse();
		if (appException.getErrorCode()!=null)
			errorResponse.setErrorCode(appException.getErrorCode());
		errorResponse.setErrorMessage(appException.getMessage());
		logger.debug("AppException", appException);
		return errorResponse;
	}

}