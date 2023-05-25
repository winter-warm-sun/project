package com.example.demo.service;

import com.example.demo.mapper.FilmMapper;
import com.example.demo.model.FilmInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class FilmService {
    @Resource
    private FilmMapper filmMapper;

    public void upload(MultipartFile poster, MultipartFile video, String name, String director, String type, String description) throws IOException {
        // 保存海报文件和视频文件
        String posterPath = saveFile(poster, "posters");
        String videoPath = saveFile(video, "videos");

        // 保存电影信息到数据库中
        FilmInfo film = new FilmInfo();
        film.setName(name);
        film.setDirector(director);
        film.setType(type);
        film.setDescription(description);
        film.setPosterPath(posterPath);
        film.setVideoPath(videoPath);
        filmMapper.save(film);
    }

    private String saveFile(MultipartFile file, String folder) throws IOException {
        // 生成唯一的文件名
        String fileName = UUID.randomUUID().toString() + "." + StringUtils.getFilenameExtension(file.getOriginalFilename());

        // 保存文件到磁盘上
        String rpath = "uploads/" + folder + "/" + fileName;
        String s="D:/";
        String path=s+rpath;
        File dest = new File(path);
        FileUtils.forceMkdirParent(dest);
        file.transferTo(dest);

        // 返回文件路径
        return rpath;
    }


    public List<FilmInfo> getList() throws IOException {
        List<FilmInfo> films = filmMapper.getList();
        return films;
    }


    public int update(Integer fid,String type,String description) {
        return filmMapper.update(fid,type,description);
    }

    public int delete(Integer fid) {
        return filmMapper.delete(fid);
    }
}
