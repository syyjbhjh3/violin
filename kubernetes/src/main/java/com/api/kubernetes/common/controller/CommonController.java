package com.api.kubernetes.common.controller;

import com.api.kubernetes.common.model.dto.ResourceDTO;
import com.api.kubernetes.common.model.dto.ResultDTO;
import com.api.kubernetes.common.model.enums.Action;
import com.api.kubernetes.common.service.CommonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CommonController {

    private final CommonService commonService;

    @PostMapping
    public ResultDTO create(@RequestHeader("X-Cluster-Id") UUID clusterId, @RequestBody ResourceDTO resourceDTO) throws IOException {
        resourceDTO.initInfo(clusterId, Action.CREATE);
        return commonService.resourceProcess(resourceDTO);
    }

    @PutMapping
    public ResultDTO update(@RequestHeader("X-Cluster-Id") UUID clusterId, @RequestBody ResourceDTO resourceDTO) throws IOException {
        resourceDTO.initInfo(clusterId, Action.UPDATE);
        return commonService.resourceProcess(resourceDTO);
    }

    @DeleteMapping
    public ResultDTO delete(@RequestHeader("X-Cluster-Id") UUID clusterId, @RequestBody ResourceDTO resourceDTO) throws IOException {
        resourceDTO.initInfo(clusterId, Action.DELETE);
        return commonService.resourceProcess(resourceDTO);
    }
}
