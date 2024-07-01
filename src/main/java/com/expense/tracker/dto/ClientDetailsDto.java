package com.expense.tracker.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
public class ClientDetailsDto {
    String id;
    String name;
    String email;
    String phoneNumber;
    String userId;
//    List<ProjectDetails> projectDetails;

}
