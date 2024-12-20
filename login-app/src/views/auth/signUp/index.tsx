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
import axios, { AxiosError } from 'axios';
import DefaultAuth from 'layouts/auth/Default';
import illustration from 'assets/img/auth/auth.png';
import { MdOutlineRemoveRedEye } from 'react-icons/md';
import { RiEyeCloseLine } from 'react-icons/ri';
import * as process from "process";

function SignUp() {
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

        setLoading(true);
        try {
            const response = await axios.post(
                process.env.REACT_APP_AUTH_API_URL + '/signUp',
                {
                    id : email,
                    type : 1, /* No OAuth  */
                    email,
                    password,
                    name,
                },
                {
                    headers: { 'Content-Type': 'application/json' }
                }
            );

        } catch (error) {
            if (error instanceof AxiosError) {
                console.error(
                    'Sign Up Error:',
                    error.response?.data || error.message,
                );
            } else {
                console.error('Unexpected Error:', error);
            }
        } finally {
            setLoading(false);
        }
    };

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
                        Sign up and Usage our K8S Platform!
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
                        <FormLabel
                            display="flex"
                            ms="4px"
                            fontSize="sm"
                            fontWeight="500"
                            color={textColor}
                            mb="8px"
                        >
                            Email<Text color={brandStars}>*</Text>
                        </FormLabel>
                        <Input
                            isRequired={true}
                            variant="auth"
                            fontSize="sm"
                            ms={{ base: '0px', md: '0px' }}
                            type="email"
                            placeholder="mail@simmmple.com"
                            mb="24px"
                            fontWeight="500"
                            size="lg"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                        />

                        <FormLabel
                            ms="4px"
                            fontSize="sm"
                            fontWeight="500"
                            color={textColor}
                            display="flex"
                        >
                            Password<Text color={brandStars}>*</Text>
                        </FormLabel>
                        <InputGroup size="md">
                            <Input
                                isRequired={true}
                                fontSize="sm"
                                placeholder="New Password"
                                mb="24px"
                                size="lg"
                                type={show ? 'text' : 'password'}
                                variant="auth"
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                            />
                            <InputRightElement
                                display="flex"
                                alignItems="center"
                                mt="4px"
                            >
                                <Icon
                                    color={textColorSecondary}
                                    _hover={{ cursor: 'pointer' }}
                                    as={
                                        show
                                            ? RiEyeCloseLine
                                            : MdOutlineRemoveRedEye
                                    }
                                    onClick={handleClick}
                                />
                            </InputRightElement>
                        </InputGroup>

                        <FormLabel
                            ms="4px"
                            fontSize="sm"
                            fontWeight="500"
                            color={textColor}
                            display="flex"
                        >
                            Confirm Password<Text color={brandStars}>*</Text>
                        </FormLabel>
                        <InputGroup size="md">
                            <Input
                                isRequired={true}
                                fontSize="sm"
                                placeholder="Confirm Password"
                                mb="24px"
                                size="lg"
                                type={show ? 'text' : 'password'}
                                variant="auth"
                                value={confirmPassword}
                                onChange={(e) =>
                                    setConfirmPassword(e.target.value)
                                }
                            />
                        </InputGroup>

                        <FormLabel
                            display="flex"
                            ms="4px"
                            fontSize="sm"
                            fontWeight="500"
                            color={textColor}
                            mb="8px"
                        >
                            Name<Text color={brandStars}>*</Text>
                        </FormLabel>
                        <Input
                            isRequired={true}
                            variant="auth"
                            fontSize="sm"
                            ms={{ base: '0px', md: '0px' }}
                            type="email"
                            placeholder="mail@simmmple.com"
                            mb="24px"
                            fontWeight="500"
                            size="lg"
                            value={name}
                            onChange={(e) => setName(e.target.value)}
                        />

                        <Button
                            isLoading={loading}
                            onClick={handleSignUp}
                            fontSize="sm"
                            variant="brand"
                            fontWeight="500"
                            w="100%"
                            h="50"
                            mb="24px"
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
