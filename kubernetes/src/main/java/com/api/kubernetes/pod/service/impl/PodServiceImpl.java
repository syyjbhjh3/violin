package com.api.kubernetes.pod.service.impl;

import com.api.kubernetes.common.model.dto.KubernetesDTO;
import com.api.kubernetes.common.model.dto.ResultDTO;
import com.api.kubernetes.common.model.enums.Message;
import com.api.kubernetes.common.model.enums.Status;
import com.api.kubernetes.common.util.k8sClient.UserClusterClientManager;
import com.api.kubernetes.pod.service.PodService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PodServiceImpl implements PodService {

    /* Repository */

    /* Util */
    UserClusterClientManager k8sClientManager;

    public ResultDTO retrieve(KubernetesDTO kubernetesDTO) {
        return new ResultDTO<>(Status.SUCCESS, Message.CLUSTER_SEARCH_SUCCESS.getMessage());
    }
}
