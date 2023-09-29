package com.jar.service.system.user.service.domain;

import com.jar.service.system.user.service.domain.entity.User;
import com.jar.service.system.user.service.domain.event.UserDatabaseDeletedEvent;
import com.jar.service.system.user.service.domain.event.UserAppOrderDeletedEvent;
import com.jar.service.system.user.service.domain.valueobject.ChangePassword;

import java.time.ZonedDateTime;

public class UserDomainServiceImpl implements UserDomainService {

    @Override
    public void initializeUser(User user) {
        user.initializeUser();
    }

    @Override
    public void verifyUserEmailAddress(User user, Integer emailCode) {
        user.checkEmailAuthentication(emailCode);
    }

    @Override
    public void updateUserPassword(User user, ChangePassword changePassword) {
        user.updatePassword(changePassword);
    }

    @Override
    public void resetPassword(User user, ChangePassword changePassword) {
        user.resetPassword(changePassword);
    }

    public UserAppOrderDeletedEvent deleteUsersAppOrder(User user) {
        return new UserAppOrderDeletedEvent(user, ZonedDateTime.now());
    }

    @Override
    public UserDatabaseDeletedEvent requestDeleteUserDatabase(User user) {
        return new UserDatabaseDeletedEvent(user.getId(), ZonedDateTime.now());
    }

}
