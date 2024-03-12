package com.zack.api.util.responses.bodies;

import com.zack.api.models.UserModel;

import java.util.UUID;

public class UserDataForAdm extends UserData{
    private UUID id;
    public UserDataForAdm(UserModel user) {
        super(user);
        id=user.getId();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
