package com.expense.tracker.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ClientDetailsDto {
    String id;
    @NotBlank(message = "Name must not be empty")
    String name;
    String email;
    @NotBlank(message = "Phone number must not empty")
    @Size(max = 11, min = 10, message = "phone number must not be greater than 10 digits")
    String phoneNumber;
    String userId;
    @Size(min = 1,message = "Please fill at least one project details")
    @NotNull
    List<ProjectDetailsDto> projects;
}
