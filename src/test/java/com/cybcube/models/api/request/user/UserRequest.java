package com.cybcube.models.api.request.user;

import com.cybcube.models.api.base.User;

public class UserRequest extends User {

    public UserRequest(int id, String username,
                String firstName, String lastName,
                String email, String password,
                String phone, int userStatus) {
        super(id, username,
                firstName, lastName,
                email, password,
                phone, userStatus);
    }
}
