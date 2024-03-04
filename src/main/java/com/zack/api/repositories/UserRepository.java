package com.zack.api.repositories;

import com.zack.api.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> {

    @Query("SELECT u FROM UserModel u WHERE u.name = :name OR u.mail = :mail")
    UserModel findOneByUsernameOrEmail(String name, String mail);

    @Query("SELECT u FROM UserModel u WHERE u.name = :name")
    UserModel findOneByUsername(String name);

    @Query("SELECT u FROM UserModel u WHERE u.mail = :mail")
    UserModel findOneByEmail(String mail);

    @Transactional
    @Modifying
    @Query("UPDATE UserModel u SET u.mail = :newMail WHERE u.id = :userId")
    void updateUserMailById(String newMail,  UUID userId);

    @Transactional
    @Modifying
    @Query("UPDATE UserModel u SET u.name = :newName WHERE u.id = :userId")
    void updateUserNameById(String newName, UUID userId);

    @Transactional
    @Modifying
    @Query("UPDATE UserModel u SET u.resume = :newResume WHERE u.id = :userId")
    void updateUserResumeById(String newResume,  UUID userId);


    @Transactional
    @Modifying
    @Query("UPDATE UserModel u SET u.profile = :newProfile WHERE u.id = :userId")
    void updateUserProfileById(String newProfile,  UUID userId);
}
