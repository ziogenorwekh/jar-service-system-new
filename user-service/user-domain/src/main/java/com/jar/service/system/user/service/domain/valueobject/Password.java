package com.jar.service.system.user.service.domain.valueobject;

import com.jar.service.system.common.domain.valueobject.BaseId;

public class Password extends BaseId<String> {
    public Password(String value) {
        super(value);
    }

    public boolean match(String password) {
        return this.getValue().equals(password);
    }

}
