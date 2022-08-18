package com.codestates.soloproject.v1.entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String password;
    @Column
    private String gender;
    @Column
    private String companyName;

    public Member(long id, String name, String password, String gender, String companyName) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.gender = gender;
        this.companyName = companyName;
    }

    @ManyToOne
    @JoinColumn(name = "TYPE_CODE")
    private CompanyType companyType;

    @ManyToOne
    @JoinColumn(name = "LOCATION_CODE")
    private CompanyLocation companyLocation;
}
