package com.jar.service.system.user.service.domain;

import com.jar.service.system.user.service.domain.entity.User;
import com.jar.service.system.user.service.domain.event.UserDatabaseDeletedEvent;
import com.jar.service.system.user.service.domain.event.UserAppOrderDeletedEvent;
import com.jar.service.system.user.service.domain.valueobject.ChangePassword;

public interface UserDomainService {


    void initializeUser(User user);

    void verifyUserEmailAddress(User user, Integer emailCode);

    void updateUserPassword(User user, ChangePassword changePassword);

    void resetPassword(User user, ChangePassword changePassword);

    void mailSendBeforeCheckout(User user);

    UserAppOrderDeletedEvent deleteUsersAppOrder(User user);

    UserDatabaseDeletedEvent requestDeleteUserDatabase(User user);
}
