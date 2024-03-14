package com.zack.api.repositories;

import com.zack.api.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> {

    @Query("SELECT u FROM UserModel u WHERE u.name = :name OR u.mail = :mail")
    UserModel findOneByUsernameOrEmail(String name, String mail);

    @Query("SELECT u FROM UserModel u WHERE u.name = :name")
    UserModel findOneByUsername(String name);

    @Query("SELECT u FROM UserModel u WHERE u.mail = :mail")
    UserModel findOneByEmail(String mail);

    @Query("SELECT u FROM UserModel u WHERE u.id = :id")
    UserModel getUserById(UUID id);

}
