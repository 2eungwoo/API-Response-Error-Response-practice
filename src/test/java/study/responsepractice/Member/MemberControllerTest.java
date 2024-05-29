package study.responsepractice.Member;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.coyote.BadRequestException;
import org.awaitility.core.ThrowingRunnable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import study.responsepractice.Member.dto.MemberDto;
import study.responsepractice.global.error.GlobalExceptionHandler;

import java.net.BindException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(MemberController.class)
@MockBean(JpaMetamodelMappingContext.class)
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private MemberService memberService;

    @Test
    @DisplayName(" 회원 저장 성공 ")
    void saveMember() throws Exception {
        // given
        MemberDto.Request memberJoinRequest = MemberDto.Request.builder()
                .id(999L)
                .name("TESTER")
                .description("TESTER DESCRIPTION")
                .build();

        Member mem = memberJoinRequest.toEntity();
        // given(memberService.saveMember(memberJoinRequest)).willReturn(new MemberDto.Response(mem));
        given(memberService.saveMember(/*memberJoinRequest*/any())).willReturn(new MemberDto.Response(mem));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        String memberJson = objectMapper.writeValueAsString(memberJoinRequest);


        // when
        final ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.post("/v2/member")
                        .content(memberJson)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(999L))
                .andExpect(jsonPath("$.data.name").value("TESTER"))
                .andExpect(jsonPath("$.data.description").value("TESTER DESCRIPTION"))
                .andDo(print());

    }

    @Test
    @DisplayName(" 회원 조회 by ID ")
    void findMemberById() throws Exception {
        // given
        Long mockMemberId = 999L;
        Member mem = Member.builder()
                .id(mockMemberId)
                .name("TESTER")
                .description("TESTER DESCRIPTION")
                .build();

        given(memberService.getMyInfo(mockMemberId)).willReturn(new MemberDto.Response(mem));

        // when
        Long memberId = 999L;
        final ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.get("/v2/member/{memberId}", memberId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(999L))
                .andExpect(jsonPath("$.data.name").value("TESTER"))
                .andExpect(jsonPath("$.data.description").value("TESTER DESCRIPTION"))
                .andDo(print());

    }

    @Test
    @DisplayName(" 회원 저장 실패 (valid check) ")
    void saveMemberFail() throws Exception {
        // given
        MemberDto.Request memberJoinRequest = MemberDto.Request.builder()
                .id(999L)
                .name("TESTER")
                .build();

        Member mem = memberJoinRequest.toEntity();
        // given(memberService.saveMember(memberJoinRequest)).willReturn(new MemberDto.Response(mem));
        given(memberService.saveMember(/*memberJoinRequest*/any())).willReturn(new MemberDto.Response(mem));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        String memberJson = objectMapper.writeValueAsString(memberJoinRequest);


        // when
        final ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.post("/v2/member")
                        .content(memberJson)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        result
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.code").value("C002"))
                .andDo(print());


//        assertThrows(BadRequestException.class, () -> mockMvc.perform(
//                MockMvcRequestBuilders.post("/v2/member")
//                        .content(memberJson)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .contentType(MediaType.APPLICATION_JSON)
//        ));
    }
}
