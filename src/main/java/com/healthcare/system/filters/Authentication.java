package com.healthcare.system.filters;

import com.healthcare.system.controllers.Controller;
import com.healthcare.system.dto.ObjectDTO;
import com.healthcare.system.dto.ResponseCrudDTO;
import com.healthcare.system.exceptions.AlreadyLoggedInException;
import com.healthcare.system.exceptions.NotLoggedInException;
import com.healthcare.system.exceptions.ValidationException;
import com.healthcare.system.session.SessionManager;

public class Authentication {

    public static ResponseCrudDTO<?> isValidUser(ObjectDTO objectDTO, String role, Controller<?> controller) throws NotLoggedInException, ValidationException {
        String sessionId = objectDTO.getSessionId();
        if(sessionId == null) {
            throw new ValidationException("Not a valid request missing sessionId");
        }
        if(!SessionManager.getAuthentication()) {
            throw new NotLoggedInException("Log in before to access the resource");
        }
        String email = SessionManager.getEmail(sessionId);
        if(email == null) {
            throw new NotLoggedInException("Log in before to access the resource");
        }
        return controller.execute();
    }

    public static ResponseCrudDTO<?> isAuthenticated(Controller<?> controller) throws AlreadyLoggedInException {
        if(SessionManager.getAuthentication()) {
            throw new AlreadyLoggedInException("User is already logged in");
        }
        return controller.execute();
    }

}
