package com.example.demo.mapper;

import com.example.demo.searcher.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Mapper
public interface IndexMapper {

    void saveForwardIndex(@Param("list") List<DocInfo> forwardIndex);


    void saveInvertedIndex(@Param("list")List<InvertedInfo> invertedInfos);


    ArrayList<DocWeight> getDocWeights(@Param("word") String word);
}
