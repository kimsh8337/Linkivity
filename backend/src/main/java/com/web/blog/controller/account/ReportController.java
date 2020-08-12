package com.web.blog.controller.account;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import com.web.blog.dao.post.PostListDao;
import com.web.blog.dao.user.ReportDao;
import com.web.blog.dao.user.ReviewDao;
import com.web.blog.dao.user.UserDao;
import com.web.blog.model.post.PostList;
import com.web.blog.model.user.Report;
import com.web.blog.model.user.Review;
import com.web.blog.model.user.User;

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
@RequestMapping("/report")
public class ReportController {

    @Autowired
    ReportDao reportDao;

    @Autowired
    UserDao userDao;

    @PostMapping("/regist")
    @ApiOperation("신고 등록")
    public Object regist(@RequestBody Report request) throws SQLException, IOException {
        try {
            Report report = new Report();
            report.setReason(request.getReason());
            report.setEmail(request.getEmail());
            report.setRemail(request.getRemail());
            reportDao.save(report);

            return new ResponseEntity<>(report, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/list")
    @ApiOperation("신고 리스트")
    public Object list() throws SQLException, IOException {
        List<Report> list = reportDao.findAll();
        return new ResponseEntity<>(list, HttpStatus.ACCEPTED);
    }

    @GetMapping("/dropUser")
    @ApiOperation("사용자 탈퇴 처리")
    public Object dropUser(@PathVariable String remail) throws SQLException, IOException {
        try {
            Optional<User> user = userDao.findUserByEmail(remail);
            if (user != null) {
                userDao.delete(user.get());
                return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}