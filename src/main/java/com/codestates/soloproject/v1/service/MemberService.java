package com.codestates.soloproject.v1.service;

import com.codestates.soloproject.v1.entity.Member;
import com.codestates.soloproject.v1.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    //전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    //타입 조회
    public List<Member> findMemberByType(int companyType) {
        List<Member> memberOpt = memberRepository.findByCompanyType(companyType);
        return memberOpt;
    }
    //지역 조회
    public List<Member> findMemberByLocation(int companyLocation) {
        List<Member> memberOpt = memberRepository.findByCompanyLocation(companyLocation);
        return memberOpt;
    }
}
