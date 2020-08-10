package com.web.blog.controller.post;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.web.blog.dao.post.PostListDao;
import com.web.blog.model.post.PostList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/recommand")
public class SurveyController {
    
    @Autowired
    PostListDao postListDao;

    @GetMapping("/list")
    @ApiOperation(value = "포스트 리스트")
    public List<PostList> selectAll() throws SQLException, IOException {
        
        
        
        List<PostList> temp = new LinkedList<>();
        temp = postListDao.findByFlagOrderByCreateDateDesc(1);
        return temp;
    }
}