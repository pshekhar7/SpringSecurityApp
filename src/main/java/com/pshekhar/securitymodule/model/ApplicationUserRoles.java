package com.pshekhar.securitymodule.model;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum ApplicationUserRoles {
    STUDENT(Sets.newHashSet()),
    ADMINTRAINEE(Sets.newHashSet
            (ApplicationUserPermissions.COURSE_READ,
                    ApplicationUserPermissions.STUDENT_READ
            )),
    ADMIN(Sets.newHashSet
            (ApplicationUserPermissions.COURSE_READ,
                    ApplicationUserPermissions.COURSE_WRITE,
                    ApplicationUserPermissions.STUDENT_READ,
                    ApplicationUserPermissions.STUDENT_WRITE
            ));

    private final Set<ApplicationUserPermissions> permissions;

    ApplicationUserRoles(Set<ApplicationUserPermissions> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermissions> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> authorities = getPermissions()
                .stream()
                .map(p -> new SimpleGrantedAuthority(p.getPermission()))
                .collect(Collectors.toSet());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
