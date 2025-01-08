
// Chakra imports
import { Box, SimpleGrid } from '@chakra-ui/react';
import DevelopmentTable from 'views/admin/namespace/components/DevelopmentTable';
import React, { useState} from "react";

export default function Settings() {
    type RowObj = {
        name: string;
        namespace: string;
        phase: string;
        nodeName: string;
        restartCount: string;
        startTime: string;
    };

    const [tableDataComplex, setTableDataComplex] = useState<RowObj[]>([]);

    const convertDate = (isoString: string): string => {
        const date = new Date(isoString);
        return date.toISOString().split("T")[0];
    };

    return (
        <Box pt={{ base: '130px', md: '80px', xl: '80px' }}>
            <SimpleGrid
                mb="20px"
                columns={{ sm: 1, md: 3 }}
                spacing={{ base: '20px', xl: '20px' }}
            >
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
