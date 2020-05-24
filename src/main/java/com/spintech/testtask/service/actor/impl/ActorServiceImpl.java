package com.spintech.testtask.service.actor.impl;

import com.spintech.testtask.repository.actor.ActorRepository;
import com.spintech.testtask.repository.actor.entity.ActorEntity;
import com.spintech.testtask.repository.user.UserRepository;
import com.spintech.testtask.repository.user.entity.User;
import com.spintech.testtask.service.actor.ActorService;
import com.spintech.testtask.service.actor.model.ActorDto;
import com.spintech.testtask.service.tmdb.TmdbApi;
import com.spintech.testtask.service.tv.model.TvDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Service
public class ActorServiceImpl implements ActorService {

    private final TmdbApi tmdbApi;
    private final UserRepository userRepository;
    private final ActorRepository actorRepository;

    @Autowired
    public ActorServiceImpl(TmdbApi tmdbApi, UserRepository userRepository, ActorRepository actorRepository) {
        this.tmdbApi = tmdbApi;
        this.userRepository = userRepository;
        this.actorRepository = actorRepository;
    }

    @Override
    public void addActorToFavourite(long actorId, long userId) {
        ActorDto actorDto = tmdbApi.getActorById(actorId);
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User is not found"));
        ActorEntity actorEntity = actorRepository.findById(actorId).orElse(new ActorEntity(actorId, actorDto.getName(), new HashSet<>()));
        actorEntity.getFans().add(user);
        user.getFavouriteActors().add(actorEntity);
        actorRepository.save(actorEntity);
        userRepository.save(user);
    }

    @Override
    public void deleteActorFromFavourite(long actorId, long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User is not found"));
        ActorEntity actorEntity = actorRepository.findById(actorId).orElseThrow(() -> new RuntimeException("Actor is not found in user favourites"));
        user.getFavouriteActors().remove(actorEntity);
        actorEntity.getFans().remove(user);
        actorRepository.save(actorEntity);
        userRepository.save(user);
    }

    @Override
    public Set<TvDto> getActorTvs(long actorId) {
        return tmdbApi.getActorsTvs(actorId);
    }

    @Override
    public List<ActorDto> getFavouriteList(long userId) {
        return actorRepository.findActorEntitiesByFans_Id(userId)
                .stream()
                .map(actorEntity -> new ActorDto(actorEntity.getId(), actorEntity.getName()))
                .collect(toList());
    }
}
