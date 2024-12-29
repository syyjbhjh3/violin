/*!
  _   _  ___  ____  ___ ________  _   _   _   _ ___   
 | | | |/ _ \|  _ \|_ _|__  / _ \| \ | | | | | |_ _| 
 | |_| | | | | |_) || |  / / | | |  \| | | | | || | 
 |  _  | |_| |  _ < | | / /| |_| | |\  | | |_| || |
 |_| |_|\___/|_| \_\___/____\___/|_| \_|  \___/|___|
                                                                                                                                                                                                                                                                                                                                       
=========================================================
* Horizon UI - v1.1.0
=========================================================

* Product Page: https://www.horizon-ui.com/
* Copyright 2022 Horizon UI (https://www.horizon-ui.com/)

* Designed and Coded by Simmmple

=========================================================

* The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

*/

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
    MdFileCopy,
} from 'react-icons/md';
import ComplexTable from 'views/admin/default/components/ComplexTable';
import TotalSpent from 'views/admin/default/components/TotalSpent';
import tableDataComplex from 'views/admin/default/variables/tableDataComplex';
import axios, { AxiosError } from "axios";
import * as process from "process";

export default function UserReports() {
    // Chakra Color Mode
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
                                    as={MdFileCopy}
                                    color={brandColor}
                                />
                            }
                        />
                    }
                    name="Total Projects"
                    value="2935"
                />
            </SimpleGrid>

            <SimpleGrid
                columns={{ base: 1, md: 1, xl: 2 }}
                gap="20px"
                mb="20px"
            >
                <ComplexTable tableData={tableDataComplex} tableTitle={tableTitle} />
                <TotalSpent />
            </SimpleGrid>
        </Box>
    );
}
