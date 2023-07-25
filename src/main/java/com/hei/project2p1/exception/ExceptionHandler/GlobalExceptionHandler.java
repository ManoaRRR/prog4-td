package com.hei.project2p1.exception.ExceptionHandler;

import com.hei.project2p1.exception.BadRequestException;
import com.hei.project2p1.exception.ForbiddenException;
import com.hei.project2p1.exception.NotFoundException;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

//@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	// handling specific exception
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ErrorDetailsFormat> NotFoundExceptionHandling(NotFoundException exception, WebRequest request){
		if (AnnotationUtils.findAnnotation(exception.getClass(), ResponseStatus.class) != null) {
			throw exception;
		}
		return new ResponseEntity<>(exceptionFormatter(exception,request), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ErrorDetailsFormat> BadRequestExceptionHandling(BadRequestException exception, WebRequest request){
		if (AnnotationUtils.findAnnotation(exception.getClass(), ResponseStatus.class) != null) {
			throw exception;
		}
		return new ResponseEntity<>(exceptionFormatter(exception,request), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ForbiddenException.class)
	public ResponseEntity<ErrorDetailsFormat> BadRequestExceptionHandling(ForbiddenException exception, WebRequest request){
		if (AnnotationUtils.findAnnotation(exception.getClass(), ResponseStatus.class) != null) {
			throw exception;
		}
		return new ResponseEntity<>(exceptionFormatter(exception,request), HttpStatus.FORBIDDEN);
	}

	// handling global exception
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetailsFormat> GlobalExceptionHandling(Exception exception, WebRequest request) throws Exception {
		if (AnnotationUtils.findAnnotation(exception.getClass(), ResponseStatus.class) != null) {
			throw exception;
		}
		return new ResponseEntity<>(exceptionFormatter(exception,request), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private ErrorDetailsFormat exceptionFormatter(Exception exception, WebRequest request) {
		return new ErrorDetailsFormat(new Date(), exception.getMessage(), request.getDescription(true));
	}

}
