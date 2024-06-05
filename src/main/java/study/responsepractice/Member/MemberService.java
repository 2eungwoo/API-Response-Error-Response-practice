package study.responsepractice.Member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.responsepractice.Member.dto.MemberDto;
import study.responsepractice.global.error.ErrorCode;
import study.responsepractice.global.error.exception.AuthenticationNotFoundException;
import study.responsepractice.global.error.exception.CustomException;
import study.responsepractice.global.error.exception.MemberNotExistException;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberDto.Response saveMember(MemberDto.Request memberRequestDto){
            Member memberEntity = memberRequestDto.toEntity();
            Member savedMember = memberRepository.save(memberEntity);
            return new MemberDto.Response(savedMember);
    }

    // todo : checked exception
    public MemberDto.Response getMyInfo(Long memberId){
        Member foundMember = memberRepository.findById(memberId).orElseThrow(
                ()-> new MemberNotExistException()
        );
        return new MemberDto.Response(foundMember);
    }
}
