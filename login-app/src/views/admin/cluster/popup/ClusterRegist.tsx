import React, { useState, useEffect  } from 'react';
import {
    Modal,
    ModalOverlay,
    ModalContent,
    ModalHeader,
    ModalCloseButton,
    ModalBody,
    FormControl,
    FormLabel,
    Input,
    Button,
    Text,
    useColorModeValue,
    Textarea,
    Select
} from '@chakra-ui/react';

import { AxiosError } from "axios";
import { apiClient } from "../../../../api/axiosConfig";
import { ApiResponse } from "../../../../types/api";
import { useAuthStore } from "../../../../store/useAuthStore";
import { useModalStore } from "../../../../store/useModalStore";
import AlterPopup from "../../../../components/common/AlertPopup";

interface RegistrationPopupProps {
    isOpen: boolean;
    onClose: () => void;
}

const RegistrationPopup: React.FC<RegistrationPopupProps> = ({ isOpen, onClose }) => {
    const [clusterName, setClusterName] = useState('');
    const [clusterType, setClusterType] = useState('');
    const [clusterURL, setClusterURL] = useState('');

    const [kubeconfigName, setKubeconfigName] = useState('');
    const [kubeconfigType, setKubeconfigType] = useState('');
    const [kubeconfigData, setKubeconfigData] = useState('');

    const [loading, setLoading] = useState(false);

    const textColor = useColorModeValue('navy.700', 'white');
    const brandStars = useColorModeValue('brand.500', 'brand.400');

    const { openModal } = useModalStore();

    useEffect(() => {
        if (clusterName.trim() && kubeconfigType.trim()) {
            setKubeconfigName(`${clusterName}-${kubeconfigType}`);
        } else {
            setKubeconfigName(''); // 초기화
        }
    }, [clusterName, kubeconfigType]);


    const handleRegistCluster = async () => {
        if (!kubeconfigName.trim() || !kubeconfigType.trim() || !kubeconfigData.trim()) {
            return;
        }

        if (!clusterName.trim() || !clusterType.trim() || !clusterURL.trim()) {
            console.log(clusterType)
            return;
        }

        setLoading(true);

        const param = {
            clusterName,
            type : clusterType,
            url : clusterURL,
            userId : useAuthStore.getState().userInfo.id,
            kubeconfigName, kubeconfigType, kubeconfigData
        };

        apiClient.post<ApiResponse<any>>(process.env.REACT_APP_CLUSTER_API_URL + '/create', param)
            .then((response) => {
                if (response.data.result === 'SUCCESS') {
                    openModal('클러스터 등록 성공', response.data.resultMessage);
                } else {
                    openModal('클러스터 등록 실패', response.data.resultMessage);
                }
            })
            .catch((error) => {
                if (error instanceof AxiosError) {
                    const errorMessage = error.response?.data?.resultMessage || error.message;
                    openModal('클러스터 등록 실패', errorMessage);
                } else {
                    openModal('클러스터 등록 실패', 'Unexpected Error Occurred');
                }
            })
            .finally(() => {
                setLoading(false);
            });
    };

    return (
        <>
            <Modal isOpen={isOpen} onClose={onClose}>
                <ModalOverlay />
                <ModalContent>
                    <ModalHeader>Cluster Registration</ModalHeader>
                    <ModalCloseButton />
                    <ModalBody>
                        <FormControl>
                            <FormLabel
                                display="flex"
                                fontSize="sm"
                                fontWeight="500"
                                color={textColor}
                                mb="8px"
                            >
                                Cluster Name<Text color={brandStars}>*</Text>
                            </FormLabel>
                            <Input
                                required
                                fontSize="sm"
                                type="text"
                                mb="24px"
                                value={clusterName}
                                onChange={(e) => setClusterName(e.target.value)}
                            />
                            <FormLabel
                                display="flex"
                                fontSize="sm"
                                fontWeight="500"
                                color={textColor}
                                mb="8px"
                            >
                                Cluster Type<Text color={brandStars}>*</Text>
                            </FormLabel>
                            <Select
                                w="unset"
                                alignItems="center"
                                fontSize="sm"
                                fontWeight="500"
                                mb="24px"
                                value={clusterType}
                                defaultValue="K8S"
                                onChange={(e) => setClusterType(e.target.value)}
                            >
                                <option value="K8S">K8S</option>
                                <option value="GKE">GKE</option>
                                <option value="EKS">EKS</option>
                                <option value="AKS">AKS</option>
                                <option value="OPENSHIFT">OPENSHIFT</option>
                            </Select>
                            <FormLabel
                                display="flex"
                                fontSize="sm"
                                fontWeight="500"
                                color={textColor}
                                mb="8px"
                            >
                                Cluster URL<Text color={brandStars}>*</Text>
                            </FormLabel>
                            <Input
                                required
                                fontSize="sm"
                                type="url"
                                mb="24px"
                                value={clusterURL}
                                onChange={(e) => setClusterURL(e.target.value)}
                            />
                            <FormLabel
                                display="flex"
                                fontSize="sm"
                                fontWeight="500"
                                color={textColor}
                                mb="8px"
                            >
                                KubeConfig Name<Text color={brandStars}>*</Text>
                            </FormLabel>
                            <Input
                                required
                                fontSize="sm"
                                type="text"
                                mb="24px"
                                value={kubeconfigName}
                                disabled
                            />
                            <FormLabel
                                display="flex"
                                fontSize="sm"
                                fontWeight="500"
                                color={textColor}
                                mb="8px"
                            >
                                KubeConfig Type<Text color={brandStars}>*</Text>
                            </FormLabel>
                            <Input
                                required
                                fontSize="sm"
                                type="text"
                                placeholder="Admin, User, Read Only"
                                mb="24px"
                                value={kubeconfigType}
                                onChange={(e) => setKubeconfigType(e.target.value)}
                            />
                            <FormLabel
                                display="flex"
                                fontSize="sm"
                                fontWeight="500"
                                color={textColor}
                                mb="8px"
                            >
                                KubeConfig Data<Text color={brandStars}>*</Text>
                            </FormLabel>
                            <Textarea
                                required
                                fontSize="sm"
                                mb="24px"
                                value={kubeconfigData}
                                onChange={(e) => setKubeconfigData(e.target.value)}
                            />
                            <Button
                                isLoading={loading}
                                onClick={handleRegistCluster}
                                fontSize="sm"
                                variant="brand"
                                fontWeight="500"
                                w="100%"
                                mb="24px"
                            >
                                Regist
                            </Button>
                        </FormControl>
                    </ModalBody>
                </ModalContent>
            </Modal>
            <AlterPopup />
        </>
    );
};

export default RegistrationPopup;