package com.sorbet.entity;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
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
@DynamicInsert
@DynamicUpdate
@Builder
public class User{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String userLoginId;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String nickname;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private int point;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @ColumnDefault("'PLAIN'")
    private UserLevel level;


    public void addPoint(int value) {
        this.point += value;
        this.level = UserLevel.calculateLevel(this.point);
    }

    public void updateLevelByPoint() {
        this.level = UserLevel.calculateLevel(this.point);
    }


/*    // 🔽 필수 구현 메서드들
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
        }*/

    // User.java

    public UserLevel getUserLevel() {
        return UserLevel.calculateLevel(this.point);
    }

    public String getLevelLabel() {
        return getUserLevel().getLabel();
    }

    public String getLevelEmoji() {
        return getUserLevel().getEmoji();
    }

    public String getLevelColor() {
        return getUserLevel().getColorCode();
    }


}

