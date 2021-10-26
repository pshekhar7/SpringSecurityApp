package com.pshekhar.securitymodule.auth;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserDetailsService implements UserDetailsService {
    private final UserDaoService applicationUserDao;

    public ApplicationUserDetailsService(UserDaoService applicationUserDao) {
        this.applicationUserDao = applicationUserDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return applicationUserDao.
                findUserByUsername(username)
                .map(u -> User
                        .builder()
                        .username(u.getUsername())
                        .password(u.getPassword())
                        .authorities(u.getAuthorities())
                        .accountExpired(u.isAccountExpired())
                        .accountLocked(u.isAccountLocked())
                        .credentialsExpired(u.isCredentialsExpired())
                        .disabled(u.isDisabled())
                        .build())
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format("Username [%s] not found", username)));
    }


}
