package com.example.demo;

import com.example.demo.searcher.Parser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SearchEngineApplication {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(SearchEngineApplication.class, args);
//        Parser parser=new Parser();
//        try {
//            parser.runByThread();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

}
