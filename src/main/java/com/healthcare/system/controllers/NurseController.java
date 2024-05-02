package com.healthcare.system.controllers;

import com.healthcare.system.dto.NurseDTO;
import com.healthcare.system.dto.ResponseCrudDTO;
import com.healthcare.system.entities.Nurse;
import com.healthcare.system.exceptions.AlreadyLoggedInException;
import com.healthcare.system.exceptions.ValidationException;
import com.healthcare.system.exceptions.WrongCredentials;
import com.healthcare.system.services.NurseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> save(NurseDTO nurseDTO) {
        try{
            nurseService.register(nurseDTO);
            return new ResponseEntity<>("Saved Nurse Successfully!", HttpStatus.CREATED);
        } catch (ValidationException v) {
            return new ResponseEntity<>(v.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (AlreadyLoggedInException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (WrongCredentials w) {
            return new ResponseEntity<>(w.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/")
    public ResponseCrudDTO<List<Nurse>> findAll() {
        List<Nurse> nurses = nurseService.findAll();
        return new ResponseCrudDTO<>("Fetched Nurses Successfully!", 200, nurses);
    }
}
