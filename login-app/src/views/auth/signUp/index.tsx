import React, { useState } from 'react';
import {
    Box,
    Button,
    Flex,
    FormControl,
    FormLabel,
    Heading,
    Icon,
    Input,
    InputGroup,
    InputRightElement,
    Text,
    useColorModeValue,
} from '@chakra-ui/react';
import axios, { AxiosError } from 'axios'; // axios 추가
import DefaultAuth from 'layouts/auth/Default';
import illustration from 'assets/img/auth/auth.png';
import { MdOutlineRemoveRedEye } from 'react-icons/md';
import { RiEyeCloseLine } from 'react-icons/ri';

function SignUp() {
    // State 관리
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [name, setName] = useState('');
    const [show, setShow] = useState(false);
    const [loading, setLoading] = useState(false);

    const handleClick = () => setShow(!show);

    // API 호출 로직
    const handleSignUp = async () => {
        if (password !== confirmPassword) {
            alert('Passwords do Not match!');
            return;
        }

        try {
            const response = await axios.post(
                'https://api.example.com/signup',
                {
                    /* Type  */
                    email,
                    password,
                    name,
                },
            );
            console.log('Sign Up Success:', response.data);
        } catch (error) {
            if (error instanceof AxiosError) {
                console.error(
                    'Sign Up Error:',
                    error.response?.data || error.message,
                );
            } else {
                console.error('Unexpected Error:', error);
            }
        }
    };

    // Chakra UI Color Mode
    const textColor = useColorModeValue('navy.700', 'white');
    const textColorSecondary = 'gray.400';
    const brandStars = useColorModeValue('brand.500', 'brand.400');

    return (
        <DefaultAuth illustrationBackground={illustration} image={illustration}>
            <Flex
                maxW={{ base: '100%', md: 'max-content' }}
                w="100%"
                mx={{ base: 'auto', lg: '0px' }}
                me="auto"
                h="100%"
                alignItems="start"
                justifyContent="center"
                mb={{ base: '30px', md: '60px' }}
                px={{ base: '25px', md: '0px' }}
                mt={{ base: '40px', md: '14vh' }}
                flexDirection="column"
            >
                <Box me="auto">
                    <Heading color={textColor} fontSize="36px" mb="10px">
                        Sign Up
                    </Heading>
                    <Text
                        mb="36px"
                        ms="4px"
                        color={textColorSecondary}
                        fontWeight="400"
                        fontSize="md"
                    >
                        Sign up and Usage our Platform!
                    </Text>
                </Box>
                <Flex
                    zIndex="2"
                    direction="column"
                    w={{ base: '100%', md: '420px' }}
                    maxW="100%"
                    background="transparent"
                    borderRadius="15px"
                    mx={{ base: 'auto', lg: 'unset' }}
                    me="auto"
                    mb={{ base: '20px', md: 'auto' }}
                >
                    <FormControl>
                        <FormLabel color={textColor}>
                            Email<Text color={brandStars}>*</Text>
                        </FormLabel>
                        <Input
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            type="email"
                            placeholder="mail@simmmple.com"
                            mb="24px"
                        />

                        <FormLabel color={textColor}>
                            Password<Text color={brandStars}>*</Text>
                        </FormLabel>
                        <InputGroup size="md">
                            <Input
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                                type={show ? 'text' : 'password'}
                                placeholder="New Password"
                                mb="24px"
                            />
                            <InputRightElement>
                                <Icon
                                    as={
                                        show
                                            ? RiEyeCloseLine
                                            : MdOutlineRemoveRedEye
                                    }
                                    onClick={handleClick}
                                />
                            </InputRightElement>
                        </InputGroup>

                        <FormLabel color={textColor}>
                            Confirm Password<Text color={brandStars}>*</Text>
                        </FormLabel>
                        <InputGroup size="md">
                            <Input
                                value={confirmPassword}
                                onChange={(e) =>
                                    setConfirmPassword(e.target.value)
                                }
                                type={show ? 'text' : 'password'}
                                placeholder="Confirm Password"
                                mb="24px"
                            />
                        </InputGroup>

                        <FormLabel color={textColor}>
                            Name<Text color={brandStars}>*</Text>
                        </FormLabel>
                        <Input
                            value={name}
                            onChange={(e) => setName(e.target.value)}
                            type="text"
                            placeholder="Your Name"
                            mb="24px"
                        />

                        <Button
                            isLoading={loading}
                            onClick={handleSignUp}
                            colorScheme="teal"
                            w="100%"
                            h="50px"
                        >
                            Sign Up
                        </Button>
                    </FormControl>
                </Flex>
            </Flex>
        </DefaultAuth>
    );
}

export default SignUp;
