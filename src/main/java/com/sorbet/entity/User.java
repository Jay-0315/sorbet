package com.sorbet.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.sorbet.domain.UserLevel;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;
import java.util.Collections;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String userId;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String nickname;

    @Column(unique = true, nullable = false)
    private String email;


    private int point = 0;

    @Enumerated(EnumType.STRING)
    private UserLevel level = UserLevel.PLAIN;

    public void addPoint(int value) {
        this.point += value;
        this.level = UserLevel.calculateLevel(this.point);
    }

    // 🔽 필수 구현 메서드들
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
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

    @Override
    public String getUsername() {
        return userId;
    }
}
