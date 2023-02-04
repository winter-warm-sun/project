package com.example.demo.searcher;

import lombok.Data;

@Data
public class DocWeight {
    private int docid;
    private int weight;
    private String title;
    private String url;
    private String content;
}
