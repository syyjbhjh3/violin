import { create } from 'zustand';

interface UserInfo {
    id: string;
    accessToken: string;
    refreshToken: string;
}

interface AuthState {
    accessToken: string | null;
    refreshToken: string | null;
    userInfo: UserInfo | null;
    setAccessToken: (token: string) => void;
    setRefreshToken: (token: string) => void;
    setUserInfo: (userInfo: UserInfo) => void;
    logout: () => void;
    rehydrate: () => void;
}

export const useAuthStore = create<AuthState>((set) => ({
    accessToken: null,
    refreshToken: null,
    userInfo: null,

    setAccessToken: (token: string) => {
        localStorage.setItem('accessToken', token);
        set({ accessToken: token });
    },

    setRefreshToken: (token: string) => {
        localStorage.setItem('refreshToken', token);
        set({ refreshToken: token });
    },

    setUserInfo: (userInfo: UserInfo) => {
        localStorage.setItem('userInfo', JSON.stringify(userInfo));
        set({
            accessToken: userInfo.accessToken,
            refreshToken: userInfo.refreshToken,
            userInfo,
        });
    },

    logout: () => {
        localStorage.removeItem('accessToken');
        localStorage.removeItem('refreshToken');
        localStorage.removeItem('userInfo');
        set({ accessToken: null, refreshToken: null, userInfo: null });
    },

    rehydrate: () => {
        const storedAccessToken = localStorage.getItem('accessToken');
        const storedRefreshToken = localStorage.getItem('refreshToken');
        const storedUserInfo = localStorage.getItem('userInfo');

        set({
            accessToken: storedAccessToken || null,
            refreshToken: storedRefreshToken || null,
            userInfo: storedUserInfo ? JSON.parse(storedUserInfo) : null,
        });
    },
}));
