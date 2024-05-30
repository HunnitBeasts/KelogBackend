package com.hunnit_beasts.kelog.enumeration;

public enum TargetType {
    LIKE(1), //좋아요 받았을때 (user-post)
    FOLLOW(2), //팔로잉 받았을때 (user-user)
    COMMENT(3), //댓글이 달렸을떄 (user-comment)
    SUBSCRIBE(4); //구독한 사람이 글 올렸을때 (post-user)

    private final Integer value;

    TargetType(Integer value) {this.value = value;}

    public Integer getValue() {return value;}

    public static TargetType ofNum(Integer value) {
        TargetType result = null;

        for (TargetType targetType : TargetType.values()) {
            if(targetType.getValue().equals(value)) {
                result = targetType;
            }
        }
        return result;
    } //이넘으로 변환
}
