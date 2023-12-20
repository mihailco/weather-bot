package ru.handh.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Geodata {
    int id;
    private String lon;
    private String lat;
    private String name;
}
