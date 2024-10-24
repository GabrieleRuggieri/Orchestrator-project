package com.gabriele.common.enums;

public enum ErrorMessageEnum {

    ITEM_NOT_FOUND("ERR_ITEM_NOT_FOUND", "Item not found");

    private final String error; // es: ERR_NOT_FOUND
    private final String message;

    ErrorMessageEnum(String error, String message) {
        this.error = error;
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }


}
