package com.api.login.user;

import com.api.login.common.model.MessageEnum;
import com.api.login.common.model.vo.ResultVo;
import com.api.login.common.util.crypt.Encrypt;
import com.api.login.user.model.entity.UserEntity;
import com.api.login.user.model.vo.UserVo;
import com.api.login.user.repo.UserRepository;
import com.api.login.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class UserTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private Encrypt encrypt;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("[User] Join - Success")
    void testJoinSuccess() {
        /* Given */
        UserVo userVo = new UserVo("user01", "password", "John Doe", "john@example.com", "010-1234-5678", "Address", "Role", "Active");

        when(userRepository.save(any(UserEntity.class))).thenReturn(new UserEntity());
        when(encrypt.getSalt()).thenReturn("randomSalt");
        when(encrypt.getEncrypt(anyString(), anyString())).thenReturn("encryptedPassword");
        when(userService.existUser(userVo)).thenReturn(false);

        /* When */
        ResultVo result = userService.join(userVo);

        /* Then */
        assertEquals(MessageEnum.RESULT_SUCCESS.message, result.getResult());
        assertEquals(MessageEnum.JOIN_SUCCESS.message, result.getResultMessage());
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    @DisplayName("[User] Join - Fail(existUser)")
    void testJoinFailureDueToDuplicateUser() {
        /* Given */
        UserVo userVo = new UserVo("user01", "password", "John Doe", "john@example.com", "010-1234-5678", "Address", "Role", "Active");

        when(userService.existUser(userVo)).thenReturn(true);

        /* When */
        ResultVo result = userService.join(userVo);

        /* Then */
        assertNotNull(result);
        assertEquals(MessageEnum.RESULT_ERROR.message, result.getResult());
        assertEquals(MessageEnum.EXIST_USER.message, result.getResultMessage());
        verify(userRepository, times(0)).save(any(UserEntity.class));
    }

}
