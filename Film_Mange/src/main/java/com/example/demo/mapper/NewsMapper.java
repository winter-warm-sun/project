package com.example.demo.mapper;

import com.example.demo.model.NewsInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface NewsMapper {
    List<NewsInfo> getList();

    int add(@Param("title") String title,@Param("content") String content);

    NewsInfo getDetail(@Param("nid") Integer nid);

    int delete(@Param("nid") Integer nid);

    int update(@Param("nid") Integer nid,
               @Param("title") String title,
               @Param("content") String content);
}
