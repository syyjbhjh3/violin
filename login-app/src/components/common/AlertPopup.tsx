import React from 'react';
import {
    Modal,
    ModalOverlay,
    ModalContent,
    ModalHeader,
    ModalCloseButton,
    ModalBody,
    ModalFooter,
    Button,
    useDisclosure,
} from '@chakra-ui/react';
import { useModalStore } from '../../store/useModalStore';

const AlterPopup = () => {
    const { isOpen, title, description, closeModal } = useModalStore();

    return (
        <Modal isOpen={isOpen} onClose={closeModal}>
            <ModalOverlay />
            <ModalContent>
                <ModalHeader>{title}</ModalHeader>
                <ModalCloseButton />
                <ModalBody>{description}</ModalBody>
                <ModalFooter>
                    <Button colorScheme="blue" onClick={closeModal}>
                        확인
                    </Button>
                </ModalFooter>
            </ModalContent>
        </Modal>
    );
};

export default AlterPopup;
