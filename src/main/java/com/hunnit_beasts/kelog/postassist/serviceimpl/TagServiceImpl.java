package com.hunnit_beasts.kelog.postassist.serviceimpl;

import com.hunnit_beasts.kelog.common.enumeration.ErrorCode;
import com.hunnit_beasts.kelog.common.handler.exception.ExpectException;
import com.hunnit_beasts.kelog.post.entity.domain.Post;
import com.hunnit_beasts.kelog.post.repository.querydsl.PostQueryDSLRepository;
import com.hunnit_beasts.kelog.postassist.dto.convert.TagInfos;
import com.hunnit_beasts.kelog.postassist.dto.response.TagsResponseDTO;
import com.hunnit_beasts.kelog.postassist.dto.response.UserTagsResponseDTO;
import com.hunnit_beasts.kelog.postassist.entity.compositekey.TagPostId;
import com.hunnit_beasts.kelog.postassist.entity.domain.TagPost;
import com.hunnit_beasts.kelog.postassist.repository.TagPostJpaRepository;
import com.hunnit_beasts.kelog.postassist.repository.TagQueryDSLRepository;
import com.hunnit_beasts.kelog.postassist.service.TagService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class TagServiceImpl implements TagService {

    private final TagPostJpaRepository tagPostJpaRepository;

    private final PostQueryDSLRepository postQueryDSLRepository;
    private final TagQueryDSLRepository tagQueryDSLRepository;

    @Override
    public void createTagPost(List<String> tags, Post createdPost) {
        List<TagPost> tagPosts = new ArrayList<>();
        for (String tag : tags) {
            TagPost tagPost = new TagPost(tag, createdPost);
            tagPosts.add(tagPost);
        }

        tagPostJpaRepository.saveAll(tagPosts);
    }

    @Override
    public TagsResponseDTO allTags() {
        return new TagsResponseDTO(tagQueryDSLRepository.findAllTags());
    }

    @Override
    public void removeDeletedTags(Long postId, Set<String> existingTags, Set<String> newTags) {
        Set<String> tagsToRemove = existingTags.stream()
                .filter(tag -> !newTags.contains(tag))
                .collect(Collectors.toSet());

        if (tagsToRemove.isEmpty())
            return;

        List<TagPostId> tagPostIdsToRemove = tagsToRemove.stream()
                .map(tagName -> new TagPostId(tagName, postId))
                .toList();

        List<TagPost> tagPostsToRemove = tagPostIdsToRemove.stream()
                .map(tagPostId -> tagPostJpaRepository.findById(tagPostId)
                        .orElseThrow(() -> new ExpectException(ErrorCode.NO_TAG_DATA_ERROR)))
                .collect(Collectors.toList());

        tagPostJpaRepository.deleteAll(tagPostsToRemove);
    }

    @Override
    public void addNewTags(Post post, Set<String> existingTags, Set<String> newTags) {
        newTags.stream()
                .filter(tag -> !existingTags.contains(tag))
                .forEach(tag -> {
                    TagPost newTagPost = new TagPost(tag, post);
                    tagPostJpaRepository.save(newTagPost);
                });
    }

    @Override
    public Set<String> getExistingTags(Long postId) {
        return Set.copyOf(tagQueryDSLRepository.findTagNameByPostId(postId));
    }

    @Override
    public UserTagsResponseDTO userTags(Long userId) {
        Long userPostCount = postQueryDSLRepository.getUserCountByUserId(userId);
        List<TagInfos> tagInfos = tagQueryDSLRepository.findUserTagsByUserId(userId);
        return new UserTagsResponseDTO(userPostCount,tagInfos);
    }
}
