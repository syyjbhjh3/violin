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

import React from 'react';

// Chakra imports
import {
    Box,
    Flex,
    Grid,
    Text,
    useColorModeValue,
    SimpleGrid,
    useDisclosure
} from '@chakra-ui/react';

// Custom components
import Banner from './components/Banner';
import NFT from 'components/card/NFT';
import Card from 'components/card/Card';
import RegistrationPopup from './popup/ClusterRegist';

// Assets
import Nft1 from 'assets/img/nfts/Nft1.png';
import Nft2 from 'assets/img/nfts/Nft2.png';
import Nft3 from 'assets/img/nfts/Nft3.png';
import ComplexTable from "../default/components/ComplexTable";
import tableDataComplex from "../default/variables/tableDataComplex";

export default function Cluster() {
    const textColor = useColorModeValue('secondaryGray.900', 'white');
    const tableTitle = 'Cluster Status';

    const { isOpen, onOpen, onClose } = useDisclosure();

    return (
        <Box pt={{ base: '180px', md: '80px', xl: '80px' }}>
            {/* Main Fields */}
            <Grid
                mb="20px"
                gridTemplateColumns={{
                    xl: 'repeat(3, 1fr)',
                    '2xl': '1fr 0.46fr',
                }}
                gap={{ base: '20px', xl: '20px' }}
                display={{ base: 'block', xl: 'grid' }}
            >
                <Flex
                    flexDirection="column"
                    gridArea={{ xl: '1 / 1 / 2 / 3', '2xl': '1 / 1 / 2 / 2' }}
                >
                    <Banner />
                    <Flex direction="column">
                        <Flex
                            mt="45px"
                            mb="20px"
                            justifyContent="space-between"
                            direction={{ base: 'column', md: 'row' }}
                            align={{ base: 'start', md: 'center' }}
                        >
                            <Text
                                color={textColor}
                                fontSize="2xl"
                                ms="24px"
                                fontWeight="700"
                            >
                                K8S Resource
                            </Text>
                            <Flex
                                align="center"
                                me="20px"
                                ms={{ base: '24px', md: '0px' }}
                                mt={{ base: '20px', md: '0px' }}
                            >
                            </Flex>
                        </Flex>
                        <SimpleGrid columns={{ base: 1, md: 3 }} gap="20px">
                            <NFT
                                name="Pod"
                                author="By Esthera Jackson"
                                bidders={[
                                ]}
                                image={Nft1}
                                currentbid="0.91 ETH"
                                download="#"
                                btn="Pod"
                            />
                            <NFT
                                name="Deployment"
                                author="By Nick Wilson"
                                bidders={[
                                ]}
                                image={Nft2}
                                currentbid="0.91 ETH"
                                download="#"
                                btn="Deployment"
                            />
                            <NFT
                                name="Service"
                                author="By Will Smith"
                                bidders={[
                                ]}
                                image={Nft3}
                                currentbid="0.91 ETH"
                                download="#"
                                btn="Service"
                            />
                        </SimpleGrid>
                    </Flex>
                </Flex>
                <Flex
                    flexDirection="column"
                    gridArea={{ xl: '1 / 3 / 2 / 4', '2xl': '1 / 2 / 2 / 3' }}
                >
                    <Card p="0px" mb="20px">
                        <ComplexTable tableData={tableDataComplex} tableTitle={tableTitle} />
                    </Card>
                    {/*<Card px="0px">*/}
                    {/*    <TableTopCreators tableData={tableDataTopCreators} />*/}
                    {/*</Card>*/}
                </Flex>
            </Grid>
            {/* Delete Product */}
            <RegistrationPopup isOpen={isOpen} onClose={onClose} />
        </Box>
    );
}
