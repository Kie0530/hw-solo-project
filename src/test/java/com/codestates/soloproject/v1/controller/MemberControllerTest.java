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
        companyType.setTypeName("?????????");

        CompanyLocation companyLocation = new CompanyLocation();
        companyLocation.setLocationCode(1L);
        companyLocation.setLocationName("??????");

        Member member1 = new Member(1l, "?????????", "1234", "???", "??????");
        member1.setCompanyType(companyType);
        member1.setCompanyLocation(companyLocation);

        Member member2 = new Member(2l, "?????????", "5678", "???", "??????");
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
                                parameterWithName("page").description("????????? ??????"),
                                parameterWithName("size").description("????????? ?????? ???")
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data[]").type(JsonFieldType.ARRAY).description("?????? ?????????"),
                                        fieldWithPath("data[].id").type(JsonFieldType.NUMBER).description("?????? ID"),
                                        fieldWithPath("data[].name").type(JsonFieldType.STRING).description("??????"),
                                        fieldWithPath("data[].password").type(JsonFieldType.STRING).description("????????????"),
                                        fieldWithPath("data[].gender").type(JsonFieldType.STRING).description("??????"),
                                        fieldWithPath("data[].companyName").type(JsonFieldType.STRING).description("?????????"),
                                        fieldWithPath("data[].companyType").type(JsonFieldType.OBJECT).description("?????? ??????"),
                                        fieldWithPath("data[].companyType.typeCode").type(JsonFieldType.NUMBER).description("?????? ??????"),
                                        fieldWithPath("data[].companyType.typeName").type(JsonFieldType.STRING).description("?????? ??????"),
                                        fieldWithPath("data[].companyLocation").type(JsonFieldType.OBJECT).description("?????? ??????"),
                                        fieldWithPath("data[].companyLocation.locationCode").type(JsonFieldType.NUMBER).description("?????? ??????"),
                                        fieldWithPath("data[].companyLocation.locationName").type(JsonFieldType.STRING).description("?????? ??????"),
                                        fieldWithPath("pageInfo").type(JsonFieldType.OBJECT).description("????????? ?????????"),
                                        fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("????????? ??????"),
                                        fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("????????? ??????"),
                                        fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("?????? ????????? ???"),
                                        fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("?????? ????????? ???")
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
        companyType1.setTypeName("?????????");

        CompanyType companyType2 = new CompanyType();
        companyType2.setTypeCode(2L);
        companyType2.setTypeName("?????????");

        CompanyLocation companyLocation1 = new CompanyLocation();
        companyLocation1.setLocationCode(101L);
        companyLocation1.setLocationName("??????");

        CompanyLocation companyLocation2 = new CompanyLocation();
        companyLocation2.setLocationCode(102L);
        companyLocation2.setLocationName("??????");

        Member member1 = new Member(1l, "?????????", "1234", "???", "??????");
        member1.setCompanyType(companyType1);
        member1.setCompanyLocation(companyLocation1);

        Member member2 = new Member(2l, "?????????", "5678", "???", "??????");
        member2.setCompanyType(companyType2);
        member2.setCompanyLocation(companyLocation2);

        Member member3 = new Member(3l, "?????????", "9011", "???", "?????????");
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
                                parameterWithName("page").description("????????? ??????"),
                                parameterWithName("size").description("????????? ??????"),
                                parameterWithName("type").description("??????"),
                                parameterWithName("location").description("??????")
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data[]").type(JsonFieldType.ARRAY).description("?????? ?????????"),
                                        fieldWithPath("data[].id").type(JsonFieldType.NUMBER).description("?????? ID"),
                                        fieldWithPath("data[].name").type(JsonFieldType.STRING).description("??????"),
                                        fieldWithPath("data[].password").type(JsonFieldType.STRING).description("????????????"),
                                        fieldWithPath("data[].gender").type(JsonFieldType.STRING).description("??????"),
                                        fieldWithPath("data[].companyName").type(JsonFieldType.STRING).description("?????????"),
                                        fieldWithPath("data[].companyType").type(JsonFieldType.OBJECT).description("?????? ??????"),
                                        fieldWithPath("data[].companyType.typeCode").type(JsonFieldType.NUMBER).description("?????? ??????"),
                                        fieldWithPath("data[].companyType.typeName").type(JsonFieldType.STRING).description("?????? ??????"),
                                        fieldWithPath("data[].companyLocation").type(JsonFieldType.OBJECT).description("?????? ??????"),
                                        fieldWithPath("data[].companyLocation.locationCode").type(JsonFieldType.NUMBER).description("?????? ??????"),
                                        fieldWithPath("data[].companyLocation.locationName").type(JsonFieldType.STRING).description("?????? ??????"),
                                        fieldWithPath("pageInfo").type(JsonFieldType.OBJECT).description("????????? ?????????"),
                                        fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("????????? ??????"),
                                        fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("????????? ??????"),
                                        fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("?????? ????????? ???"),
                                        fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("?????? ????????? ???")
                                )
                        )
                        ))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
        }
    }


