package com.hr.companion.api.auth;

import com.hr.companion.api.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private JwtService jwtService;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    private User mockUser;

    private String username = "lando";
    private String failUsername = "king";

    List<String> roleList = List.of("USER");
    Set<String> roles = Set.copyOf(roleList);

    @BeforeEach
    void setup(){
        mockUser = User
                .builder()
                .id(1L)
                .username(username)
                .email("lando@gmail.com")
                .enabled(true)
                .roles(roles)
                .build();
    }

    @Test
    public void testLoadUserByUserName_Success(){
        //assign
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(mockUser));

        //act
        var newUser = customUserDetailsService.loadUserByUsername(username);

        //assert
        assertThat(newUser).isNotNull();
        assertThat(newUser.getUsername()).isEqualTo(mockUser.getUsername());

        //verify
        verify(userRepository,only()).findByUsername(username);
    }
}
