package com.api.kubernetes.pod.service.impl;

import com.api.kubernetes.pod.service.PodService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class PodServiceImpl implements PodService {

    /* Repository */

    /* Util */
    private final WebClient webClient;
}
