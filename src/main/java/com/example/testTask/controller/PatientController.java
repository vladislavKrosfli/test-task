package com.example.testTask.controller;

import com.example.testTask.entity.Patient;
import com.example.testTask.service.KeycloakService;
import com.example.testTask.service.PatientService;
import com.example.testTask.exception.NotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/patients")
@AllArgsConstructor
@Tag(name = "Test Task controller")
public class PatientController {

    PatientService patientService;
    KeycloakService keycloakService;

    @GetMapping
    @Operation(summary = "Get all Patients")
    public List<Patient> getPatients(){
        return patientService.getPatients();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Patient by ID")
    public Patient getPatient(@PathVariable UUID id){
        Patient patient = patientService.getById(id);
        if(patient == null){
            throw new NotFoundException(HttpStatus.NOT_FOUND, "There is no patient with ID = " + id);
        }
        return patient;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Patient by ID")
    public String deletePatient(@PathVariable UUID id){
        if(patientService.getById(id) == null){
            throw new NotFoundException(HttpStatus.NOT_FOUND, "There is no patient with ID = " + id);
        }

        keycloakService.deleteUserByUsername(patientService.getById(id).getName());
        patientService.delete(id);
        return "Patient with ID = " + id + " was deleted";
    }

    @PutMapping
    @Operation(summary = "Update Patient")
    public Patient updatePatient(@RequestBody Patient patient){
        return patientService.update(patient);
    }

    @PostMapping
    @Operation(summary = "Add new Patient")
    public Patient addNewPatient(@RequestBody Patient patient){
        return patientService.create(patient);
    }
}
