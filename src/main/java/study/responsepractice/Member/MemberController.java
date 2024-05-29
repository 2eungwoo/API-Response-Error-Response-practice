package study.responsepractice.Member;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.responsepractice.Member.dto.MemberDto;
import study.responsepractice.global.response.ResponseCode;
import study.responsepractice.global.response.ResponseCodeMyLegacy;
import study.responsepractice.global.response.ResultResponse;
import study.responsepractice.global.response.ResultResponseMyLegacy;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // my legacy
    @PostMapping("/v1/member")
    public ResultResponseMyLegacy<?> saveMember(@Valid @RequestBody MemberDto.Request memberRequestDto){
        MemberDto.Response savedMemberDto = memberService.saveMember(memberRequestDto);
        return ResultResponseMyLegacy.builder()
                .status(ResponseCodeMyLegacy.REGISTER_SUCCESS.getStatus())
                .code(ResponseCodeMyLegacy.REGISTER_SUCCESS.getCode())
                .message(ResponseCodeMyLegacy.REGISTER_SUCCESS.getMessage())
                .data(savedMemberDto)
                .build();
    }

    // new version
    @PostMapping("/v2/member")
    public ResponseEntity<ResultResponse> signup(@Valid @RequestBody MemberDto.Request memberRequestDto) {
        MemberDto.Response memberResponseDto = memberService.saveMember(memberRequestDto);
        ResultResponse response = ResultResponse.of(ResponseCode.REGISTER_SUCCESS, memberResponseDto);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    // new version
    @GetMapping("/v2/member/{memberId}")
    public ResponseEntity<ResultResponse> getMyInfo(@PathVariable Long memberId) {
        MemberDto.Response memberResponseDto = memberService.getMyInfo(memberId);
        ResultResponse response = ResultResponse.of(ResponseCode.GET_MY_INFO_SUCCESS, memberResponseDto);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }
}
