package com.healthcare.system.controllers;

import com.healthcare.system.dto.NurseDTO;
import com.healthcare.system.dto.ResponseCrudDTO;
import com.healthcare.system.entities.Nurse;
import com.healthcare.system.exceptions.AlreadyLoggedInException;
import com.healthcare.system.exceptions.ValidationException;
import com.healthcare.system.exceptions.WrongCredentials;
import com.healthcare.system.services.NurseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/v1/nurses")
@Tag(name = "nurseController", description = "Crud Operations for Nurses")
public class NurseController {

    private final NurseService nurseService;

    public NurseController(NurseService nurseService) {
        this.nurseService = nurseService;
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('healthProvider')")
    @Operation(summary = "Save a Doctor", description = "Takes NurseDTO to register the nurse")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = String.class)) }),
            @ApiResponse(responseCode = "400", description = "Incorrect credentials",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "400", description = "User not authorized",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = String.class))})})
    public ResponseEntity<String> save(NurseDTO nurseDTO) {
        try {
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
    @Operation(summary = "FindAll Nurses", description = "Finds all the nurses in the health care system")
    @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = Nurse.class))) })
    public ResponseCrudDTO<List<Nurse>> findAll() {
        List<Nurse> nurses = nurseService.findAll();
        return new ResponseCrudDTO<>("Fetched Nurses Successfully!", 200, nurses);
    }
}
