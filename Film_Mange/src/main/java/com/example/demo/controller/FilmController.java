package com.example.demo.controller;

import com.example.demo.common.SessionUtil;
import com.example.demo.model.FilmInfo;
import com.example.demo.model.UserInfo;
import com.example.demo.service.FilmService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/film")
public class FilmController {
    @Resource
    private FilmService filmService;

    @RequestMapping("/upload")
    public int upload(@RequestParam("image") MultipartFile poster,
                                    @RequestParam("video") MultipartFile video,
                                    @RequestParam("name") String name,
                                    @RequestParam("director") String director,
                                    @RequestParam("type") String type,
                                    @RequestParam("description") String description) {
        try {
            filmService.upload(poster, video, name, director, type, description);
            return 1;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @RequestMapping("/list")
    public List<FilmInfo> getList(HttpServletRequest request) throws IOException {
        UserInfo userInfo= SessionUtil.getLoginUser(request);
        if(userInfo!=null) {
            return filmService.getList();
        }
        return null;
    }

    @RequestMapping("/update")
    public int update(HttpServletRequest request,Integer fid,String type,String description) {
        UserInfo userInfo= SessionUtil.getLoginUser(request);
        if(userInfo!=null) {
            return filmService.update(fid,type,description);
        }
        return 0;
    }

    @RequestMapping("/delete")
    public int delete(HttpServletRequest request,Integer fid) {
        UserInfo userInfo=SessionUtil.getLoginUser(request);
        if(userInfo!=null) {
            return filmService.delete(fid);
        }
        return 0;
    }
}
