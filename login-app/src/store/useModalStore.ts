import { create } from 'zustand';

interface ModalState {
    isOpen: boolean;
    title: string;
    description: string;
    openModal: (title: string, description: string) => void;
    closeModal: () => void;
}

export const useModalStore = create<ModalState>((set) => ({
    isOpen: false,
    title: '',
    description: '',
    openModal: (title, description) =>
        set({ isOpen: true, title, description }),
    closeModal: () => set({ isOpen: false }),
}));
