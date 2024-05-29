package study.responsepractice.global.error.exception;

import study.responsepractice.global.error.ErrorCode;

public class MemberNotExistException extends CustomException{
    public MemberNotExistException(){
        super(ErrorCode.MEMBER_NOT_EXIST);
    }
}
