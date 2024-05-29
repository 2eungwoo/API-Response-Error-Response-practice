package study.responsepractice.Member.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import study.responsepractice.Member.Member;

public class MemberDto {

    @Builder
    @AllArgsConstructor
    public static class Request{
        private Long id;

        @NotEmpty @Size(min=3, max=10)
        private String name;

        @NotEmpty
        private String description;

        public Member toEntity(){
            return Member.builder()
                    .id(id)
                    .name(name)
                    .description(description)
                    .build();
        }
    }

    @Getter
    public static class Response{
        private final Long id;
        private final String name;
        private final String description;

        public Response(Member member){
            this.id = member.getId();
            this.name = member.getName();
            this.description = member.getDescription();
        }
    }

}
