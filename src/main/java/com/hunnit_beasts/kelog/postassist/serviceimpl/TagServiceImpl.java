package com.hunnit_beasts.kelog.postassist.serviceimpl;

import com.hunnit_beasts.kelog.common.enumeration.ErrorCode;
import com.hunnit_beasts.kelog.common.handler.exception.ExpectException;
import com.hunnit_beasts.kelog.post.entity.domain.Post;
import com.hunnit_beasts.kelog.postassist.dto.response.TagCreateResponseDTO;
import com.hunnit_beasts.kelog.postassist.entity.domain.Tag;
import com.hunnit_beasts.kelog.postassist.entity.domain.TagPost;
import com.hunnit_beasts.kelog.postassist.repository.TagJpaRepository;
import com.hunnit_beasts.kelog.postassist.repository.TagPostJpaRepository;
import com.hunnit_beasts.kelog.postassist.service.TagService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
@Transactional
public class TagServiceImpl implements TagService {

    private final TagJpaRepository tagJpaRepository;
    private final TagPostJpaRepository tagPostJpaRepository;

    @Override
    public TagCreateResponseDTO createTag(String tag) {
        if(!tagJpaRepository.existsById(tag)) {
            Tag createTag = tagJpaRepository.save(new Tag(tag));
            return new TagCreateResponseDTO(createTag.getTagName());
        }
        return new TagCreateResponseDTO(tag);
    }

    @Override
    public void createTags(List<String> tags) {
        List<Tag> tagEntities = tags.stream()
                .map(Tag::new)
                .toList();
        tagJpaRepository.saveAll(tagEntities);
    }

    @Override
    public List<TagPost> addTagPost(List<String> tags, Post post) {
        List<TagPost> tagPosts = tags.stream()
                .map(tagName -> tagJpaRepository.findById(tagName)
                        .orElseThrow(() -> new ExpectException(ErrorCode.NO_TAG_DATA_ERROR)))
                .map(tag -> new TagPost(tag, post))
                .collect(Collectors.toList());

        Iterable<TagPost> savedTagPosts = tagPostJpaRepository.saveAll(tagPosts);
        return StreamSupport.stream(savedTagPosts.spliterator(), false)
                .collect(Collectors.toList());
    }
}
