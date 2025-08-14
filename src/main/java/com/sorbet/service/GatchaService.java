package com.sorbet.service;

import com.sorbet.domain.CharacterType;
import com.sorbet.domain.Grade;
import com.sorbet.entity.User;
import com.sorbet.entity.UserCharacterInventory;
import com.sorbet.repository.UserCharacterInventoryRepository;
import com.sorbet.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class GatchaService {

    private final UserRepository userRepository;
    private final UserCharacterInventoryRepository inventoryRepository;

    @Transactional
    public CharacterType drawCharacter(String userId) {
        User user = userRepository.findByUserLoginId(userId)
                .orElseThrow(() -> new EntityNotFoundException("유저 없음"));

        int cost = 100; // 뽑기 비용

        if (user.getPoint() < cost) {
            throw new IllegalStateException("포인트가 부족합니다!");
        }

        user.setPoint(user.getPoint() - cost); // ✅ 포인트 차감




        CharacterType drawn = drawWithRate();

        UserCharacterInventory entry = new UserCharacterInventory();
        entry.setCharacterType(drawn);
        entry.setUser(user);
        entry.setAcquiredAt(LocalDateTime.now());

        inventoryRepository.save(entry);

        return drawn;
    }

    // ✅ 여기에 아래 2개 메서드를 같이 붙여줘!
    private CharacterType drawWithRate() {
        int roll = new Random().nextInt(100) + 1; // 1~100
        Grade selectedGrade = getGradeByChance(roll);

        // 등급별 캐릭터 리스트 중 무작위 추출
        var candidates = Arrays.stream(CharacterType.values())
                .filter(c -> c.getGrade() == selectedGrade)
                .toList();

        return candidates.get(new Random().nextInt(candidates.size()));
    }


    public List<CharacterType> drawMultiple(String userId, int count) {
        List<CharacterType> resultList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            resultList.add(drawCharacter(userId)); // drawCharacter를 그대로 활용
        }
        return resultList;
    }



    private Grade getGradeByChance(int roll) {
        int cumulative = 0;
        for (Grade grade : Grade.values()) {
            cumulative += grade.getRate();
            if (roll <= cumulative) {
                return grade;
            }
        }
        throw new RuntimeException("확률 총합이 100이 아닙니다.");
    }
}
