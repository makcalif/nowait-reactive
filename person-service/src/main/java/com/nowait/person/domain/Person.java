package com.nowait.person.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class Person {
    String id;
    String businessId;
    String name;
    String phone;
}
