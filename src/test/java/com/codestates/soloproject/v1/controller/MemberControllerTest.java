package com.codestates.soloproject.v1.controller;

import com.codestates.soloproject.v1.entity.CompanyLocation;
import com.codestates.soloproject.v1.entity.CompanyType;
import com.codestates.soloproject.v1.entity.Member;
import com.codestates.soloproject.v1.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static com.codestates.soloproject.v1.ApiDocumentUtils.getResponsePreProcessor;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @Test
    public void getMembersTest() throws Exception {
        CompanyType companyType = new CompanyType();
        companyType.setTypeCode(101);
        companyType.setTypeName("임대업");

        CompanyLocation companyLocation = new CompanyLocation();
        companyLocation.setLocationCode(1);
        companyLocation.setLocationName("서울");

        Member member1 = new Member(1l, "홍길동", "1234", "남", "대성");
        member1.setCompanyType(companyType);
        member1.setCompanyLocation(companyLocation);

        Member member2 = new Member(2l, "김민희", "5678", "여", "마라");
        member2.setCompanyType(companyType);
        member2.setCompanyLocation(companyLocation);

        List<Member> memberList = new ArrayList<>();

        //given
        given(memberService.findMembers())
                .willReturn(memberList);

        ResultActions actions = mockMvc.perform(
                get("/v1/members")
                        .accept(MediaType.APPLICATION_JSON)
        );

        MvcResult result = actions
                .andExpect(status().isOk())
                .andDo(document(
                        "get-members",
                        getResponsePreProcessor(),
                        responseFields(
                                List.of(
                                        fieldWithPath("data[]").type(JsonFieldType.ARRAY).description("결과 데이터"),
                                        fieldWithPath("data[].id").type(JsonFieldType.NUMBER).description("회원 ID"),
                                        fieldWithPath("data[].name").type(JsonFieldType.STRING).description("이름"),
                                        fieldWithPath("data[].password").type(JsonFieldType.STRING).description("비밀번호"),
                                        fieldWithPath("data[].gender").type(JsonFieldType.STRING).description("젠더"),
                                        fieldWithPath("data[].companyName").type(JsonFieldType.STRING).description("회사명"),
                                        fieldWithPath("data[].companyType").type(JsonFieldType.OBJECT).description("업종 정보"),
                                        fieldWithPath("data[].companyType.typeCode").type(JsonFieldType.NUMBER).description("업종 코드"),
                                        fieldWithPath("data[].companyType.typeName").type(JsonFieldType.STRING).description("업종 이름"),
                                        fieldWithPath("data[].companyLocation").type(JsonFieldType.OBJECT).description("위치 정보"),
                                        fieldWithPath("data[].companyLocation.locationCode").type(JsonFieldType.NUMBER).description("위치 코드"),
                                        fieldWithPath("data[].companyLocation.locationName").type(JsonFieldType.STRING).description("위치 이름")
                                )
                        )

                ))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }


}