package com.cybcube.models.api.response.user;

import com.cybcube.models.api.base.User;

public class UserResponse extends User {

        public UserResponse() {
            super();
        }

        public UserResponse(int id, String username,
                            String firstName, String lastName,
                            String email, String password,
                            String phone, int userStatus) {
            super(id, username,
                    firstName, lastName,
                    email, password,
                    phone, userStatus);
        }
}
