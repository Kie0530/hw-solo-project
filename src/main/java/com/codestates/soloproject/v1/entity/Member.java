package com.codestates.soloproject.v1.entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String password;
    private String gender;
    private String companyName;

    public Member(long id, String name, String password, String gender, String companyName) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.gender = gender;
        this.companyName = companyName;
    }

    @ManyToOne
    @JoinColumn(name = "typeCode")
    private CompanyType companyType;

    @ManyToOne
    @JoinColumn(name = "locationCode")
    private CompanyLocation companyLocation;
}
