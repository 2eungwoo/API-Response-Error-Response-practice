package study.responsepractice.global.error.exception;

import study.responsepractice.global.error.ErrorCode;

public class 기타등등Exception extends CustomException{
    public 기타등등Exception() {
        super("흐음",ErrorCode.NEED_LOGIN);
    }
}
