package com.api.kubernetes.cluster.controller;

import com.api.kubernetes.cluster.service.ClusterService;
import com.api.kubernetes.common.model.dto.KubernetesDTO;
import com.api.kubernetes.common.model.dto.ResultDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping("/cluster")
@RequiredArgsConstructor
public class ClusterController {

    private final ClusterService clusterService;

    /* 클러스터 생성 */
    @PostMapping
    public ResultDTO create(@RequestBody KubernetesDTO kubernetesDTO) {
        return clusterService.create(kubernetesDTO);
    }

    /* 클러스터 업데이트 */
    @PutMapping("/{clusterId}")
    public ResultDTO update(@RequestBody KubernetesDTO kubernetesDTO) {
        return clusterService.update(kubernetesDTO);
    }

    /* 클러스터 삭제 */
    @DeleteMapping("/{clusterId}")
    public ResultDTO delete(@RequestBody KubernetesDTO kubernetesDTO) {
        return clusterService.delete(kubernetesDTO);
    }

    /* 클러스터 목록 조회 */
    @GetMapping("/{userId}")
    public ResultDTO retrieve(@PathVariable String userId) {
        return clusterService.retrieve(userId);
    }

    /* 특정 클러스터 상세 조회 */
    @GetMapping("/detail/{clusterId}")
    public ResultDTO detail(@RequestBody KubernetesDTO kubernetesDTO) {
        return clusterService.detail(kubernetesDTO);
    }
}
