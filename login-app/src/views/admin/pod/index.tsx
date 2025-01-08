
// Chakra imports
import { Box, SimpleGrid } from '@chakra-ui/react';
import DevelopmentTable from 'views/admin/pod/components/DevelopmentTable';
import PieCard from 'views/admin/pod/components/PieCard';
import { apiClient } from "../../../api/axiosConfig";
import { ApiResponse } from "../../../types/api";
import { AxiosError } from "axios";
import React, { useState } from "react";
import { useAuthStore } from "../../../store/useAuthStore";

export default function Settings() {
    type RowObj = {
        name: string;
        tech: string[];
        date: string;
        progress: number;
    };

    const tableDataComplex: RowObj[] = [];
    const [loading, setLoading] = useState(true);
    const userInfo = useAuthStore.getState().userInfo;

    apiClient.get<ApiResponse<any>>(`${process.env.REACT_APP_K8S_API_URL}/pod/${userInfo.id}`)
        .then((response) => {
            if (response.data.result === 'SUCCESS' && response.data.data?.length > 0) {
                console.log(response.data);
                const transformedData = response.data.data.map((item: any) => ({
                    ...item,
                    createdAt: item.createdAt ? convertDate(item.createdAt) : null,
                }));
                //setData(transformedData);
            } else {
                /* 조회 결과 없음 */
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

    const convertDate = (isoString: string): string => {
        const date = new Date(isoString);
        return date.toISOString().split("T")[0]; // YYYY-MM-DD
    };

    return (
        <Box pt={{ base: '130px', md: '80px', xl: '80px' }}>
            <SimpleGrid
                mb="20px"
                columns={{ sm: 1, md: 3 }}
                spacing={{ base: '20px', xl: '20px' }}
            >
                <PieCard />
                <PieCard />
                <PieCard />
            </SimpleGrid>

            <SimpleGrid
                mb="20px"
                columns={{ sm: 1, md: 1 }}
                spacing={{ base: '20px', xl: '20px' }}
            >

                <DevelopmentTable tableData={tableDataComplex} />
            </SimpleGrid>
        </Box>
    );
}
