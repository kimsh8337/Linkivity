package com.web.blog.controller.account;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import com.web.blog.dao.post.PostListDao;
import com.web.blog.dao.user.ReviewDao;
import com.web.blog.model.post.PostList;
import com.web.blog.model.user.Review;

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
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    ReviewDao reviewDao;

    @Autowired
    PostListDao postDao;

    @PostMapping("/regist")
    @ApiOperation("후기 등록")
    public Object regist(@RequestBody Review request) throws SQLException, IOException {
        try {
            // 평균 평점 구하기
            float reviewCnt = reviewDao.findByPid(request.getPid()).size();
            PostList post = postDao.findByPid(request.getPid());
            if (reviewCnt == 0) {
                post.setStar(request.getStar());
            } else {
                float cur = post.getStar();
                float newStar = (cur * reviewCnt + (float) request.getStar()) / (reviewCnt + 1);
                System.out.println(newStar);
                post.setStar(newStar);
            }
            // 후기 등록
            Review review = new Review();
            review.setPid(request.getPid());
            review.setEmail(request.getEmail());
            review.setTitle(request.getTitle());
            review.setContent(request.getContent());
            review.setImg(request.getImg());
            review.setStar(request.getStar());
            review.setProimg(request.getProimg());
            LocalDateTime time = LocalDateTime.now();
            review.setCreateDate(time);
            review.setNickname(request.getNickname());
            reviewDao.save(review);

            return new ResponseEntity<>(review, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/list/{pid}")
    @ApiOperation("후기 리스트")
    public List<Review> list(@PathVariable int pid) {
        List<Review> list = new LinkedList<>();
        list = reviewDao.findByPid(pid);
        return list;
    }

    @PutMapping("/modify")
    @ApiOperation("후기 수정")
    public Object modify(@RequestBody Review request) throws SQLException, IOException {
        try {
            Review temp = reviewDao.findByRvid(request.getRvid());

            if (temp != null) {
                float reviewCnt = reviewDao.findByPid(request.getPid()).size();
                PostList post = postDao.findByPid(request.getPid());
                float cur = post.getStar();
                float newStar = (cur * reviewCnt - (float) temp.getStar() + (float) request.getStar()) / reviewCnt;
                post.setStar(newStar);

                Review nTemp = temp;
                nTemp.setTitle(request.getTitle());
                nTemp.setContent(request.getContent());
                nTemp.setImg(request.getImg());
                nTemp.setStar(request.getStar());
                LocalDateTime time = LocalDateTime.now();
                nTemp.setCreateDate(time);

                reviewDao.save(nTemp);

                return new ResponseEntity<>(nTemp, HttpStatus.ACCEPTED);
            } else {
                System.out.println("DB에 없음");
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{rvid}")
    @ApiOperation("후기 삭제")
    public Object delete(@PathVariable int rvid) throws SQLException, IOException {
        Review review = reviewDao.findByRvid(rvid);
        if (review != null) {
            float reviewCnt = reviewDao.findByPid(review.getPid()).size();
            PostList post = postDao.findByPid(review.getPid());
            float cur = post.getStar();
            float newStar = (cur * reviewCnt - (float)review.getStar()) / (reviewCnt - 1);
            post.setStar(newStar);

            reviewDao.delete(review);
            return new ResponseEntity<>(review, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getLastReview")
    @ApiOperation("최신 리뷰 불러오기")
    public List<Review> getLastReview() throws SQLException, IOException {
        List<Review> list = new LinkedList<>();
        list = reviewDao.findTop6ByOrderByCreateDateDesc();
        return list;
    }

    @GetMapping("/reviewDetail/{pid}/{rvid}")
    @ApiOperation("pid와 rvid로 데이터 가져오기")
    public Object reviewDetail(@PathVariable int pid, @PathVariable int rvid) throws SQLException, IOException {
        Review review = new Review();
        try {
            review = reviewDao.findByPidAndRvid(pid, rvid);
            if (review != null) {
                return review;
            } else {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listbyemail/{email}")
    @ApiOperation("내가 쓴 후기 리스트")
    public List<Review> list(@PathVariable String email) {
        List<Review> list = new LinkedList<>();
        list = reviewDao.findByEmail(email);
        return list;
    }

}