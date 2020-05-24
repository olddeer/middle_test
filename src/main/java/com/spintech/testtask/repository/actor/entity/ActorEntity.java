package com.spintech.testtask.repository.actor.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spintech.testtask.repository.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActorEntity {

    @Id
    private long id;

    @Column
    private String name;

    @Column
    @ManyToMany
    @JsonIgnore
    private Set<User> fans = new HashSet<>();

}
