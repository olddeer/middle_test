package com.spintech.testtask.service.converter;

import com.spintech.testtask.repository.tv.entity.TvEntity;
import com.spintech.testtask.service.tv.model.TvDto;
import org.springframework.core.convert.converter.Converter;

public class TvEntityToTvDto implements Converter<TvEntity, TvDto> {

    @Override
    public TvDto convert(TvEntity tvEntity) {
        return TvDto.builder().id(tvEntity.getId())
                .averageVote(tvEntity.getAverageVote())
                .name(tvEntity.getName())
                .build();
    }
}
