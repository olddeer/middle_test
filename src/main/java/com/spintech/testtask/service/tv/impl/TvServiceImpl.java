package com.spintech.testtask.service.tv.impl;

import com.spintech.testtask.repository.tv.TvRepository;
import com.spintech.testtask.repository.tv.entity.TvEntity;
import com.spintech.testtask.repository.user.UserRepository;
import com.spintech.testtask.repository.user.entity.User;
import com.spintech.testtask.service.tmdb.TmdbApi;
import com.spintech.testtask.service.tv.TvService;
import com.spintech.testtask.service.tv.model.TvDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

@Service
public class TvServiceImpl implements TvService {

    private final TmdbApi tmdbApi;
    private final ConversionService conversionService;
    private final TvRepository repository;
    private final UserRepository userRepository;


    @Autowired
    public TvServiceImpl(TmdbApi tmdbApi, @Qualifier("mvcConversionService") ConversionService conversionService,
                         TvRepository repository, UserRepository userRepository) {
        this.tmdbApi = tmdbApi;
        this.conversionService = conversionService;
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @Override
    public List<TvDto> getWatchedTvList(long userId) {
        return repository.getAllByWatchers_Id(userId)
                .stream()
                .map(tvEntity -> conversionService.convert(tvEntity, TvDto.class))
                .collect(toList());
    }

    @Override
    public void updateWatchedTvList(long userId, long tvId, boolean isWatched) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        TvEntity tvToUpdate = repository.findById(tvId)
                .orElse(repository.save(requireNonNull(conversionService.convert(tmdbApi.getTvById(tvId), TvEntity.class))));

        if(isWatched) {
            tvToUpdate.getWatchers().add(user);
            user.getWatchedTvs().add(tvToUpdate);
        } else {
            tvToUpdate.getWatchers().remove(user);
            user.getWatchedTvs().removeIf(tv -> tv.getId().equals(tvId));
        }
        repository.save(tvToUpdate);
        userRepository.save(user);
    }
}
