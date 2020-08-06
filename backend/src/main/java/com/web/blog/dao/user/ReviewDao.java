package com.web.blog.dao.user;

import java.util.List;

import com.web.blog.model.user.Review;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewDao extends JpaRepository<Review, Integer>{
    List<Review> findByPid(int pid);
    Review findByRvid(int rvid);
}