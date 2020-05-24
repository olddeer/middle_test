package com.spintech.testtask.service.actor;

import com.spintech.testtask.service.actor.model.ActorDto;
import com.spintech.testtask.service.tv.model.TvDto;

import java.util.List;
import java.util.Set;

public interface ActorService {

    void addActorToFavourite(long actorId, long userId);

    void deleteActorFromFavourite(long actorId, long userId);

    List<ActorDto> getFavouriteList(long userId);

    Set<TvDto> getActorTvs(long actorId);


}
