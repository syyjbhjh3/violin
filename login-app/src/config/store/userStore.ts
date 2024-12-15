import { create } from 'zustand';

interface AuthState {
    isAuthenticated: boolean;
    login: () => void;
    logout: () => void;
}

const userStore = create<AuthState>((set) => ({
    isAuthenticated: false,
    login: () => set({ isAuthenticated: true }),
    logout: () => set({ isAuthenticated: false }),
}));

export default userStore;