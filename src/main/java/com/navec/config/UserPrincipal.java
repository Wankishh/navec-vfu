package com.navec.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.navec.user.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.navec.user.User;

public class UserPrincipal implements UserDetails {
    private static final long serialVersionUID = 4058478250527974398L;

    private final String username;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    private User user;

    public UserPrincipal(final String username, final String password, final Collection<? extends GrantedAuthority> authorities) {
        super();
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public UserPrincipal(final String userName, final User user) {
        this(userName, user.getPassword(), createAuthorities(user.getRole()));
        this.user = user;
    }


    public static List<GrantedAuthority> createAuthorities(final Role role) {
        final List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.toString()));
        return authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Long getUserId() {
        if (user != null) {
            return user.getId();
        }
        return null;
    }

    public User getUser() {
        return this.user;
    }
}
