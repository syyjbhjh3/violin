// Chakra imports
import {
    Box,
    Icon,
    SimpleGrid,
    useColorModeValue,
} from '@chakra-ui/react';
// Custom components
import MiniStatistics from 'components/card/MiniStatistics';
import IconBox from 'components/icons/IconBox';
import {
    MdHub,
    MdMoveUp,
    MdOutlineFilterDrama,
    MdSettingsSuggest,
    MdStars,
    MdSchema
} from 'react-icons/md';
import ComplexTable from 'views/admin/default/components/ComplexTable';
import TotalSpent from 'views/admin/default/components/TotalSpent';
import PieCard from 'views/admin/default/components/PieCard';
import { useEffect, useState } from "react";
import { useAuthStore } from "../../../store/useAuthStore";
import { apiClient } from "../../../api/axiosConfig";
import { ApiResponse } from "../../../types/api";
import { AxiosError } from "axios";
import UserActivity from "./components/UserActivity";

export default function UserReports() {
    const [loading, setLoading] = useState(true);
    const [totalClusterCnt, setTotalClusterCnt] = useState('');
    const [totalPodCnt, setTotalPodCnt] = useState('');
    const [totalNodeCnt, setTotalNodeCnt] = useState('');
    const [totalNamespaceCnt, setTotalNamespaceCnt] = useState('');
    const [totalSvcCnt, setTotalSvcCnt] = useState('');
    const [totalDeployCnt, setTotalDeployCnt] = useState('');
    const [nodePieChartData, setNodePieChartData] = useState([]);
    const [podPieChartData, setPodPieChartData] = useState([]);

    const nodesChartTitle = 'Node Operation rate';
    const podsChartTitle = 'Pod Operation rate';

    useEffect(() => {
        const userInfo = useAuthStore.getState().userInfo;

        setLoading(true);

        apiClient.get<ApiResponse<any>>(`${process.env.REACT_APP_CLUSTER_API_URL}/status/${userInfo.id}`)
            .then((response) => {
                if (response.data.result === 'SUCCESS') {
                    const status = response.data.data;

                    setTotalClusterCnt(status.totalClusters);
                    setTotalPodCnt(status.totalPods);
                    setTotalNodeCnt(status.totalNodes);
                    setTotalNamespaceCnt(status.totalNamespaces);
                    setTotalSvcCnt(status.totalServices);
                    setTotalDeployCnt(status.totalDeployments);

                    setNodePieChartData(status.nodePieChart || []);
                    setPodPieChartData(status.podPieChart || []);
                }
            })
            .catch((error) => {
                if (error instanceof AxiosError) {
                    const errorMessage = error.response?.data?.resultMessage || error.message;
                    console.error(errorMessage);
                }
            })
            .finally(() => {
                setLoading(false);
            });
    }, [useAuthStore.getState().userInfo?.id]);

    const brandColor = useColorModeValue('brand.500', 'white');
    const boxBg = useColorModeValue('secondaryGray.300', 'whiteAlpha.100');
    const tableTitle = 'Cluster Status';
    return (
        <Box pt={{ base: '130px', md: '80px', xl: '80px' }}>
            <SimpleGrid
                columns={{ base: 1, md: 2, lg: 3, '2xl': 6 }}
                gap="20px"
                mb="20px"
            >
                <MiniStatistics
                    startContent={
                        <IconBox
                            w="56px"
                            h="56px"
                            bg={boxBg}
                            icon={
                                <Icon
                                    w="32px"
                                    h="32px"
                                    as={MdHub}
                                    color={brandColor}
                                />
                            }
                        />
                    }
                    name="Connect Cluster"
                    value={totalClusterCnt}
                />

                <MiniStatistics
                    startContent={
                        <IconBox
                            w="56px"
                            h="56px"
                            bg={boxBg}
                            icon={
                                <Icon
                                    w="32px"
                                    h="32px"
                                    as={MdSettingsSuggest}
                                    color={brandColor}
                                />
                            }
                        />
                    }
                    name="Total Node"
                    value={totalNodeCnt}
                />

                <MiniStatistics
                    startContent={
                        <IconBox
                            w="56px"
                            h="56px"
                            bg={boxBg}
                            icon={
                                <Icon
                                    w="32px"
                                    h="32px"
                                    as={MdStars}
                                    color={brandColor}
                                />
                            }
                        />
                    }
                    name="Total Namespace"
                    value={totalNamespaceCnt}
                />

                <MiniStatistics
                    startContent={
                        <IconBox
                            w="56px"
                            h="56px"
                            bg={boxBg}
                            icon={
                                <Icon
                                    w="32px"
                                    h="32px"
                                    as={MdOutlineFilterDrama}
                                    color={brandColor}
                                />
                            }
                        />
                    }
                    name="Total Pod"
                    value={totalPodCnt}
                />

                <MiniStatistics
                    startContent={
                        <IconBox
                            w="56px"
                            h="56px"
                            bg={boxBg}
                            icon={
                                <Icon
                                    w="32px"
                                    h="32px"
                                    as={MdSchema}
                                    color={brandColor}
                                />
                            }
                        />
                    }
                    name="Total Service"
                    value={totalSvcCnt}
                />

                <MiniStatistics
                    startContent={
                        <IconBox
                            w="56px"
                            h="56px"
                            bg={boxBg}
                            icon={
                                <Icon
                                    w="32px"
                                    h="32px"
                                    as={MdMoveUp}
                                    color={brandColor}
                                />
                            }
                        />
                    }
                    name="Total Deployment"
                    value={totalDeployCnt}
                />
            </SimpleGrid>

            <SimpleGrid
                columns={{ base: 1, md: 1, xl: 3 }}
                gap="20px"
                mb="20px"
            >
                <ComplexTable tableTitle={tableTitle} />
                <PieCard
                    chartTitle= { nodesChartTitle }
                    pieChartData= { nodePieChartData }
                />
                <PieCard
                    chartTitle= { podsChartTitle }
                    pieChartData= { podPieChartData }
                />

            </SimpleGrid>
            <SimpleGrid
                columns={{ base: 1, md: 1, xl: 2 }}
                gap="20px"
                mb="20px"
            >

                <TotalSpent />
                <UserActivity />
            </SimpleGrid>
        </Box>
    );
}
