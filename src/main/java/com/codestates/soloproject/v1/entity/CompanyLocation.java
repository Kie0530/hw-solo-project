package com.codestates.soloproject.v1.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class CompanyLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long locationCode;

    @Column(nullable = false, unique = true)
    private String locationName;
}
