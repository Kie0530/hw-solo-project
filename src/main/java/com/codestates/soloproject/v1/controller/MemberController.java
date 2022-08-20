package com.codestates.soloproject.v1.controller;

import com.codestates.soloproject.v1.dto.ResponseDto;
import com.codestates.soloproject.v1.entity.Member;
import com.codestates.soloproject.v1.service.MemberService;
import org.springframework.data.domain.Page;
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

    //타입 조회
    @GetMapping("/{companyType}")
    public ResponseEntity getMemberByType(@RequestParam(value = "page", defaultValue = "1") int page,
                                          @RequestParam(value = "size", defaultValue = "10") int size,
                                          @PathVariable("companyType") int companyType) {
        List<Member> members = memberService.findMemberByType(page, size, companyType);
        return new ResponseEntity<>(members, HttpStatus.OK);
    }

    //지역 조회
    @GetMapping("/{companyLocation}")
    public ResponseEntity getMemberByLocation(@RequestParam(value = "page", defaultValue = "1") int page,
                                              @RequestParam(value = "size", defaultValue = "10") int size,
                                              @PathVariable("companyLocation") int companyLocation) {
        List<Member> members = memberService.findMemberByLocation(page, size, companyLocation);
        return new ResponseEntity<>(members, HttpStatus.OK);
    }
}
