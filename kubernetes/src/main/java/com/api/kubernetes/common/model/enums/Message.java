package com.api.kubernetes.common.model.enums;

import lombok.Getter;

@Getter
public enum Message {
    /**
    *   Convention : METHOD_STATUS
    **/

    /* Cluster Message */
    CLUSTER_CONNECT_SUCCESS("클러스터 연결이 확인되었습니다."),
    CLUSTER_CONNECT_FAIL("클러스터 연결에 실패하였습니다."),
    CLUSTER_CREATE_SUCCESS("클러스터 생성이 완료되었습니다."),
    CLUSTER_CREATE_FAIL("클러스터 생성에 실패하였습니다."),
    CLUSTER_UPDATE_SUCCESS("클러스터 수정이 완료되었습니다."),
    CLUSTER_UPDATE_FAIL("클러스터 수정에 실패하였습니다."),
    CLUSTER_DELETE_SUCCESS("클러스터 삭제가 완료되었습니다."),
    CLUSTER_DELETE_FAIL("클러스터 삭제에 실패하였습니다."),

    CLUSTER_SEARCH_SUCCESS("클러스터 목록 조회가 완료되었습니다.");

    public final String message;

    Message(String message) {
        this.message = message;
    }
}
