package com.hunnit_beasts.kelog.common.event;

import com.hunnit_beasts.kelog.post.dto.response.PostCreateResponseDTO;

public record PostEvent(PostCreateResponseDTO dto) {}
