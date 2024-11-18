package com.gabriele.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Book implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String bookName;
	private String Author;

}
