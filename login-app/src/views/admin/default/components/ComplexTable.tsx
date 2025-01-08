import {
    Box,
    Flex,
    Icon,
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
import Card from 'components/card/Card';
import Menu from 'components/menu/MainMenu';
// Assets
import { MdCancel, MdCheckCircle, MdOutlineError } from 'react-icons/md';
import React, { useEffect, useState } from 'react';
import { apiClient } from "../../../../api/axiosConfig";
import { useAuthStore } from "../../../../store/useAuthStore";
import { ApiResponse } from "../../../../types/api";
import { AxiosError } from "axios";

type RowObj = {
    clusterName: string;
    status: string;
    createdAt: string;
};

const columnHelper = createColumnHelper<RowObj>();

export default function ComplexTable(props: { tableTitle: any }) {
    const { tableTitle } = props;
    const [sorting, setSorting] = React.useState<SortingState>([]);
    const textColor = useColorModeValue('secondaryGray.900', 'white');
    const borderColor = useColorModeValue('gray.200', 'whiteAlpha.100');

    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const userInfo = useAuthStore.getState().userInfo;

        setLoading(true);

        apiClient.get<ApiResponse<any>>(`${process.env.REACT_APP_CLUSTER_API_URL}/${userInfo.id}`)
            .then((response) => {
                if (response.data.result === 'SUCCESS' && response.data.data?.length > 0) {
                    const transformedData = response.data.data.map((item: any) => ({
                        ...item,
                        createdAt: item.createdAt ? convertDate(item.createdAt) : null,
                    }));
                    setData(transformedData);
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
    }, [useAuthStore.getState().userInfo?.id]);

    const convertDate = (isoString: string): string => {
        const date = new Date(isoString);
        return date.toISOString().split("T")[0]; // YYYY-MM-DD
    };

    const columns = [
        columnHelper.accessor('clusterName', {
            id: 'clusterName',
            header: () => (
                <Text
                    justifyContent="space-between"
                    align="center"
                    fontSize={{ sm: '10px', lg: '12px' }}
                    color="gray.400"
                >
                    NAME
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
                            info.getValue() === 'ENABLE'
                                ? 'green.500'
                                : info.getValue() === 'DISABLE'
                                    ? 'red.500'
                                    : info.getValue() === 'ERROR'
                                        ? 'orange.500'
                                        : null
                        }
                        as={
                            info.getValue() === 'ENABLE'
                                ? MdCheckCircle
                                : info.getValue() === 'DISABLE'
                                    ? MdCancel
                                    : info.getValue() === 'ERROR'
                                        ? MdOutlineError
                                        : null
                        }
                    />
                    <Text color={textColor} fontSize="sm" fontWeight="700">
                        {info.getValue()}
                    </Text>
                </Flex>
            ),
        }),
        columnHelper.accessor('createdAt', {
            id: 'createdAt',
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
                <Flex align="center">
                    <Text color={textColor} fontSize="sm" fontWeight="700">
                        {info.getValue()}
                    </Text>
                </Flex>
            ),
        }),
    ];

    let defaultData: RowObj[] = [];

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

    // No data condition
    if (data.length === 0) {
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
                        {tableTitle}
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
                                                </Flex>
                                            </Th>
                                        );
                                    })}
                                </Tr>
                            ))}
                        </Thead>
                        <Tbody>
                            <Tr>
                                <Td colSpan={columns.length}>
                                    <Flex
                                        justify="center"
                                        align="center"
                                        direction="column"
                                    >
                                        <Text
                                            color={textColor}
                                            fontSize="lg"
                                            fontWeight="400"
                                            textAlign="center"
                                        >
                                            No Data Available
                                        </Text>
                                    </Flex>
                                </Td>
                            </Tr>
                        </Tbody>
                    </Table>
                </Box>
            </Card>
        );
    }

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
                    {tableTitle}
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
                            .rows.slice(0, 5)
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
