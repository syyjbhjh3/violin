import {
    Box,
    Flex, Icon,
    Table,
    Tbody,
    Td,
    Text,
    Th,
    Thead,
    Tr,
    useColorModeValue,
} from '@chakra-ui/react';
import {
    createColumnHelper,
    flexRender,
    getCoreRowModel,
    getSortedRowModel,
    SortingState,
    useReactTable,
} from '@tanstack/react-table';
// Custom components
import Card from '../../../../../components/card/Card';
import Menu from '../../../../../components/menu/MainMenu';
import * as React from 'react';
import { useEffect, useState } from "react";
import { useAuthStore } from "../../../../../store/useAuthStore";
import { apiClient } from "../../../../../api/axiosConfig";
import { ApiResponse } from "../../../../../types/api";
import { AxiosError } from "axios";
import { MdCancel, MdCheckCircle, MdOutlineError } from "react-icons/md";

// Assets
type RowObj = {
    name: string;
    status: string;
    clusterName: string;
    persistentVolumeClaim: string;
    capacity: string;
    creationTimestamp: string;
};

const columnHelper = createColumnHelper<RowObj>();

// const columns = columnsDataCheck;
export default function ComplexTable(props: { tableData: any }) {
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const userInfo = useAuthStore.getState().userInfo;

        setLoading(true);

        apiClient.get<ApiResponse<any>>(`${process.env.REACT_APP_K8S_API_URL}/persistentVolume/${userInfo.id}`)
            .then((response) => {
                if (response.data.result === 'SUCCESS' && response.data.data?.length > 0) {
                    const transformedData = response.data.data.map((item: any) => ({
                        ...item,
                        creationTimestamp: item.creationTimestamp ? convertDate(item.creationTimestamp) : null,
                    }));
                    console.log(transformedData)
                    setData(transformedData);
                } else {
                    setData([]);
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

    const convertDate = (isoString: string): string => {
        const date = new Date(isoString);
        return date.toISOString().split("T")[0];
    };

    const { tableData } = props;
    const [sorting, setSorting] = React.useState<SortingState>([]);
    const textColor = useColorModeValue('secondaryGray.900', 'white');
    const borderColor = useColorModeValue('gray.200', 'whiteAlpha.100');
    let defaultData = tableData;
    const columns = [
        columnHelper.accessor('name', {
            id: 'name',
            header: () => (
                <Text
                    justifyContent="space-between"
                    align="center"
                    fontSize={{ sm: '10px', lg: '12px' }}
                    color="gray.400"
                >
                    Name
                </Text>
            ),
            cell: (info: any) => (
                <Flex align="center">
                    <Text color={textColor} fontSize="sm" fontWeight="700">
                        {info.getValue()}
                    </Text>
                </Flex>
            ),
        }),
        columnHelper.accessor('status', {
            id: 'status',
            header: () => (
                <Text
                    justifyContent="space-between"
                    align="center"
                    fontSize={{ sm: '10px', lg: '12px' }}
                    color="gray.400"
                >
                    STATUS
                </Text>
            ),
            cell: (info) => (
                <Flex align="center">
                    <Icon
                        w="24px"
                        h="24px"
                        me="5px"
                        color={
                            info.getValue() === 'Ready'
                                ? 'green.500'
                                : info.getValue() === 'NotReady'
                                    ? 'red.500'
                                        : null
                        }
                        as={
                            info.getValue() === 'Ready'
                                ? MdCheckCircle
                                : info.getValue() === 'NotReady'
                                    ? MdCancel
                                        : null
                        }
                    />
                    <Text color={textColor} fontSize="sm" fontWeight="700">
                        {info.getValue()}
                    </Text>
                </Flex>
            ),
        }),
        columnHelper.accessor('clusterName', {
            id: 'clusterName',
            header: () => (
                <Text
                    justifyContent="space-between"
                    align="center"
                    fontSize={{ sm: '10px', lg: '12px' }}
                    color="gray.400"
                >
                    Cluster
                </Text>
            ),
            cell: (info: any) => (
                <Flex align="center">
                    <Text color={textColor} fontSize="sm" fontWeight="700">
                        {info.getValue()}
                    </Text>
                </Flex>
            ),
        }),
        columnHelper.accessor('persistentVolumeClaim', {
            id: 'persistentVolumeClaim',
            header: () => (
                <Text
                    justifyContent="space-between"
                    align="center"
                    fontSize={{ sm: '10px', lg: '12px' }}
                    color="gray.400"
                >
                    persistentVolumeClaim
                </Text>
            ),
            cell: (info: any) => (
                <Flex align="center">
                    <Text color={textColor} fontSize="sm" fontWeight="700">
                        {info.getValue()}
                    </Text>
                </Flex>
            ),
        }),
        columnHelper.accessor('capacity', {
            id: 'capacity',
            header: () => (
                <Text
                    justifyContent="space-between"
                    align="center"
                    fontSize={{ sm: '10px', lg: '12px' }}
                    color="gray.400"
                >
                    capacity
                </Text>
            ),
            cell: (info: any) => (
                <Flex align="center">
                    <Text color={textColor} fontSize="sm" fontWeight="700">
                        {info.getValue()}
                    </Text>
                </Flex>
            ),
        }),
        columnHelper.accessor('creationTimestamp', {
            id: 'creationTimestamp',
            header: () => (
                <Text
                    justifyContent="space-between"
                    align="center"
                    fontSize={{ sm: '10px', lg: '12px' }}
                    color="gray.400"
                >
                    DATE
                </Text>
            ),
            cell: (info) => (
                <Text color={textColor} fontSize="sm" fontWeight="700">
                    {info.getValue()}
                </Text>
            ),
        }),
    ];
    const [data, setData] = React.useState(() => [...defaultData]);
    const table = useReactTable({
        data,
        columns,
        state: {
            sorting,
        },
        onSortingChange: setSorting,
        getCoreRowModel: getCoreRowModel(),
        getSortedRowModel: getSortedRowModel(),
        debugTable: true,
    });
    return (
        <Card
            flexDirection="column"
            w="100%"
            px="0px"
            overflowX={{ sm: 'scroll', lg: 'hidden' }}
        >
            <Flex
                px="25px"
                mb="8px"
                justifyContent="space-between"
                align="center"
            >
                <Text
                    color={textColor}
                    fontSize="22px"
                    fontWeight="700"
                    lineHeight="100%"
                >
                    PersistentVolume List
                </Text>
                <Menu />
            </Flex>
            <Box>
                <Table variant="simple" color="gray.500" mb="24px" mt="12px">
                    <Thead>
                        {table.getHeaderGroups().map((headerGroup) => (
                            <Tr key={headerGroup.id}>
                                {headerGroup.headers.map((header) => {
                                    return (
                                        <Th
                                            key={header.id}
                                            colSpan={header.colSpan}
                                            pe="10px"
                                            borderColor={borderColor}
                                            cursor="pointer"
                                            onClick={header.column.getToggleSortingHandler()}
                                        >
                                            <Flex
                                                justifyContent="space-between"
                                                align="center"
                                                fontSize={{
                                                    sm: '10px',
                                                    lg: '12px',
                                                }}
                                                color="gray.400"
                                            >
                                                {flexRender(
                                                    header.column.columnDef
                                                        .header,
                                                    header.getContext(),
                                                )}
                                                {{
                                                    asc: '',
                                                    desc: '',
                                                }[
                                                    header.column.getIsSorted() as string
                                                ] ?? null}
                                            </Flex>
                                        </Th>
                                    );
                                })}
                            </Tr>
                        ))}
                    </Thead>
                    <Tbody>
                        {table
                            .getRowModel()
                            .rows.slice(0, 11)
                            .map((row) => {
                                return (
                                    <Tr key={row.id}>
                                        {row.getVisibleCells().map((cell) => {
                                            return (
                                                <Td
                                                    key={cell.id}
                                                    fontSize={{ sm: '14px' }}
                                                    minW={{
                                                        sm: '150px',
                                                        md: '200px',
                                                        lg: 'auto',
                                                    }}
                                                    borderColor="transparent"
                                                >
                                                    {flexRender(
                                                        cell.column.columnDef
                                                            .cell,
                                                        cell.getContext(),
                                                    )}
                                                </Td>
                                            );
                                        })}
                                    </Tr>
                                );
                            })}
                    </Tbody>
                </Table>
            </Box>
        </Card>
    );
}
