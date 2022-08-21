package com.codestates.soloproject.v1.repository;

import com.codestates.soloproject.v1.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

//    @Query(value = "SELECT m FROM MEMBER m WHERE m.companyType = :companyType AND m.companyLocation = :companyLocation")
//    List<Member> findByOption(@Param("companyType") Long companyType, @Param("companyLocation") Long companyLocation);
    @Query(value = "select * from member where type_code = ?1 and location_code = ?2", nativeQuery = true)
    List<Member> findByOption(Long companyType, Long companyLocation);

}
