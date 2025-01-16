import axios from 'axios';
import { useAuthStore } from '../store/useAuthStore';
import * as process from "process";

export const authClient = axios.create({
    baseURL: process.env.REACT_APP_AUTH_API_URL,
    timeout: 5000,
    headers: {
        'Content-Type': 'application/json',
    },
});

export const apiClient = axios.create({
    timeout: 5000,
    headers: {
        'Content-Type': 'application/json',
    },
});

apiClient.interceptors.request.use(
    (config) => {
        const { accessToken } = useAuthStore.getState();

        if (accessToken) {
            config.headers['Authorization'] = `Bearer ${accessToken}`;
            config.headers['X-User-Id'] = useAuthStore.getState().userInfo.id;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

apiClient.interceptors.response.use(
    (response) => response,
    async (error) => {
        const originalRequest = error.config;

        if (error.response?.status === 401 && !originalRequest._retry) {
            originalRequest._retry = true;
            const { refreshToken, setAccessToken, logout, userInfo } = useAuthStore.getState();

            if (!refreshToken) {
                console.error('Refresh Token 없음');
                logout();
                return Promise.reject(error);
            }

            const param = { id : userInfo.id, refreshToken };

            try {
                const response = await axios.post(process.env.REACT_APP_AUTH_API_URL + '/refresh', param);
                const newAccessToken = response.data.accessToken;

                setAccessToken(newAccessToken);
                originalRequest.headers['Authorization'] = `Bearer ${newAccessToken}`;

                return apiClient(originalRequest);
            } catch (refreshError) {
                console.error('토큰 갱신 실패:', refreshError);
                logout();
                return Promise.reject(refreshError);
            }
        }

        return Promise.reject(error);
    }
)