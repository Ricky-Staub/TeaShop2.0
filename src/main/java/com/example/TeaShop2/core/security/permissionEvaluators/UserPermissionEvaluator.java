package com.example.TeaShop2.core.security.permissionEvaluators;

import com.example.TeaShop2.domain.entitys.user.User;
import org.springframework.stereotype.Component;

@Component
public class UserPermissionEvaluator {

    public UserPermissionEvaluator() {
    }

    public boolean isUserAboveAge (User principal, int age) {
        return true;
    }

}
