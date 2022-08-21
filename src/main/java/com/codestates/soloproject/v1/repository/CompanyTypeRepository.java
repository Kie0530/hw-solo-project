package com.codestates.soloproject.v1.repository;

import com.codestates.soloproject.v1.entity.CompanyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyTypeRepository extends JpaRepository<CompanyType, Long> {
}
