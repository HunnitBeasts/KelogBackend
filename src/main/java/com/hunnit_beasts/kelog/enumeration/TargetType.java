package com.hunnit_beasts.kelog.enumeration;

public enum TargetType {
    LIKE, //좋아요 받았을때 (user-post)
    FOLLOW, //팔로잉 받았을때 (user-user)
    COMMENT, //댓글이 달렸을떄 (user-comment)
    SUBSCRIBE //구독한 사람이 글 올렸을때 (post-user)

}
