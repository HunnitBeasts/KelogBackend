package com.hunnit_beasts.kelog.postassist.serviceimpl;

import com.hunnit_beasts.kelog.post.entity.domain.Post;
import com.hunnit_beasts.kelog.postassist.dto.response.AllTagsResponseDTO;
import com.hunnit_beasts.kelog.postassist.entity.domain.Tag;
import com.hunnit_beasts.kelog.postassist.entity.domain.TagPost;
import com.hunnit_beasts.kelog.postassist.repository.TagJpaRepository;
import com.hunnit_beasts.kelog.postassist.repository.TagPostJpaRepository;
import com.hunnit_beasts.kelog.postassist.service.TagService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public void createTagPost(List<String> tags, Post createdPost) {
        List<TagPost> tagPosts = new ArrayList<>();
        for (String tagName : tags) {
            Tag tag = tagJpaRepository.findById(tagName)
                    .orElseGet(() -> tagJpaRepository.save(new Tag(tagName)));

            TagPost tagPost = new TagPost(tag, createdPost);
            tagPosts.add(tagPost);
        }

        tagPostJpaRepository.saveAll(tagPosts);
    }

    @Override
    public AllTagsResponseDTO allTags() {
        Iterable<Tag> allTags = tagJpaRepository.findAll();

        List<String> tagNames = StreamSupport.stream(allTags.spliterator(), false)
                .map(Tag::getTagName)
                .collect(Collectors.toList());

        return new AllTagsResponseDTO(tagNames);
    }
}
