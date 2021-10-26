package com.pshekhar.securitymodule.auth;

import com.pshekhar.securitymodule.converter.AuthoritiesConverter;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(generator = "user_id", strategy = GenerationType.AUTO)
    Long id;
    private String username;
    private String password;
    @Convert(converter = AuthoritiesConverter.class)
    @Column(columnDefinition = "TEXT")
    private Set<? extends GrantedAuthority> authorities;
    private boolean isAccountExpired;
    private boolean isAccountLocked;
    private boolean isCredentialsExpired;
    private boolean isDisabled;
    @CreationTimestamp
    ZonedDateTime createdOn;
    @UpdateTimestamp
    ZonedDateTime lastUpdatedOn;

    public User(String username,
                String password,
                Set<? extends GrantedAuthority> authorities,
                boolean isAccountExpired,
                boolean isAccountLocked,
                boolean isCredentialsExpired,
                boolean isDisabled) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.isAccountExpired = isAccountExpired;
        this.isAccountLocked = isAccountLocked;
        this.isCredentialsExpired = isCredentialsExpired;
        this.isDisabled = isDisabled;
    }
}
