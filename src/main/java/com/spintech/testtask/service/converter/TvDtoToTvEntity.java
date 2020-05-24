package com.spintech.testtask.service.converter;

import com.spintech.testtask.repository.tv.entity.TvEntity;
import com.spintech.testtask.service.tv.model.TvDto;
import org.springframework.core.convert.converter.Converter;

import java.util.HashSet;

public class TvDtoToTvEntity  implements Converter<TvDto, TvEntity> {
    @Override
    public TvEntity convert(TvDto tvDto) {
        return new TvEntity(tvDto.getId(), tvDto.getName(), tvDto.getAverageVote(), new HashSet<>());
    }
}
