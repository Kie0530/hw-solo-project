package com.codestates.soloproject.v1.controller;

import com.codestates.soloproject.v1.dto.ResponseDto;
import com.codestates.soloproject.v1.entity.Member;
import com.codestates.soloproject.v1.service.MemberService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/members")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    //전체 조회
    @GetMapping
    public ResponseEntity getMembers(@RequestParam(value = "page", defaultValue = "1") int page,
                                     @RequestParam(value = "size", defaultValue = "10") int size) {
        Page<Member> membersPage = memberService.getMembers(page - 1, size);
        List<Member> members = membersPage.getContent();

        return new ResponseEntity<>(new ResponseDto<>(members, membersPage), HttpStatus.OK);
    }

    //조건 조회
    @GetMapping("/search")
    public ResponseEntity getMemberByType(@RequestParam(value = "page", defaultValue = "1") int page,
                                          @RequestParam(value = "size", defaultValue = "10") int size,
                                          @RequestParam(value = "type") Long typeCode,
                                          @RequestParam(value = "location") Long locationCode) {
        List<Member> members = memberService.getMembersByOption(page-1, size, typeCode, locationCode);
        System.out.println(members);
        System.out.println(typeCode);
        System.out.println(locationCode);
        System.out.println("===============================");
        Page<Member> memberPage = new PageImpl<>(members);
        return new ResponseEntity<>(new ResponseDto<>(members, memberPage), HttpStatus.OK);
    }

}
