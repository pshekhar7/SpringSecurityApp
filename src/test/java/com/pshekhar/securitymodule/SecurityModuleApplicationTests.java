package com.pshekhar.securitymodule;

import com.google.common.collect.Lists;
import com.pshekhar.securitymodule.auth.User;
import com.pshekhar.securitymodule.model.ApplicationUserRoles;
import com.pshekhar.securitymodule.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootTest
@Slf4j
class SecurityModuleApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void contextLoads() {
    }

    @Test
    public void loadUsers() {
        this.getUsers().forEach(u -> userRepository.save(u));
    }

    private List<User> getUsers() {
        List<User> users = Lists.newArrayList(
                new User("anna",
                        passwordEncoder.encode("password"),
                        ApplicationUserRoles.STUDENT.getGrantedAuthorities(),
                        false,
                        false,
                        false,
                        false
                ),
                new User("linda",
                        passwordEncoder.encode("password"),
                        ApplicationUserRoles.ADMIN.getGrantedAuthorities(),
                        false,
                        false,
                        false,
                        false
                ),
                new User("tom",
                        passwordEncoder.encode("password"),
                        ApplicationUserRoles.ADMINTRAINEE.getGrantedAuthorities(),
                        false,
                        false,
                        false,
                        false
                )

        );
        return users;
    }

}
