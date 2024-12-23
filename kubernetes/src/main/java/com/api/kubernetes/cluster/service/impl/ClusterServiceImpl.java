package com.api.kubernetes.cluster.service.impl;

import com.api.kubernetes.cluster.model.dto.ClusterDTO;
import com.api.kubernetes.common.model.dto.ResultDTO;
import com.api.kubernetes.common.model.enums.Message;
import com.api.kubernetes.common.model.enums.Status;
import com.api.kubernetes.cluster.repo.ClusterRepository;
import com.api.kubernetes.cluster.service.ClusterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClusterServiceImpl implements ClusterService {

    private final ClusterRepository clusterRepository;

    public ResultDTO create(ClusterDTO clusterDTO) {
        return new ResultDTO(
                Status.SUCCESS,
                Message.SEARCH_SUCCESS.message);
    }

    public ResultDTO update(ClusterDTO clusterDTO) {
        return new ResultDTO(
                Status.SUCCESS,
                Message.SEARCH_SUCCESS.message);
    }

    public ResultDTO delete(ClusterDTO clusterDTO) {
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
