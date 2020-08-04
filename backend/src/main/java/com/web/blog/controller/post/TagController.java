package com.web.blog.controller.post;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.web.blog.dao.post.LikeListDao;
import com.web.blog.dao.post.PostListDao;
import com.web.blog.dao.post.TagDao;
import com.web.blog.model.post.LikeList;
import com.web.blog.model.post.PostList;
import com.web.blog.model.post.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/tag")
public class TagController {

    @Autowired
    TagDao tagDao;

    @GetMapping("/regist/{tagname}")
    @ApiOperation("태그 등록")
    public Object regist(@PathVariable String tagname) throws SQLException, IOException {
        try {

            String[] tags = tagname.split(" ");
            for (String s : tags) {
                Tag tag = tagDao.findByTagname(s);
                if (tag == null) {
                    tag = new Tag();
                    tag.setTagname(s);
                    tag.setCnt(1);
                    tagDao.save(tag);
                }else{
                    tag.setCnt(tag.getCnt()+1);
                    tagDao.save(tag);
                }
            }
            return "태그 등록";
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/toptag")
    @ApiOperation("인기 태그")
    public List<Tag> toptag() throws SQLException, IOException {
        List<Tag> taglist = new LinkedList<>();
        taglist = tagDao.findAllByOrderByCntDesc();

        return taglist;
    }
}