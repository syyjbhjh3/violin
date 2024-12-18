package com.api.login.user;

import com.api.login.common.model.enums.MessageEnum;
import com.api.login.common.model.enums.StatusEnum;
import com.api.login.common.model.dto.ResultDTO;
import com.api.login.common.util.crypt.Encrypt;
import com.api.login.common.util.redis.RedisService;
import com.api.login.user.model.dto.LoginDTO;
import com.api.login.user.model.dto.UserDTO;
import com.api.login.user.model.entity.UserEntity;
import com.api.login.user.repo.UserRepository;
import com.api.login.user.service.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class UserTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private Encrypt encrypt;

    @MockBean
    private UserService userService;

    @MockBean
    private RedisService redisService;

    @Mock
    private StringRedisTemplate redisTemplate;

    @Test
    @DisplayName("[User] Join - Success")
    void testJoinSuccess() {
        /* Given */
        UserDTO userDTO = UserDTO.builder()
                .type("userType")
                .id("userId")
                .password("encryptedPassword")
                .name("John Doe")
                .gender("M")
                .phone("123-456-7890")
                .email("john.doe@example.com")
                .address("123 Main St")
                .salt("randomSalt")
                .build();

        when(userRepository.save(any(UserEntity.class))).thenReturn(new UserEntity());
        when(encrypt.getSalt()).thenReturn("randomSalt");
        when(encrypt.getEncrypt(anyString(), anyString())).thenReturn("encryptedPassword");
        when(userService.existUser("user01")).thenReturn(false);

        /* When */
        ResultDTO result = userService.signUp(userDTO);

        /* Then */
        assertEquals(StatusEnum.SUCCESS, result.getResult());
        assertEquals(MessageEnum.JOIN_SUCCESS.message, result.getResultMessage());
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    @DisplayName("[User] Join - Fail(existUser)")
    void testJoinFailureDueToDuplicateUser() {
        /* Given */
        UserDTO userDTO = UserDTO.builder()
                .type("userType")
                .id("userId")
                .password("encryptedPassword")
                .name("John Doe")
                .gender("M")
                .phone("123-456-7890")
                .email("john.doe@example.com")
                .address("123 Main St")
                .salt("randomSalt")
                .build();

        when(userService.existUser("user01")).thenReturn(true);

        /* When */
        ResultDTO result = userService.signUp(userDTO);

        /* Then */
        assertNotNull(result);
        assertEquals(StatusEnum.SUCCESS, result.getResult());
        assertEquals(MessageEnum.JOIN_DUPLICATE.message, result.getResultMessage());
        verify(userRepository, times(0)).save(any(UserEntity.class));
    }

    @Test
    @DisplayName("[User] Login - Success")
    void testLoginSuccess() {
        /* Given */
        UserDTO userDTO = UserDTO.builder()
                .type("userType")
                .id("userId")
                .password("encryptedPassword")
                .name("John Doe")
                .gender("M")
                .phone("123-456-7890")
                .email("john.doe@example.com")
                .address("123 Main St")
                .salt("randomSalt")
                .build();

        UserEntity userEntity = userDTO.toEntity();

        when(userRepository.findById("user01")).thenReturn(Optional.of(userEntity));

        when(encrypt.getSalt()).thenReturn("randomSalt");
        when(encrypt.getEncrypt(anyString(), anyString())).thenReturn("hashedPassword");

        LoginDTO loginVo = new LoginDTO(userEntity.getType(), userEntity.getId(), "accessToken", "refreshToken");
        when(redisTemplate.opsForValue().get(anyString())).thenReturn("refreshToken");

        /* When */
        ResultDTO resultDTO = userService.login(userDTO);

        /* Then */
        assertNotNull(resultDTO);
        assertNotNull(resultDTO.getData());

        verify(redisTemplate).opsForValue().set(anyString(), anyString(), any());

        String storedToken = redisTemplate.opsForValue().get("user01_refreshToken");
        assertEquals("refreshToken", storedToken);
    }
}
