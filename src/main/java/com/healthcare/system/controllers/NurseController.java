package com.healthcare.system.controllers;

import com.healthcare.system.dto.NurseDTO;
import com.healthcare.system.dto.ResponseCrudDTO;
import com.healthcare.system.entities.Nurse;
import com.healthcare.system.exceptions.AlreadyLoggedInException;
import com.healthcare.system.exceptions.ValidationException;
import com.healthcare.system.exceptions.WrongCredentials;
import com.healthcare.system.services.NurseService;

import java.util.List;

public class NurseController {

    private final NurseService nurseService;

    public NurseController(NurseService nurseService) {
        this.nurseService = nurseService;
    }

    public ResponseCrudDTO<Void> save(NurseDTO nurseDTO) {
        try{
            nurseService.register(nurseDTO);
            return new ResponseCrudDTO<Void>("Saved Nurse Successfully!",201, null);
        } catch (ValidationException v) {
            return new ResponseCrudDTO<Void>("Failed to Save!\n Reason: " + v.getMessage(),403, null);
        } catch (AlreadyLoggedInException e) {
            throw new RuntimeException(e);
        } catch (WrongCredentials w) {
            return new ResponseCrudDTO<Void>("Failed to Save!\n Reason: " + w.getMessage(), 401,null);
        }
    }

    public ResponseCrudDTO<List<Nurse>> findAll() {
        List<Nurse> nurses = nurseService.findAll();
        return new ResponseCrudDTO<>("Fetched Nurses Successfully!", 200, nurses);
    }
}
