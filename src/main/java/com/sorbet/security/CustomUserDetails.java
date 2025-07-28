package com.sorbet.security;

import com.sorbet.entity.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(user.getRole().getKey()));
        // 예: "ROLE_USER" 또는 "ROLE_ADMIN"
    }

    public String getNickname() {
        return user.getNickname();
    }

    public Long getUserId() {
        return user.getId();
    }
    public String getUserEmail() {
        return user.getEmail(); // userId 기준 로그인
    }
    public int getUserPoint() {
        return user.getPoint();
    }


    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserLoginId(); // userId 기준 로그인
    }


    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}
