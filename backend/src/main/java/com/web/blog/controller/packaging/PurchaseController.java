package com.web.blog.controller.packaging;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.web.blog.dao.post.PostListDao;
import com.web.blog.dao.post.TagDao;
import com.web.blog.model.post.PostList;
import com.web.blog.model.post.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/purchase")
public class PurchaseController {

    @Autowired
    PostListDao postDao;

    @Autowired
    TagDao tagDao;

    // infinite-loading paging
    @GetMapping("/getList/{type}/{page}")
    @ResponseBody
    public List<PostList> getList(@PathVariable String type, @PathVariable int page) {
        int start = page * 9;
        int end = start + 9;

        List<PostList> temp = new LinkedList<>();
        if (type.equals("all")) {
            temp = postDao.findByFlagOrderByCreateDateDesc(1);
        } else if (type.equals("spring")) {
            temp = postDao.findBySpringAndFlagOrderByCreateDateDesc(1, 1);
        } else if (type.equals("summer")) {
            temp = postDao.findBySummerAndFlagOrderByCreateDateDesc(1, 1);
        } else if (type.equals("autumn")) {
            temp = postDao.findByAutumnAndFlagOrderByCreateDateDesc(1, 1);
        } else if (type.equals("winter")) {
            temp = postDao.findByWinterAndFlagOrderByCreateDateDesc(1, 1);
        } else {
            temp = postDao.findByPlaceAndFlagOrderByCreateDateDesc(type, 1);
        }

        if (end > temp.size()) {
            end = temp.size();
        }

        List<PostList> list = new LinkedList<>();
        for (int i = start; i < end; i++) {
            list.add(temp.get(i));
        }

        return list;
    }

    @GetMapping("/search/{type}/{key}/{word}/{page}")
    @ApiOperation(value = "검색")
    public List<PostList> search(@PathVariable String type, @PathVariable String key, @PathVariable String word,
            @PathVariable int page) throws SQLException, IOException {
        List<PostList> searchpost = new LinkedList<>();
        if (key.equals("title")) {
            searchpost = postDao.findByTitleLikeOrderByCreateDateDesc("%" + word + "%");
        } else if (key.equals("activity")) {
            searchpost = postDao.findByActivityLikeOrderByCreateDateDesc("%" + word + "%");
        } else if (key.equals("price")) {
            int price = Integer.parseInt(word);
            searchpost = postDao.findByPriceLessThanEqualOrderByCreateDateDesc(price);
        }
        List<PostList> post = new LinkedList<>();
        if (type.equals("all")) {
            post = searchpost;
        } else if (type.equals("spring")) {
            for (PostList p : searchpost) {
                if (p.getSpring() == 1)
                    post.add(p);
            }
        } else if (type.equals("summer")) {
            for (PostList p : searchpost) {
                if (p.getSummer() == 1)
                    post.add(p);
            }
        } else if (type.equals("autumn")) {
            for (PostList p : searchpost) {
                if (p.getAutumn() == 1)
                    post.add(p);
            }
        } else if (type.equals("winter")) {
            for (PostList p : searchpost) {
                if (p.getWinter() == 1)
                    post.add(p);
            }
        } else {
            for (PostList p : searchpost) {
                if (p.getPlace().equals(type))
                    post.add(p);
            }
        }

        int start = page * 9;
        int end = start + 9;

        if (end > post.size()) {
            end = post.size();
        }

        List<PostList> list = new LinkedList<>();
        for (int i = start; i < end; i++) {
            list.add(post.get(i));
        }

        return list;
    }

    @GetMapping("/list")
    @ApiOperation(value = "포스트 리스트")
    public List<PostList> selectAll() throws SQLException, IOException {
        List<PostList> temp = new LinkedList<>();
        temp = postDao.findByFlagOrderByCreateDateDesc(1);
        return temp;
    }

    @GetMapping("/listbylike")
    @ApiOperation(value = "포스트 리스트 좋아요 정렬")
    public List<PostList> selectAllByLike() throws SQLException, IOException {
        List<PostList> temp = new LinkedList<>();
        temp = postDao.findAllByOrderByLikecntDesc();
        return temp;
    }

    @GetMapping("/detail/{pid}")
    @ApiOperation(value = "포스트 상세정보")
    public Object selectDetail(@PathVariable int pid) throws SQLException, IOException {
        PostList post = postDao.findByPid(pid);
        if (post != null) {
            // System.out.println(post);
            return post;
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/modify")
    @ApiOperation(value = "포스트 수정하기")
    public Object modify(@Valid @RequestBody PostList request) throws SQLException, IOException {
        try {
            PostList post = postDao.findByPid(request.getPid());
            if (post != null) {

                PostList newTemp = post;
                newTemp.setTitle(request.getTitle());
                newTemp.setLocation(request.getLocation());
                newTemp.setImgurl(request.getImgurl());
                newTemp.setPrice(request.getPrice());
                newTemp.setSdate(request.getSdate());
                newTemp.setEdate(request.getEdate());
                newTemp.setCompanyInfo(request.getCompanyInfo());
                newTemp.setDetail(request.getDetail());
                newTemp.setActivity(request.getActivity());
                newTemp.setSpring(request.getSpring());
                newTemp.setSummer(request.getSummer());
                newTemp.setAutumn(request.getAutumn());
                newTemp.setWinter(request.getWinter());
                newTemp.setPlace(request.getPlace());
                LocalDateTime time = LocalDateTime.now();
                newTemp.setCreateDate(time);
                // System.out.println(newTemp);
                postDao.save(newTemp);
                return newTemp;
            } else {
                System.out.println("DB에 없음.");
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{pid}")
    @ApiOperation(value = "포스트 삭제")
    public Object delete(@PathVariable int pid) throws SQLException, IOException {
        PostList post = postDao.findByPid(pid);
        if (post != null) {
            postDao.delete(post);
            return "포스트 삭제 완료";
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/regist/{packPost}")
    @ApiOperation("상품 구매")
    public Object regist(@PathVariable List<Integer> packPost)
            throws SQLException, IOException {
        try {
            List<Integer> item = new LinkedList<>();
            item = packPost;
            
            
            return item;
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // @GetMapping("/types/{typename}/{page}")
    // @ApiOperation(value = "타입 별 포스트")
    // public List<PostList> seasons(@PathVariable String typename, @PathVariable
    // int page) throws SQLException, IOException {
    // int start = page * 9;
    // int end = start + 9;

    // List<PostList> list = new LinkedList<>();
    // if (typename.equals("spring")) {
    // list = postDao.findBySpring(1);
    // } else if (typename.equals("summer")) {
    // list = postDao.findBySummer(1);
    // } else if (typename.equals("autumn")) {
    // list = postDao.findByAutumn(1);
    // } else if (typename.equals("winter")) {
    // list = postDao.findByWinter(1);
    // } else {
    // list = postDao.findByPlace(typename);
    // }

    // if (end > list.size()) {
    // end = list.size();
    // }

    // List<PostList> plist = new LinkedList<>();
    // for (int i = start; i < end; i++) {
    // plist.add(list.get(i));
    // }

    // return plist;
    // }

}