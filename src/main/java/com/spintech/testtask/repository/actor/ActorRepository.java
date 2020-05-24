package com.spintech.testtask.repository.actor;

import com.spintech.testtask.repository.actor.entity.ActorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActorRepository extends JpaRepository<ActorEntity, Long> {
    void deleteActorEntityById(Long actorId);

    List<ActorEntity> findActorEntitiesByFans_Id(Long userId);
}
