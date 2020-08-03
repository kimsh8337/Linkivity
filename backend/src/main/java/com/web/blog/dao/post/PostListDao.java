package com.web.blog.dao.post;

import java.util.List;

import com.web.blog.model.post.PostList;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostListDao extends JpaRepository<PostList, String> {
    List<PostList> findByEmail(String email);
    List<PostList> findByEmailAndFlagOrderByCreateDateDesc(String email, int flag);
    PostList findByPid(int pid);
    List<PostList> findByFlag(int flag);
    List<PostList> findByFlagOrderByCreateDateDesc(int flag);
    List<PostList> findByActivityLikeOrderByCreateDateDesc(String word);
    List<PostList> findByTitleLikeOrderByCreateDateDesc(String word);
    List<PostList> findByPriceLessThanEqualOrderByCreateDateDesc(int word);
    List<PostList> findAllByOrderByLikecntDesc();
    List<PostList> findBySpring(int spring);
    List<PostList> findBySummer(int summer);
    List<PostList> findByFall(int fall);
    List<PostList> findByWinter(int winter);
    List<PostList> findByPlace(String place);
}