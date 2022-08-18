package com.codestates.soloproject.v1.repository;

import com.codestates.soloproject.v1.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query(value = "SELECT m FROM Member m WHERE m.companyType = :companyType")
    List<Member> findByCompanyType( int companyType);

    @Query(value = "SELECT m FROM Member m WHERE m.companyLocation = :companyLocation")
    List<Member> findByCompanyLocation( int companyLocation);
}
