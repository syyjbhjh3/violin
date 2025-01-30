package com.api.kubernetes.common.model.enums;

public enum Type {
    /* Kubernetes Cluster Type */
    GKE, EKS, AKS, K8S, OPENSHIFT,

    /* Kubernetes Resource */
    POD, SERVICE, DEPLOYMENT, NODE, NAMESPACE, PERSISTENTVOLUME, PERSISTENTVOLUMECLAIM;
}
