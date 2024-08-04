package com.example.testTask.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "patients")
@Schema(description = "Patient")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Schema(description = "ID", example = "d8ff176f-bd0a-4b8e-b329-871952e32e1f")
    private UUID id;

    @NotNull
    @Schema(description = "name", example = "Иванов Иван Иванович")
    private String name;

    @NotNull
    @Schema(description = "gender", example = "male")
    private String gender;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Schema(description = "birthdate", example = "2024-01-13T18:25:43")
    private LocalDateTime birthdate;
}
