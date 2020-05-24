package com.spintech.testtask.repository.tv.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spintech.testtask.repository.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TvEntity {

    @Id
    private Long id;

    @Column
    private String name;

    @Column
    private double averageVote;

    @Column
    @ManyToMany
    @JsonIgnore
    private Set<User> watchers = new HashSet<>();
}
