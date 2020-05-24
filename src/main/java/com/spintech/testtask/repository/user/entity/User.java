package com.spintech.testtask.repository.user.entity;

import com.spintech.testtask.repository.actor.entity.ActorEntity;
import com.spintech.testtask.repository.tv.entity.TvEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique=true)
    private String email;

    private String password;

    @Column
    @ManyToMany(mappedBy = "watchers")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<TvEntity> watchedTvs = new HashSet<>();

    @Column
    @ManyToMany(mappedBy = "fans")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<ActorEntity> favouriteActors = new HashSet<>();
}