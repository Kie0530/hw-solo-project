package com.codestates.soloproject.v1.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Primary;

import javax.persistence.*;

@Entity
@Table(name = "COMPANY_TYPE")
@Data
@NoArgsConstructor
public class CompanyType {
    @Id
    @Column(name = "TYPE_CODE")
    private int typeCode;
//    private int typeCode;

    @Column(nullable = false, unique = true)
    private String typeName;
}
