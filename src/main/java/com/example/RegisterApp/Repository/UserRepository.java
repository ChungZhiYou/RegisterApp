package com.example.RegisterApp.Repository;

import com.example.RegisterApp.Models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByEmail(String email);
    @Query("SELECT u FROM UserEntity u WHERE u.username LIKE CONCAT('%', :query, '%')")
    List<UserEntity> searchUsers(String query);

    UserEntity findFirstByUsername(String username);
}
