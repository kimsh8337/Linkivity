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


    List<PostList> findBySpringAndFlagOrderByCreateDateDesc(int spring, int flag);
    List<PostList> findBySummerAndFlagOrderByCreateDateDesc(int summer, int flag);
    List<PostList> findByAutumnAndFlagOrderByCreateDateDesc(int autumn, int flag);
    List<PostList> findByWinterAndFlagOrderByCreateDateDesc(int winter, int flag);

    List<PostList> findByPlaceAndFlagOrderByCreateDateDesc(String place, int flag);

    List<PostList> findByFlagAndPlace(int flag, String place);
    List<PostList> findByFlagAndPriceLessThanEqual(int flag, int price);
    List<PostList> findByFlagAndLocationLike(int flag, String location);

}