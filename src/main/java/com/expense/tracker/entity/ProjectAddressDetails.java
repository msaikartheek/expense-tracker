package com.expense.tracker.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProjectAddressDetails {
    String address;
    String city;
    String state;
    String country = "India";
    String zipcode;
}
