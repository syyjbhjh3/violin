import React, { useState, useEffect, useCallback } from 'react';
import { Card, Flex, Text, Select, Box } from '@chakra-ui/react';
import PieChart from 'components/charts/PieChart';

// Helper function to generate dynamic colors
const generateColors = (length: number): string[] => {
    const colors: string[] = [];
    for (let i = 0; i < length; i++) {
        const hue = Math.floor(Math.random() * 360);
        const saturation = Math.random() * 50 + 50;
        const lightness = Math.random() * 30 + 60;
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

const Conversion: React.FC<ConversionProps> = ({ chartTitle, pieChartData, ...rest }) => {
    const [selectedOption, setSelectedOption] = useState('monthly');
    const [chartKey, setChartKey] = useState(0);
    const [isChartReady, setIsChartReady] = useState(false);
    const [chartOptions, setChartOptions] = useState(null);
    const [pieChartColors, setPieChartColors] = useState<string[]>([]);

    const textColor = 'black';
    const cardColor = 'white';
    const cardShadow = '0px 18px 40px rgba(112, 144, 176, 0.12)';

    const totalNodes = pieChartData.reduce((sum, cluster) => sum + cluster.currentData, 0);
    const pieChartValues = pieChartData.map(cluster =>
        (cluster.currentData / totalNodes) * 100
    );

    // Generate colors when pieChartData length changes
    useEffect(() => {
        const colors = generateColors(pieChartData.length);
        setPieChartColors(colors);
    }, [pieChartData.length]);

    const dynamicPieChartOptions = useCallback(() => ({
        labels: pieChartData.map(cluster => cluster.clusterName),
        colors: pieChartColors,
        chart: {
            type: 'pie',
            height: '100%',
            width: '100%',
        },
        legend: {
            show: false,
        },
        dataLabels: {
            enabled: false,
        },
        tooltip: {
            enabled: true,
            theme: 'dark',
            y: {
                formatter: (value: number) => `${value.toFixed(1)}%`
            }
        },
        responsive: [{
            breakpoint: 480,
            options: {
                chart: {
                    width: 200
                },
                legend: {
                    position: 'bottom'
                }
            }
        }]
    }), [pieChartData, pieChartColors]);

    useEffect(() => {
        setChartKey(prevKey => prevKey + 1);
    }, [pieChartData, selectedOption]);

    useEffect(() => {
        if (pieChartColors.length > 0) {
            const options = dynamicPieChartOptions();
            setChartOptions(options);
            setIsChartReady(true);
        }
    }, [dynamicPieChartOptions, pieChartColors]);

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
                    onChange={handleOptionChange}
                    width="unset"
                    fontWeight="700"
                >
                    <option value="daily">Daily</option>
                    <option value="monthly">Monthly</option>
                    <option value="yearly">Yearly</option>
                </Select>
            </Flex>

            {isChartReady && chartOptions && (
                <PieChart
                    key={chartKey}
                    h="100%"
                    w="100%"
                    chartData={pieChartValues}
                    chartOptions={chartOptions}
                />
            )}

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
                {pieChartData.map((cluster, index) => (
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
                                {cluster.clusterName}
                            </Text>
                        </Flex>
                        <Text fontSize="lg" color={textColor} fontWeight="700">
                            {((cluster.currentData / totalNodes) * 100).toFixed(1)}%
                        </Text>
                    </Flex>
                ))}
            </Card>
        </Card>
    );
};

export default Conversion;
