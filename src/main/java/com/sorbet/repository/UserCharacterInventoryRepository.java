package com.sorbet.repository;

import com.sorbet.entity.User;
import com.sorbet.entity.UserCharacterInventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserCharacterInventoryRepository extends JpaRepository<UserCharacterInventory, Long> {
    List<UserCharacterInventory> findByUser(User user);
}
