package com.web.blog.dao.post;

import com.web.blog.model.post.Tag;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagDao extends JpaRepository<Tag, String> {
    Tag findByTagname(String tagname);
    List<Tag> findAllByOrderByCntDesc();
}