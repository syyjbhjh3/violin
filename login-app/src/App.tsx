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
import { useState, useEffect } from 'react';
import { useAuthStore } from "./store/useAuthStore";

export default function Main() {
    const [currentTheme, setCurrentTheme] = useState(initialTheme);

    const rehydrate = useAuthStore((state) => state.rehydrate);
    const { userInfo } = useAuthStore((state) => state);

    useEffect(() => {
        rehydrate();
    }, [rehydrate]);

    if (!userInfo) {
        return <div>Loading...</div>;
    }

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
