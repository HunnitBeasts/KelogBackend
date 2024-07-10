package com.hunnit_beasts.kelog.postassist.repository;

public interface SeriesQueryDSLRepository {
    Long findMaxOrderBySeriesId(Long seriesId);
}
