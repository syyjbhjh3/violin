package com.api.kubernetes.common.service.impl;

import com.api.kubernetes.common.model.dto.ResourceDTO;
import com.api.kubernetes.common.model.dto.ResultDTO;
import com.api.kubernetes.common.model.enums.Action;
import com.api.kubernetes.common.model.enums.Message;
import com.api.kubernetes.common.model.enums.Status;
import com.api.kubernetes.common.service.CommonService;
import com.api.kubernetes.common.util.k8sClient.UserClusterClientManager;
import io.fabric8.kubernetes.api.model.HasMetadata;
import io.fabric8.kubernetes.client.KubernetesClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommonServiceImpl implements CommonService {
    private final UserClusterClientManager k8sClientManager;

    public ResultDTO resourceProcess(ResourceDTO resourceDTO) throws IOException {
        Action action = resourceDTO.getAction();
        KubernetesClient kubernetesClient = k8sClientManager.getClusterClient(resourceDTO.getClusterId());
        HasMetadata resource = parseYaml(resourceDTO.getResourceYaml(), kubernetesClient);

        if (action == Action.CREATE) {
            kubernetesClient.resource(resource).create();
        } else if (action == Action.DELETE) {
            kubernetesClient.resource(resource).createOrReplace();
        } else if (action == Action.UPDATE) {
            kubernetesClient.resource(resource).delete();
        }
        return new ResultDTO<>(Status.SUCCESS, Message.RESOURCE_APPLY.message);
    }

    private HasMetadata parseYaml(String yaml, KubernetesClient kubernetesClient) throws IOException {
        List<HasMetadata> resources = kubernetesClient.load(new ByteArrayInputStream(yaml.getBytes())).get();

        if (resources == null || resources.isEmpty()) {
            throw new IllegalArgumentException(Message.RESOURCE_EMPTY.message);
        }

        if (resources.size() > 1) {
            throw new IllegalArgumentException(Message.RESOURCE_INVALID.message);
        }
        return resources.get(0);
    }
}
