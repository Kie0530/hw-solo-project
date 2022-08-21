package com.codestates.soloproject.v1.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Primary;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class CompanyType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long typeCode;

    @Column(nullable = false, unique = true)
    private String typeName;
}
