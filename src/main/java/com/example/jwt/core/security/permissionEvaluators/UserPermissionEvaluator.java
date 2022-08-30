package com.example.jwt.core.security.permissionEvaluators;

import com.example.jwt.domain.entitys.user.User;
import org.springframework.stereotype.Component;

@Component
public class UserPermissionEvaluator {

    public UserPermissionEvaluator() {
    }

    public boolean isUserAboveAge (User principal, int age) {
        return true;
    }

}
