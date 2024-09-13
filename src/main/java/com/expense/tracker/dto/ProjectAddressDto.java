package com.expense.tracker.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectAddressDto {
    String address;
    String city;
    String state;
    String country = "India";
    String zipcode;
}
