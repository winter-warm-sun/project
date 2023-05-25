package com.example.demo.model;

import lombok.Data;

@Data
public class FilmInfo {
    private Integer id;
    private String name;
    private String director;
    private String type;
    private String description;
    private String posterPath;
    private String videoPath;
}
