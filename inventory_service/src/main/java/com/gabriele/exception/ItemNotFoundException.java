package com.gabriele.exception;

import com.gabriele.common.enums.ErrorMessageEnum;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

@Getter
public class ItemNotFoundException extends HttpStatusCodeException {

    private final ErrorMessageEnum errorMessage = ErrorMessageEnum.ITEM_NOT_FOUND;

    public ItemNotFoundException() {
        super(HttpStatus.NOT_FOUND);
    }
}
