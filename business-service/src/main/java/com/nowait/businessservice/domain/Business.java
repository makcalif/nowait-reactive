package com.nowait.businessservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Business {
    String id;
    String name;
    String url;
    String city;
    String country;
    String zip;
}
