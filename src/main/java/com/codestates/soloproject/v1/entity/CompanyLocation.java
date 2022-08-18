package com.codestates.soloproject.v1.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "COMPANY_LOCATION")
@Data
@NoArgsConstructor
public class CompanyLocation {
    @Id
//    private List<Integer> locationCode;
    private int locationCode;

    @Column(nullable = false, unique = true)
    private String locationName;
}
