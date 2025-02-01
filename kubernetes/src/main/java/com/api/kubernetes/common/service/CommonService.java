package com.api.kubernetes.common.service;

import com.api.kubernetes.common.model.dto.ResourceDTO;
import com.api.kubernetes.common.model.dto.ResultDTO;
import org.springframework.stereotype.Service;

@Service
public interface CommonService {
    ResultDTO resourceProcess(ResourceDTO resourceDTO);
}
