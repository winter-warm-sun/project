package com.example.demo.mapper;

import com.example.demo.searcher.DocInfo;
import com.example.demo.searcher.InvertedInfo;
import com.example.demo.searcher.Result;
import com.example.demo.searcher.Weight;
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


    ArrayList<Weight> searchInvertedIndex(@Param("word") String word);

    DocInfo searchForwardIndex(@Param("docid") int docid);
}
