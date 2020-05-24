package com.spintech.testtask.service.tv;

import com.spintech.testtask.service.tv.model.TvDto;

import java.util.List;

public interface TvService {

    List<TvDto> getWatchedTvList(long userId);
    void updateWatchedTvList(long userId, long tvId, boolean isWatched);
}
