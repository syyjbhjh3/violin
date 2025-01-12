import { useState } from 'react';
import { Box, Card, Flex, Text, Select } from '@chakra-ui/react';
import PieChart from 'components/charts/PieChart';

// Helper function to generate dynamic colors
const generateColors = (length: number): string[] => {
    const colors: string[] = [];
    for (let i = 0; i < length; i++) {
        const hue = Math.floor(Math.random() * 360); // Random hue
        const saturation = Math.random() * 50 + 50; // 50% to 100% saturation
        const lightness = Math.random() * 30 + 60; // 60% to 90% lightness
        colors.push(`hsl(${hue}, ${saturation}%, ${lightness}%)`);
    }
    return colors;
};

interface ConversionProps {
    chartTitle: string;
    pieChartData: Array<{
        clusterName: string;
        currentData: number;
        totalData: number;
    }>;
    [x: string]: any;
}

const Conversion = ({ chartTitle, pieChartData, ...rest }: ConversionProps) => {
    const [selectedOption, setSelectedOption] = useState('monthly'); // Option state to manage Select value

    const textColor = 'black'; // Replace with appropriate color mode hook
    const cardColor = 'white'; // Replace with appropriate color mode hook
    const cardShadow = '0px 18px 40px rgba(112, 144, 176, 0.12)';

    // 클러스터별 통합 값을 구하기
    const totalDataSum = pieChartData.reduce((sum, node) => sum + node.totalData, 0);
    //const currentDataSum = pieChartData.reduce((sum, node) => sum + node.currentData, 0);

    // 비율을 구하기
    const pieChartValues = pieChartData.map(
        (node) => (node.currentData / totalDataSum) * 100
    );

    // 각 클러스터의 색상 생성
    const pieChartColors = generateColors(pieChartData.length);

    // Update pie chart options dynamically based on selectedOption
    const dynamicPieChartOptions = {
        labels: pieChartData.map((node) => node.clusterName),
        colors: pieChartColors,
        chart: {
            width: '50px',
        },
        states: {
            hover: {
                filter: {
                    type: 'none',
                },
            },
        },
        legend: {
            show: false,
        },
        dataLabels: {
            enabled: false,
        },
        hover: { mode: 'none' },
        plotOptions: {
            donut: {
                expandOnClick: false,
                donut: {
                    labels: {
                        show: false,
                    },
                },
            },
        },
        fill: {
            colors: pieChartColors,
        },
        tooltip: {
            enabled: true,
            theme: 'dark',
        },
    };

    // Handle option change
    const handleOptionChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
        setSelectedOption(e.target.value);
    };

    return (
        <Card
            p="20px"
            alignItems="center"
            flexDirection="column"
            w="100%"
            {...rest}
        >
            <Flex
                px={{ base: '0px', '2xl': '10px' }}
                justifyContent="space-between"
                alignItems="center"
                w="100%"
                mb="8px"
            >
                <Text color={textColor} fontSize="md" fontWeight="600" mt="4px">
                    {chartTitle}
                </Text>
                <Select
                    fontSize="sm"
                    variant="subtle"
                    value={selectedOption}
                    onChange={handleOptionChange} // Handle option change
                    width="unset"
                    fontWeight="700"
                >
                    <option value="daily">Daily</option>
                    <option value="monthly">Monthly</option>
                    <option value="yearly">Yearly</option>
                </Select>
            </Flex>

            <PieChart
                h="100%"
                w="100%"
                chartData={pieChartValues}
                chartOptions={dynamicPieChartOptions}
            />

            <Card
                bg={cardColor}
                flexDirection="row"
                boxShadow={cardShadow}
                w="100%"
                p="15px"
                px="20px"
                mt="15px"
                mx="auto"
            >
                {pieChartData.map((node, index) => (
                    <Flex key={index} direction="column" py="5px" me="10px">
                        <Flex align="center">
                            <Box
                                h="8px"
                                w="8px"
                                bg={pieChartColors[index]}
                                borderRadius="50%"
                                me="4px"
                            />
                            <Text
                                fontSize="xs"
                                color="secondaryGray.600"
                                fontWeight="700"
                                mb="5px"
                            >
                                {node.clusterName}
                            </Text>
                        </Flex>
                        <Text fontSize="lg" color={textColor} fontWeight="700">
                            {((node.currentData / node.totalData) * 100).toFixed(1)}%
                        </Text>
                    </Flex>
                ))}
            </Card>
        </Card>
    );
};

export default Conversion;