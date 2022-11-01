package xyz.jaign.filemanage.service.impl;

import xyz.jaign.filemanage.component.NonStaticResourceHttpRequestHandler;
import xyz.jaign.filemanage.dao.ClassListMapper;
import xyz.jaign.filemanage.dto.send.MovieDTOSend;
import xyz.jaign.filemanage.entity.ClassList;
import xyz.jaign.filemanage.enums.ClassType;
import xyz.jaign.filemanage.service.MoviesService;
import xyz.jaign.filemanage.util.CacheUtil;
import xyz.jaign.filemanage.util.MovieOptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class MoviesServiceImpl implements MoviesService {

    @Autowired
    private LoginServiceImpl loginServiceImpl;
    @Autowired
    private ClassManageServiceImpl classManageServiceImpl;
    @Autowired
    private ClassListMapper classListMapper;
    @Autowired
    private CacheUtil cacheUtil;
    @Autowired
    private NonStaticResourceHttpRequestHandler nonStaticResourceHttpRequestHandler;


    @Override
    public MovieDTOSend getAllMovie() {
        MovieDTOSend send = new MovieDTOSend();

        if (cacheUtil.hasKey("movie_list")){
            List<MovieDTOSend.MovieNode> list = cacheUtil.getList("movie_list", MovieDTOSend.MovieNode.class);
            send.setCode(0);
            send.setMsg("视频列表获取成功[Cache]");
            send.setCount(list.size());
            send.setData(list);
            return send;
        }
        //得到当前用户id
        int userid = loginServiceImpl.getNowUser().getId();
        List<ClassList> movieClassList = classListMapper.selectByTypeAndUser(ClassType.视频.value(), userid);
        List<MovieDTOSend.MovieNode> nodes = new ArrayList<>();
        for (ClassList classList : movieClassList) {
            List<File> movieFiles = classManageServiceImpl.getFile(classList);
            //
            for (File movieFile : movieFiles) {
                MovieDTOSend.MovieNode movieNode = MovieOptUtil.toNode(movieFile);
                movieNode.setId(nodes.size());
                nodes.add(movieNode);
            }
        }
        send.setCode(0);
        send.setMsg("视频列表获取成功");
        send.setCount(nodes.size());
        send.setData(nodes);
        cacheUtil.setList("movie_list",nodes);
        return send;
    }

    //https://blog.csdn.net/qq_34455577/java/article/details/99414271
    @Override
    public void playMovie(HttpServletRequest request, HttpServletResponse response, String path) throws Exception {
        Path filePath = Paths.get(path);
        if (Files.exists(filePath)) {
            String mimeType = Files.probeContentType(filePath);
            if (!StringUtils.isEmpty(mimeType)) {
                response.setContentType(mimeType);
            }
            request.setAttribute(NonStaticResourceHttpRequestHandler.ATTR_FILE, filePath);
            nonStaticResourceHttpRequestHandler.handleRequest(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        }
    }
}
