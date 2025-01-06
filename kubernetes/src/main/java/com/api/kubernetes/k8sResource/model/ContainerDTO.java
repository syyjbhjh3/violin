package com.api.kubernetes.k8sResource.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ContainerDTO {
    private String name;
    private String image;
    private String imagePullPolicy;
    private List<String> ports;
    private List<String> commands;
    private List<String> args;
}
