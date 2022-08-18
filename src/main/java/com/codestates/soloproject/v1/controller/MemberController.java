package com.codestates.soloproject.v1.controller;

import com.codestates.soloproject.v1.entity.Member;
import com.codestates.soloproject.v1.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity getMembers() {
        List<Member> members = memberService.findMembers();
        return new ResponseEntity<>(members, HttpStatus.OK);
    }

    //타입 조회
    @GetMapping("/{companyType}")
    public ResponseEntity getMemberByType(@PathVariable("companyType") int companyType) {
        List<Member> members = memberService.findMemberByType(companyType);
        return new ResponseEntity<>(members, HttpStatus.OK);
    }

    //지역 조회
    @GetMapping("/{companyLocation}")
    public ResponseEntity getMemberByLocation(@PathVariable("companyLocation") int companyLocation) {
        List<Member> members = memberService.findMemberByLocation(companyLocation);
        return new ResponseEntity<>(members, HttpStatus.OK);
    }
}
