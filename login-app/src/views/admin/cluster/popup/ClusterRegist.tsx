import React from 'react';
import {
    Modal,
    ModalOverlay,
    ModalContent,
    ModalHeader,
    ModalCloseButton,
    ModalBody,
    ModalFooter,
    FormControl,
    FormLabel,
    Input,
    Button,
} from '@chakra-ui/react';

interface RegistrationPopupProps {
    isOpen: boolean; // 팝업 열림 여부
    onClose: () => void; // 팝업 닫기 함수
}

const RegistrationPopup: React.FC<RegistrationPopupProps> = ({ isOpen, onClose }) => {
    return (
        <Modal isOpen={isOpen} onClose={onClose}>
            <ModalOverlay />
            <ModalContent>
                <ModalHeader>등록</ModalHeader>
                <ModalCloseButton />
                <ModalBody>
                    <FormControl>
                        <FormLabel>이름</FormLabel>
                        <Input placeholder="이름을 입력하세요" />
                    </FormControl>
                    <FormControl mt={4}>
                        <FormLabel>이메일</FormLabel>
                        <Input type="email" placeholder="이메일을 입력하세요" />
                    </FormControl>
                </ModalBody>
                <ModalFooter>
                    <Button variant="ghost" onClick={onClose}>
                        취소
                    </Button>
                    <Button colorScheme="blue" ml={3}>
                        확인
                    </Button>
                </ModalFooter>
            </ModalContent>
        </Modal>
    );
};

export default RegistrationPopup;