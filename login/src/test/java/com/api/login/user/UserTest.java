package com.api.login.user;

import com.api.login.user.controller.UserController;
import com.api.login.user.model.vo.UserVo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class UserTest {
    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @Test
    @DisplayName("[User] Join")
    void testUserJoin() {
        UserVo userVo = new UserVo("", "", "", "", "", "", "", "");
    }
}
