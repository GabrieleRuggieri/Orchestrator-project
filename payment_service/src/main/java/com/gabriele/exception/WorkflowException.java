package com.gabriele.exception;

import java.io.Serial;

public class WorkflowException extends RuntimeException {

    /**
	 * 
	 */
	@Serial
	private static final long serialVersionUID = 1L;

	public WorkflowException(String message) {
        super(message);
    }

}