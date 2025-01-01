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
    Button,
    Flex,
    Grid,
    Text,
    useColorModeValue,
    SimpleGrid,
    useDisclosure
} from '@chakra-ui/react';

// Custom components
import Banner from './components/Banner';
import TableTopCreators from './components/TableTopCreators';
import HistoryItem from './components/HistoryItem';
import NFT from 'components/card/NFT';
import Card from 'components/card/Card';
import RegistrationPopup from './popup/ClusterRegist';


// Assets
import Nft1 from 'assets/img/nfts/Nft1.png';
import Nft2 from 'assets/img/nfts/Nft2.png';
import Nft3 from 'assets/img/nfts/Nft3.png';
import Nft4 from 'assets/img/nfts/Nft4.png';
import Nft5 from 'assets/img/nfts/Nft5.png';
import Nft6 from 'assets/img/nfts/Nft6.png';
import tableDataTopCreators from './variables/tableDataTopCreators';

export default function Cluster() {
    // Chakra Color Mode
    const textColor = useColorModeValue('secondaryGray.900', 'white');
    const textColorBrand = useColorModeValue('brand.500', 'white');

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
                        <Text
                            mt="45px"
                            mb="36px"
                            color={textColor}
                            fontSize="2xl"
                            ms="24px"
                            fontWeight="700"
                        >
                            Recently Added
                        </Text>
                        <SimpleGrid
                            columns={{ base: 1, md: 3 }}
                            gap="20px"
                            mb={{ base: '20px', xl: '0px' }}
                        >
                            <NFT
                                name="Swipe Circles"
                                author="By Peter Will"
                                bidders={[
                                ]}
                                image={Nft4}
                                currentbid="0.91 ETH"
                                download="#"
                                btn="123"
                            />
                            <NFT
                                name="Colorful Heaven"
                                author="By Mark Benjamin"
                                bidders={[
                                ]}
                                image={Nft5}
                                currentbid="0.91 ETH"
                                download="#"
                                btn="123"
                            />
                            <NFT
                                name="3D Cubes Art"
                                author="By Manny Gates"
                                bidders={[
                                ]}
                                image={Nft6}
                                currentbid="0.91 ETH"
                                download="#"
                                btn="123"
                            />
                        </SimpleGrid>
                    </Flex>
                </Flex>
                <Flex
                    flexDirection="column"
                    gridArea={{ xl: '1 / 3 / 2 / 4', '2xl': '1 / 2 / 2 / 3' }}
                >
                    <Card px="0px" mb="20px">
                        <TableTopCreators tableData={tableDataTopCreators} />
                    </Card>
                    <Card p="0px">
                        <Flex
                            align={{ sm: 'flex-start', lg: 'center' }}
                            justify="space-between"
                            w="100%"
                            px="22px"
                            py="18px"
                        >
                            <Text
                                color={textColor}
                                fontSize="xl"
                                fontWeight="600"
                            >
                                History
                            </Text>
                            <Button variant="action">See all</Button>
                        </Flex>

                        <HistoryItem
                            name="Colorful Heaven"
                            author="By Mark Benjamin"
                            date="30s ago"
                            image={Nft5}
                            price="0.91 ETH"
                        />
                        <HistoryItem
                            name="Abstract Colors"
                            author="By Esthera Jackson"
                            date="58s ago"
                            image={Nft1}
                            price="0.91 ETH"
                        />
                        <HistoryItem
                            name="ETH AI Brain"
                            author="By Nick Wilson"
                            date="1m ago"
                            image={Nft2}
                            price="0.91 ETH"
                        />
                        <HistoryItem
                            name="Swipe Circles"
                            author="By Peter Will"
                            date="1m ago"
                            image={Nft4}
                            price="0.91 ETH"
                        />
                        <HistoryItem
                            name="Mesh Gradients "
                            author="By Will Smith"
                            date="2m ago"
                            image={Nft3}
                            price="0.91 ETH"
                        />
                        <HistoryItem
                            name="3D Cubes Art"
                            author="By Manny Gates"
                            date="3m ago"
                            image={Nft6}
                            price="0.91 ETH"
                        />
                    </Card>
                </Flex>
            </Grid>
            {/* Delete Product */}
            <RegistrationPopup isOpen={isOpen} onClose={onClose} />
        </Box>
    );
}
