package com.hunnit_beasts.kelog.postassist.serviceimpl;

import com.hunnit_beasts.kelog.common.enumeration.ErrorCode;
import com.hunnit_beasts.kelog.common.handler.exception.ExpectException;
import com.hunnit_beasts.kelog.post.entity.domain.Post;
import com.hunnit_beasts.kelog.post.repository.jpa.PostJpaRepository;
import com.hunnit_beasts.kelog.postassist.dto.request.SeriesCreateRequestDTO;
import com.hunnit_beasts.kelog.postassist.dto.response.PostAddResponseDTO;
import com.hunnit_beasts.kelog.postassist.dto.response.PostPopResponseDTO;
import com.hunnit_beasts.kelog.postassist.dto.response.SeriesCreateResponseDTO;
import com.hunnit_beasts.kelog.postassist.dto.response.UserSeriesResponseDTO;
import com.hunnit_beasts.kelog.postassist.entity.compositekey.SeriesPostId;
import com.hunnit_beasts.kelog.postassist.entity.domain.Series;
import com.hunnit_beasts.kelog.postassist.entity.domain.SeriesPost;
import com.hunnit_beasts.kelog.postassist.repository.SeriesJpaRepository;
import com.hunnit_beasts.kelog.postassist.repository.SeriesPostJpaRepository;
import com.hunnit_beasts.kelog.postassist.repository.SeriesQueryDSLRepository;
import com.hunnit_beasts.kelog.postassist.service.SeriesService;
import com.hunnit_beasts.kelog.user.entity.domain.User;
import com.hunnit_beasts.kelog.user.repository.jpa.UserJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class SeriesServiceImpl implements SeriesService {

    private final PostJpaRepository postJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final SeriesJpaRepository seriesJpaRepository;
    private final SeriesPostJpaRepository seriesPostJpaRepository;

    private final SeriesQueryDSLRepository seriesQueryDSLRepository;

    @Override
    public SeriesCreateResponseDTO createSeries(Long userId, SeriesCreateRequestDTO dto) {
        User user = userJpaRepository.findById(userId)
                .orElseThrow(() -> new ExpectException(ErrorCode.NO_USER_DATA_ERROR));
        Series series = seriesJpaRepository.save(new Series(user, dto));
        return new SeriesCreateResponseDTO(series);
    }

    @Override
    public Long deleteSeries(Long seriesId) {
        if(seriesJpaRepository.existsById(seriesId))
            seriesJpaRepository.deleteById(seriesId);
        else
            throw new ExpectException(ErrorCode.NO_SERIES_DATA_ERROR);
        return seriesId;
    }

    @Override
    public UserSeriesResponseDTO readUserSeries(Long userId) {
        return seriesQueryDSLRepository.findUserSeriesResponseDTOByUserId(userId);
    }

    @Override
    public PostAddResponseDTO seriesAddPost(Long postId, Long seriesId) {
        Series series = seriesJpaRepository.findById(seriesId)
                .orElseThrow(() -> new ExpectException(ErrorCode.NO_SERIES_DATA_ERROR));
        Post post = postJpaRepository.findById(postId)
                .orElseThrow(() -> new ExpectException(ErrorCode.NO_POST_DATA_ERROR));
        if(seriesPostJpaRepository.existsById(new SeriesPostId(seriesId,postId))){
            throw new ExpectException(ErrorCode.DUPLICATION_SERIES_POST_ERROR);
        }else {
            Long maxOrder = seriesQueryDSLRepository.findMaxOrderBySeriesId(series.getId());
            SeriesPost seriesPost = seriesPostJpaRepository.save(new SeriesPost(post,series,maxOrder));
            return new PostAddResponseDTO(seriesPost);
        }

    }

    @Override
    public PostPopResponseDTO seriesPopPost(Long postId, Long seriesId) {
        Series series = seriesJpaRepository.findById(seriesId)
                .orElseThrow(() -> new ExpectException(ErrorCode.NO_SERIES_DATA_ERROR));
        Post post = postJpaRepository.findById(postId)
                .orElseThrow(() -> new ExpectException(ErrorCode.NO_POST_DATA_ERROR));
        SeriesPostId id = new SeriesPostId(seriesId,postId);
        if(seriesPostJpaRepository.existsById(id)){
            seriesPostJpaRepository.deleteById(id);
            return new PostPopResponseDTO(post.getId(),series.getId());
        }else
            throw new ExpectException(ErrorCode.NOT_EXIST_SERIES_POST_ERROR);
    }
}
