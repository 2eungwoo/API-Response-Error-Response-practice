package study.responsepractice.Member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.responsepractice.Member.dto.MemberDto;
import study.responsepractice.global.error.ErrorCode;
import study.responsepractice.global.error.exception.CustomException;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberDto.Response saveMember(MemberDto.Request memberRequestDto){
            Member memberEntity = memberRequestDto.toEntity();
            Member savedMember = memberRepository.save(memberEntity);
            return new MemberDto.Response(savedMember);
    }

    // todo : unchecked exception은 controllerAdvice로 어케 못하나?
    public MemberDto.Response getMyInfo(Long memberId){
        Member foundMember = memberRepository.findById(memberId).orElseThrow(
                ()-> new CustomException(" User Not Exist ", ErrorCode.MEMBER_NOT_EXIST)
        );
        return new MemberDto.Response(foundMember);
    }
}
