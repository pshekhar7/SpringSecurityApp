package com.pshekhar.securitymodule.auth;

import java.util.Optional;

public interface ApplicationUserDao {
    Optional<ApplicationUser> findApplicationUserByUsername(String username);
}
