package com.hunnit_beasts.kelog.serviceimpl;

import com.hunnit_beasts.kelog.dto.request.tag.TagCreateRequestDTO;
import com.hunnit_beasts.kelog.dto.response.tag.TagCreateResponseDTO;
import com.hunnit_beasts.kelog.entity.domain.Tag;
import com.hunnit_beasts.kelog.repository.jpa.TagJpaRepository;
import com.hunnit_beasts.kelog.service.TagService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class TagServiceImpl implements TagService {

    private final TagJpaRepository tagJpaRepository;

    @Override
    public TagCreateResponseDTO createTag(TagCreateRequestDTO dto) {
        if(!tagJpaRepository.existsById(dto.getTagName())) {
            Tag tag = tagJpaRepository.save(new Tag(dto));
            return new TagCreateResponseDTO(tag.getTagName());
        }
        return new TagCreateResponseDTO(dto.getTagName());
    }
}
