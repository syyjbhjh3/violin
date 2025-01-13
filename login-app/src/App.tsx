import './assets/css/App.css';
import { Routes, Route, Navigate } from 'react-router-dom';
import AuthLayout from './layouts/auth';
import AdminLayout from './layouts/admin';
import RTLLayout from './layouts/rtl';
import {
    ChakraProvider,
    Center,
    Spinner
} from '@chakra-ui/react';
import initialTheme from './theme/theme';
import React, { useState, useEffect, ReactNode } from 'react';
import { useAuthStore } from "./store/useAuthStore";

interface ProtectedRouteProps {
    children: ReactNode;
}

const ProtectedRoute: React.FC<ProtectedRouteProps> = ({ children }) => {
    const { userInfo } = useAuthStore();
    return userInfo ? <>{children}</> : <Navigate to="/auth/sign-in" replace />;
};

const Main: React.FC = () => {
    const [currentTheme, setCurrentTheme] = useState(initialTheme);
    const [isLoading, setIsLoading] = useState(true);

    const rehydrate = useAuthStore((state) => state.rehydrate);
    const { userInfo } = useAuthStore((state) => state);

    useEffect(() => {
        async function initializeUserInfo() {
            try {
                await rehydrate();
            } catch (error) {
                console.error("Failed to rehydrate user info:", error);
            } finally {
                setIsLoading(false);
            }
        }
        initializeUserInfo();
    }, [rehydrate]);

    if (isLoading) {
        return (
            <Center height="100vh">
                <Spinner size="xl" />
            </Center>
        );
    }

    return (
        <ChakraProvider theme={currentTheme}>
            <Routes>
                <Route path="auth/*" element={<AuthLayout />} />
                <Route
                    path="admin/*"
                    element={
                        <ProtectedRoute>
                            <AdminLayout
                                theme={currentTheme}
                                setTheme={setCurrentTheme}
                            />
                        </ProtectedRoute>
                    }
                />
                <Route
                    path="rtl/*"
                    element={
                        <ProtectedRoute>
                            <RTLLayout
                                theme={currentTheme}
                                setTheme={setCurrentTheme}
                            />
                        </ProtectedRoute>
                    }
                />
                <Route
                    path="/"
                    element={
                        userInfo
                            ? <Navigate to="/admin" replace />
                            : <Navigate to="/auth/sign-in" replace />
                    }
                />
            </Routes>
        </ChakraProvider>
    );
}

export default Main;
