package com.expense.tracker.entity;


import jakarta.validation.constraints.NotNull;
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
    @NotNull
    String name;
    String email;
    @NotNull
    String phoneNumber;
    String userId;
    List<ProjectDetails> projects;

}
