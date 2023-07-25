package com.hei.project2p1.exception.ExceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ErrorDetailsFormat {
	private Date timestamp;
	private String message;
	private String details;
}
