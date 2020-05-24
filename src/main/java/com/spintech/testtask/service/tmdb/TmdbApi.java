package com.spintech.testtask.service.tmdb;

import com.spintech.testtask.service.actor.model.ActorDto;
import com.spintech.testtask.service.tv.model.TvDto;

import java.util.List;
import java.util.Set;

public interface TmdbApi {
    String popularTVShows();

    TvDto getTvById(long tvId);

    Set<TvDto> getActorsTvs(long actorId);

    ActorDto getActorById(Long actorId);
}
