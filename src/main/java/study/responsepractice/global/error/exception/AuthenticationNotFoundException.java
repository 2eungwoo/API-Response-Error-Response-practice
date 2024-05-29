package study.responsepractice.global.error.exception;

import study.responsepractice.global.error.ErrorCode;

public class AuthenticationNotFoundException extends CustomException {
    public AuthenticationNotFoundException() {
        super(ErrorCode.AUTHENTICATION_NOT_FOUND);
    }
}