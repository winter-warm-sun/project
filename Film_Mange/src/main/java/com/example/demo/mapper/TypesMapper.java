package com.example.demo.mapper;

import com.example.demo.model.TypeInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TypesMapper {

    int add(@Param("type") String type);

    int delete(@Param("tid") Integer tid);

    int update(@Param("type") String type,@Param("tid") Integer tid);

    List<TypeInfo> getList();
}
