package study.responsepractice.global.error.exception;

import study.responsepractice.global.error.ErrorCode;

public class NeedLoginException extends CustomException{
    public NeedLoginException() {
        super(ErrorCode.NEED_LOGIN);
    }
}
