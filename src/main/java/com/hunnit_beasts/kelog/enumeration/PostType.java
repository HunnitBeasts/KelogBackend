package com.hunnit_beasts.kelog.enumeration;

public enum PostType {
    NORMAL(1), //일반게시물
    INCOMPLETE(2), //임시 게시물
    NOTICE(3); //공지사항

    private final Integer typeNum;

    PostType(Integer typeNum) {this.typeNum = typeNum;}

    public Integer getTypeNum() {return typeNum;} //enum이 가지고있는 값 반환

    public static PostType ofNum(Integer typeNum) {
        PostType result = null;

        for (PostType postType : PostType.values()) {
            if(postType.getTypeNum().equals(typeNum)) {
                result = postType;
            }
        }
        return result;
    } //이넘으로 변환
}
