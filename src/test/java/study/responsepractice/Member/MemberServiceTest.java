package study.responsepractice.Member;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import study.responsepractice.Member.dto.MemberDto;
import study.responsepractice.global.error.exception.CustomException;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    @Test
    @DisplayName("회원 조회 실패 (유저 없음)")
    void getMemberFail() {
        // given
        Long fakeMemberId = 33333L;

        // `memberRepository.findById`가 `Optional.empty()`를 반환하도록 설정
        when(memberRepository.findById(fakeMemberId)).thenReturn(Optional.empty());

        // then
        // `memberService.getMyInfo(fakeMemberId)` 호출 시 `CustomException`이 발생하는지 검증
        assertThrows(CustomException.class, () -> memberService.getMyInfo(fakeMemberId));
    }

}