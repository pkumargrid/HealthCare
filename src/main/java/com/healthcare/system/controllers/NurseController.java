package com.healthcare.system.controllers;

import com.healthcare.system.dto.NurseDTO;
import com.healthcare.system.dto.ResponseCrudDTO;
import com.healthcare.system.entities.Nurse;
import com.healthcare.system.exceptions.AlreadyLoggedInException;
import com.healthcare.system.exceptions.ValidationException;
import com.healthcare.system.exceptions.WrongCredentials;
import com.healthcare.system.services.NurseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/v1/nurses")
public class NurseController {

    private final NurseService nurseService;

    public NurseController(NurseService nurseService) {
        this.nurseService = nurseService;
    }

    @PostMapping("/")
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

    @GetMapping("/")
    public ResponseCrudDTO<List<Nurse>> findAll() {
        List<Nurse> nurses = nurseService.findAll();
        return new ResponseCrudDTO<>("Fetched Nurses Successfully!", 200, nurses);
    }
}
