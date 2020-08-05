package com.web.blog.dao.post;

import com.web.blog.model.post.Notice;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeDao extends JpaRepository<Notice, Integer>{
    Notice findByNid(int nid);
}