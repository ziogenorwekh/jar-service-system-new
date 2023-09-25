package com.jar.service.system.database.service.application.exception;

import java.sql.SQLException;

public class DatabaseNotCreatedException extends RuntimeException
{
    public DatabaseNotCreatedException(String reason) {
        super(reason);
    }

    public DatabaseNotCreatedException(String reason, Throwable cause) {
        super(reason, cause);
    }


}
