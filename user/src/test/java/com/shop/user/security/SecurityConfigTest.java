package com.shop.user.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.shop.user.model.User;
import com.shop.user.service.UserService;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Optional;


@SpringBootTest
@AutoConfigureMockMvc
public class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @BeforeEach
    void setupMockService() {
        User user = new User.Builder()
                .setId(1L)
                .setName("Test User")
                .setEmail("test@example.com")
                .setPhone("1234567890")
                .setPassword("password123")
                .setRole("USER")
                .build();

        Mockito.when(userService.getAllUsers()).thenReturn(List.of(user));
        Mockito.when(userService.getUserById(1L)).thenReturn(Optional.of(user));
    }

    @Test
    void testUserCanAccessGetUsers() throws Exception {
        mockMvc.perform(get("/api/user")
                .with(httpBasic("user", "Nb2Kb,mZT9")))
                .andExpect(status().isOk());
    }

    @Test
    void testAdminCanAccessGetUsers() throws Exception {
        mockMvc.perform(get("/api/user")
                .with(httpBasic("admin", "WpCsGw3jp*")))
                .andExpect(status().isOk());
    }

    @Test
    void testUserCannotAccessPostUsers() throws Exception {
        mockMvc.perform(post("/api/user")
                .with(httpBasic("user", "Nb2Kb,mZT9"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "name": "New User",
                        "email": "newuser@example.com",
                        "phone": "9876543210",
                        "password": "newpassword",
                        "role": "USER"
                    }
                """))
                .andExpect(status().isForbidden());
    }

    @Test
    void testAdminCanAccessPostUsers() throws Exception {
        mockMvc.perform(post("/api/user")
                .with(httpBasic("admin", "WpCsGw3jp*"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "name": "New User",
                        "email": "newuser@example.com",
                        "phone": "9876543210",
                        "password": "newpassword",
                        "role": "USER"
                    }
                """))
                .andExpect(status().isCreated());
    }

    @Test
    void testUserCannotAccessDeleteUser() throws Exception {
        mockMvc.perform(delete("/api/user/1")
                .with(httpBasic("user", "Nb2Kb,mZT9")))
                .andExpect(status().isForbidden());
    }

    @Test
    void testAdminCanAccessDeleteUser() throws Exception {
        mockMvc.perform(delete("/api/user/1")
                .with(httpBasic("admin", "WpCsGw3jp*")))
                .andExpect(status().isNoContent());
    }

    @Test
    void testUnauthorizedAccess() throws Exception {
        mockMvc.perform(get("/api/user"))
                .andExpect(status().isUnauthorized());
    }
}
