
// Chakra imports
import { Box, SimpleGrid } from '@chakra-ui/react';
import DevelopmentTable from 'views/admin/pod/components/DevelopmentTable';
import tableDataDevelopment from 'views/admin/pod/variables/tableDataDevelopment';
import PieCard from 'views/admin/pod/components/PieCard';

export default function Settings() {
    // Chakra Color Mode
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

                <DevelopmentTable tableData={tableDataDevelopment} />
            </SimpleGrid>
        </Box>
    );
}
