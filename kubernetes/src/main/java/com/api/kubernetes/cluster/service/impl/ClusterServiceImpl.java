package com.api.kubernetes.cluster.service.impl;

import com.api.kubernetes.cluster.model.dto.ClusterDTO;
import com.api.kubernetes.cluster.model.entity.ClusterEntity;
import com.api.kubernetes.common.model.dto.ResultDTO;
import com.api.kubernetes.common.model.enums.Message;
import com.api.kubernetes.common.model.enums.Status;
import com.api.kubernetes.cluster.repo.ClusterRepository;
import com.api.kubernetes.cluster.service.ClusterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClusterServiceImpl implements ClusterService {

    private final ClusterRepository clusterRepository;

    private final WebClient webClient;


    public ResultDTO connect(ClusterDTO clusterDTO) {
        try {
            String response = webClient.get()
                    .uri(clusterDTO.getUrl() + "/healthz")
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            if (response != null && response.equals("ok")) {
                return new ResultDTO<>(Status.SUCCESS, Message.CLUSTER_CONNECT_SUCCESS.getMessage());
            } else {
                return new ResultDTO<>(Status.FAIL, Message.CLUSTER_CONNECT_FAIL.getMessage());
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResultDTO<>(Status.ERROR, e.getMessage());
        }
    }

    public ResultDTO create(ClusterDTO clusterDTO) {
        ResultDTO connResponse = connect(clusterDTO);

        if (connResponse.getResult().equals(Status.SUCCESS)) {
            ClusterDTO createDto = clusterDTO.builder()
                    .clusterName(clusterDTO.getClusterName())
                    .url(clusterDTO.getUrl())
                    .userId(clusterDTO.getUserId())
                    .type(clusterDTO.getType())
                    .status("UP")
                    .build();

            ClusterEntity saveEntity = clusterRepository.save(createDto.toEntity());

            if (saveEntity != null) {
                /* KubeConfig 저장 */
            }

        } else {
            return connResponse;
        }

        return new ResultDTO(
                Status.SUCCESS,
                Message.CLUSTER_CREATE_SUCCESS.message);
    }

    public ResultDTO update(ClusterDTO clusterDTO) {
        ClusterEntity clusterEntity = clusterRepository.findByClusterId(clusterDTO.getClusterId());

        if (clusterEntity != null) {
            ClusterDTO updateDto = clusterDTO.builder()
                    .clusterId(clusterEntity.getClusterId())
                    .clusterName(clusterDTO.getClusterName())
                    .url(clusterDTO.getUrl())
                    .userId(clusterEntity.getUserId())
                    .status(clusterEntity.getStatus())
                    .build();

            ClusterEntity updateEntity = updateDto.toEntity();
            clusterRepository.save(updateEntity);

            return new ResultDTO<>(Status.SUCCESS, Message.CLUSTER_UPDATE_SUCCESS.message);
        } else {
            return new ResultDTO<>(Status.FAIL, Message.CLUSTER_UPDATE_FAIL.message);
        }
    }

    public ResultDTO delete(ClusterDTO clusterDTO) {
        ClusterEntity clusterEntity = clusterRepository.findByClusterId(clusterDTO.getClusterId());

        if (clusterEntity != null) {
            ClusterDTO deleteDto = clusterDTO.builder()
                    .clusterId(clusterEntity.getClusterId())
                    .clusterName(clusterEntity.getClusterName())
                    .url(clusterEntity.getUrl())
                    .userId(clusterEntity.getUserId())
                    .status(clusterEntity.getStatus())
                    .build();

            ClusterEntity deleteEntity = deleteDto.toEntity();
            clusterRepository.save(deleteEntity);

            return new ResultDTO<>(Status.SUCCESS, Message.CLUSTER_DELETE_SUCCESS.message);
        } else {
            return new ResultDTO<>(Status.FAIL, Message.CLUSTER_DELETE_FAIL.message);
        }
    }

    public ResultDTO retrieve(ClusterDTO clusterDTO) {
        List<ClusterEntity> clusterEntityList = clusterRepository.findAllByStatus(clusterDTO.getStatus());

        ResultDTO resultDTO = new ResultDTO<>(Status.SUCCESS, Message.CLUSTER_SEARCH_SUCCESS.getMessage(), clusterEntityList);
        return resultDTO;
    }

    public ResultDTO datail(ClusterDTO clusterDTO) {
        return new ResultDTO(
                Status.SUCCESS,
                Message.CLUSTER_SEARCH_SUCCESS.message);
    }
}
