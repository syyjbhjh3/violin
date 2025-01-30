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

    CLUSTER_SEARCH_SUCCESS("클러스터 목록 조회가 완료되었습니다."),
    CLUSTER_SEARCH_NOT_FOUND("클러스터가 존재하지 않습니다."),

    CLUSTER_STATUS_SUCCESS("클러스터 상태 조회가 완료되었습니다"),

    /* Kubernetes Resource - POD */
    POD_SEARCH_SUCCESS("파드 목록 조회가 완료되었습니다."),
    POD_SEARCH_NOT_FOUND("파드가 존재하지 않습니다."),

    NODE_SEARCH_SUCCESS("노드 목록 조회가 완료되었습니다."),
    NODE_SEARCH_NOT_FOUND("노드가 존재하지 않습니다."),

    NAMESPACE_SEARCH_SUCCESS("네임스페이스 조회가 완료되었습니다."),

    DEPLOYMENT_SEARCH_SUCCESS("디플로이먼트 조회가 완료되었습니다."),

    PERSISTENTVOLUME_SEARCH_SUCCESS("퍼시스턴트볼륨 조회가 완료되었습니다."),

    PERSISTENTVOLUMECLAIM_SEARCH_SUCCESS("퍼시스턴트볼륨클레임 조회가 완료되었습니다."),

    RESOURCE_APPLY("Resource가 적용되었습니다."),
    RESOURCE_EMPTY("Yaml이 존재하지 않습니다."),
    RESOURCE_INVALID("Yaml이 유효하지 않습니다.");

    public final String message;

    Message(String message) {
        this.message = message;
    }
}
