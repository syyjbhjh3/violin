import './assets/css/App.css';
import { Routes, Route, Navigate } from 'react-router-dom';
import {} from 'react-router-dom';
import AuthLayout from './layouts/auth';
import AdminLayout from './layouts/admin';
import RTLLayout from './layouts/rtl';
import {
    ChakraProvider,
    // extendTheme
} from '@chakra-ui/react';
import initialTheme from './theme/theme'; //  { themeGreen }
import React, { useState, useEffect } from 'react';
import { useAuthStore } from "./store/useAuthStore";
import { Center, Spinner } from "@chakra-ui/icons";

export default function Main() {
    const [currentTheme, setCurrentTheme] = useState(initialTheme);
    const [isLoading, setIsLoading] = useState(true); // 초기화 상태 관리

    const rehydrate = useAuthStore((state) => state.rehydrate);
    const { userInfo } = useAuthStore((state) => state);

    // useEffect(() => {
    //     async function initializeUserInfo() {
    //         await rehydrate();
    //         setIsLoading(false);
    //     }
    //     initializeUserInfo();
    // }, [rehydrate]);
    //
    // if (isLoading) {
    //     return (
    //         <Center height="100vh">
    //             <Spinner size="xl" />
    //         </Center>
    //     );
    // }
    //
    // // rehydrate 완료 후 userInfo가 없으면 로그인 페이지로 리다이렉트
    // if (!userInfo) {
    //     return <Navigate to="/auth/sign-in" replace />;
    // }

    return (
        <ChakraProvider theme={currentTheme}>
            <Routes>
                <Route path="auth/*" element={<AuthLayout />} />
                <Route
                    path="admin/*"
                    element={
                        <AdminLayout
                            theme={currentTheme}
                            setTheme={setCurrentTheme}
                        />
                    }
                />
                <Route
                    path="rtl/*"
                    element={
                        <RTLLayout
                            theme={currentTheme}
                            setTheme={setCurrentTheme}
                        />
                    }
                />
                <Route path="/" element={<Navigate to="/auth" replace />} />
            </Routes>
        </ChakraProvider>
    );
}
