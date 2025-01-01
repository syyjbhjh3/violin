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
}

export const useAuthStore = create<AuthState>((set) => ({
    accessToken: localStorage.getItem('accessToken'),
    refreshToken: localStorage.getItem('refreshToken'),
    userInfo: JSON.parse(localStorage.getItem('userInfo') || 'null'),

    setAccessToken: (token: string) => {
        localStorage.setItem('accessToken', token);
        set({ accessToken: token });
    },

    setRefreshToken: (token: string) => {
        localStorage.setItem('refreshToken', token);
        set({ refreshToken: token });
    },

    setUserInfo: (userInfo: UserInfo) => {
        localStorage.setItem('accessToken', userInfo.accessToken);
        localStorage.setItem('refreshToken', userInfo.refreshToken);
        localStorage.setItem('userInfo', JSON.stringify(userInfo));
        set({ accessToken: userInfo.accessToken });
        set({ refreshToken: userInfo.refreshToken });
        set({ userInfo });
    },

    logout: () => {
        localStorage.removeItem('accessToken');
        localStorage.removeItem('refreshToken');
        localStorage.removeItem('userInfo');
        set({ accessToken: null, refreshToken: null, userInfo: null });
    },
}));
