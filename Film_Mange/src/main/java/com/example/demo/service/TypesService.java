package com.example.demo.service;

import com.example.demo.mapper.TypesMapper;
import com.example.demo.model.TypeInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TypesService {
    @Resource
    private TypesMapper typesMapper;


    public int add(String type) {
        return typesMapper.add(type);
    }

    public int delete(Integer tid) {
        return typesMapper.delete(tid);
    }

    public int update(String type,Integer tid) {
        return typesMapper.update(type,tid);
    }

    public List<TypeInfo> getList() {
        return typesMapper.getList();
    }
}
