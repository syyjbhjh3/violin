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
                return new ResultDTO<>(Status.SUCCESS, Message.SEARCH_SUCCESS.getMessage());
            } else {
                return new ResultDTO<>(Status.SUCCESS, Message.SEARCH_SUCCESS.getMessage());
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResultDTO<>(Status.SUCCESS, e.getMessage());
        }
    }

    public ResultDTO create(ClusterDTO clusterDTO) {
        ClusterDTO createDto = clusterDTO.builder()
                .clusterName(clusterDTO.getClusterName())
                .url(clusterDTO.getUrl())
                .userId(clusterDTO.getUserId())
                .useYn("Y")
                .status("UP")
                .build();

        ClusterEntity clusterEntity = createDto.toEntity();
        clusterRepository.save(clusterEntity);

        return new ResultDTO(
                Status.SUCCESS,
                Message.SEARCH_SUCCESS.message);
    }

    public ResultDTO update(ClusterDTO clusterDTO) {
        ClusterEntity clusterEntity = clusterRepository.findByClusterId(clusterDTO.getClusterId());

        if (clusterEntity != null) {
            ClusterDTO updateDto = clusterDTO.builder()
                    .clusterId(clusterEntity.getClusterId())
                    .clusterName(clusterDTO.getClusterName())
                    .url(clusterDTO.getUrl())
                    .userId(clusterEntity.getUserId())
                    .useYn(clusterEntity.getUseYn())
                    .status(clusterEntity.getStatus())
                    .build();

            ClusterEntity updateDtoEntity = updateDto.toEntity();
            clusterRepository.save(updateDtoEntity);

            return new ResultDTO<>(Status.SUCCESS, Message.SEARCH_SUCCESS.message);
        } else {
            return new ResultDTO<>(Status.FAIL, Message.SEARCH_SUCCESS.message);
        }
    }

    public ResultDTO delete(ClusterDTO clusterDTO) {
        ClusterEntity clusterEntity = clusterRepository.findByClusterId(clusterDTO.getClusterId());

        if (clusterEntity != null) {

        } else {

        }
        return new ResultDTO(
                Status.SUCCESS,
                Message.SEARCH_SUCCESS.message);
    }

    public ResultDTO retrieve(ClusterDTO clusterDTO) {
        return new ResultDTO(
                Status.SUCCESS,
                Message.SEARCH_SUCCESS.message);
    }

    public ResultDTO datail(ClusterDTO clusterDTO) {
        return new ResultDTO(
                Status.SUCCESS,
                Message.SEARCH_SUCCESS.message);
    }
}
