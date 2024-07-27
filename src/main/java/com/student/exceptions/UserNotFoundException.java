package com.student.exceptions;

public class UserNotFoundException extends RuntimeException{
	
	public UserNotFoundException(String mess) {
		super(mess);
	}

}
