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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static com.codestates.soloproject.v1.ApiDocumentUtils.getRequestPreProcessor;
import static com.codestates.soloproject.v1.ApiDocumentUtils.getResponsePreProcessor;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
        companyType.setTypeCode(101L);
        companyType.setTypeName("임대업");

        CompanyLocation companyLocation = new CompanyLocation();
        companyLocation.setLocationCode(1L);
        companyLocation.setLocationName("서울");

        Member member1 = new Member(1l, "홍길동", "1234", "남", "대성");
        member1.setCompanyType(companyType);
        member1.setCompanyLocation(companyLocation);

        Member member2 = new Member(2l, "김민희", "5678", "여", "마라");
        member2.setCompanyType(companyType);
        member2.setCompanyLocation(companyLocation);

        List<Member> memberList = new ArrayList<>(List.of(member1, member2));
        Page<Member> memberPage = new PageImpl<>(memberList);

        given(memberService.getMembers(0, 10))
                .willReturn(memberPage);

        ResultActions actions = mockMvc.perform(
                get("/v1/members")
                        .param("page", "1")
                        .param("size", "10")
                        .accept(MediaType.APPLICATION_JSON)
        );

        MvcResult result = actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("pageInfo.totalElements").value(2))
                .andDo(document(
                        "get-members",
                        getResponsePreProcessor(),
                        requestParameters(
                                parameterWithName("page").description("페이지 번호"),
                                parameterWithName("size").description("페이지 목록 수")
                        ),
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
                                        fieldWithPath("data[].companyLocation.locationName").type(JsonFieldType.STRING).description("위치 이름"),
                                        fieldWithPath("pageInfo").type(JsonFieldType.OBJECT).description("페이지 데이터"),
                                        fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("페이지 번호"),
                                        fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("페이지 크기"),
                                        fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("전체 데이터 수"),
                                        fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("전체 페이지 수")
                                )
                        )

                ))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void getMembersByTypeTest() throws Exception {
        CompanyType companyType1 = new CompanyType();
        companyType1.setTypeCode(1L);
        companyType1.setTypeName("임대업");

        CompanyType companyType2 = new CompanyType();
        companyType2.setTypeCode(2L);
        companyType2.setTypeName("제조업");

        CompanyLocation companyLocation1 = new CompanyLocation();
        companyLocation1.setLocationCode(101L);
        companyLocation1.setLocationName("서울");

        CompanyLocation companyLocation2 = new CompanyLocation();
        companyLocation2.setLocationCode(102L);
        companyLocation2.setLocationName("경기");

        Member member1 = new Member(1l, "홍길동", "1234", "남", "대성");
        member1.setCompanyType(companyType1);
        member1.setCompanyLocation(companyLocation1);

        Member member2 = new Member(2l, "김민희", "5678", "여", "마라");
        member2.setCompanyType(companyType2);
        member2.setCompanyLocation(companyLocation2);

        Member member3 = new Member(3l, "성대리", "9011", "여", "아발론");
        member3.setCompanyType(companyType1);
        member3.setCompanyLocation(companyLocation1);

        List<Member> memberList = new ArrayList<>(List.of(member1, member2));
        Page<Member> memberPage = new PageImpl<>(memberList);

        given(memberService.getMembersByOption(0, 10, 1L,101L))
                .willReturn(memberList);

        ResultActions actions = mockMvc.perform(
                get("/v1/members/search")
                        .param("page", "1")
                        .param("size","10")
                        .param("type", "1")
                        .param("location", "101")
                        .accept(MediaType.APPLICATION_JSON)
        );

        MvcResult result = actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("pageInfo.totalElements").value(2))
                .andDo(document("get-membersByOption",
                        getResponsePreProcessor(),
                        requestParameters(
                                parameterWithName("page").description("페이지 번호"),
                                parameterWithName("size").description("페이지 크기"),
                                parameterWithName("type").description("업종"),
                                parameterWithName("location").description("위치")
                        ),
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
                                        fieldWithPath("data[].companyLocation.locationName").type(JsonFieldType.STRING).description("위치 이름"),
                                        fieldWithPath("pageInfo").type(JsonFieldType.OBJECT).description("페이지 데이터"),
                                        fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("페이지 번호"),
                                        fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("페이지 크기"),
                                        fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("전체 데이터 수"),
                                        fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("전체 페이지 수")
                                )
                        )
                        ))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
        }
    }


