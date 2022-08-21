package com.codestates.soloproject.v1.service;

import com.codestates.soloproject.v1.entity.CompanyType;
import com.codestates.soloproject.v1.entity.Member;
import com.codestates.soloproject.v1.repository.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    //전체 조회
    public Page<Member> getMembers(int page, int size) {
        return memberRepository.findAll(PageRequest.of(page, size, Sort.by("id").descending()));
    }

    //조건 조회
    public List<Member> getMembersByOption(int page, int size, Long typeCode, Long locationCode) {
        List<Member> memberOpt = memberRepository.findByOption(typeCode, locationCode);
        return memberOpt;
    }
}
