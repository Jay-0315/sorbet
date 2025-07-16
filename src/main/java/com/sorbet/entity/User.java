package com.sorbet.entity;
import com.sorbet.domain.UserLevel;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username; // 닉네임 (로그인용)

    @Column(nullable = false)
    private String password;

    private int point = 0;

    @Enumerated(EnumType.STRING)
    private UserLevel level = UserLevel.PLAIN; //기본등급

    public void addPoint(int value) {
        this.point += value;
        this.level = UserLevel.calculateLevel(this.point);

    }

}
