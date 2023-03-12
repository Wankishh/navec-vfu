package com.navec.utils;

import com.navec.user.Role;
import com.navec.user.User;

import java.util.Objects;

public class PermissionUtils {

    private PermissionUtils() {}

    public static boolean isMissingPermission(User authUser, Long pathUserId) {
        boolean isAdmin = authUser.getRole() == Role.ADMIN;
        boolean isSameUser = Objects.equals(authUser.getId(), pathUserId);
        return !isAdmin && !isSameUser;
    }
}
