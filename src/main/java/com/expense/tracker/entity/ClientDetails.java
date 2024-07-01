package com.expense.tracker.entity;


import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "clientDetails")
@FieldDefaults(makeFinal = true)
@Data
public class ClientDetails {
    @Id
    @Indexed
    String id;
    String name;
    String email;
    String phoneNumber;
    String userId;
//    List<ProjectDetails> projectDetails;

}
