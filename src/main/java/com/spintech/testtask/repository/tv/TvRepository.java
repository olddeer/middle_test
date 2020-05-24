package com.spintech.testtask.repository.tv;

import com.spintech.testtask.repository.tv.entity.TvEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TvRepository extends JpaRepository<TvEntity, Long> {

    List<TvEntity> getAllByWatchers_Id(long id);
}
