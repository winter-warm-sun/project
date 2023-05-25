package com.example.demo.mapper;

import com.example.demo.model.FilmInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FilmMapper {
    List<FilmInfo> getList();

    void save(FilmInfo film);

    int update(Integer fid,String type,String description);

    int delete(Integer fid);
}
