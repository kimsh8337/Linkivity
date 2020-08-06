package com.web.blog.controller.packaging;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.web.blog.dao.packaging.PackDao;
import com.web.blog.dao.packaging.PurchaseDao;
import com.web.blog.dao.packaging.SellDao;
import com.web.blog.dao.post.LikeListDao;
import com.web.blog.dao.post.PostListDao;
import com.web.blog.dao.post.TagDao;
import com.web.blog.model.packaging.Pack;
import com.web.blog.model.packaging.Purchase;
import com.web.blog.model.packaging.Sell;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/purchase")
public class PurchaseController {

    @Autowired
    PostListDao postDao;

    @Autowired
    PackDao packDao;

    @Autowired
    PurchaseDao purchaseDao;

    @Autowired
    SellDao sellDao;

    @Autowired
    LikeListDao likeDao;

    @GetMapping("/regist/{packPost}/{email}/{sum}")
    @ApiOperation("상품 구매")
    public Object regist(@PathVariable List<String> packPost, @PathVariable String email, @PathVariable int sum)
            throws SQLException, IOException {
        try {

            Pack pack = new Pack();
            pack.setEmail(email);
            pack.setPrice(sum);
            packDao.save(pack);
            ///// 패키지테이블에 저장/////

            int packno = pack.getPackno();
            List<String> pidlist = new LinkedList<>();
            pidlist = packPost;
            for (String s : pidlist) {
                int pid = Integer.parseInt(s);
                Purchase purchase = new Purchase();
                purchase.setEmail(email);
                purchase.setPid(pid);
                purchase.setPackno(packno);
                purchase.setPuse(0);
                char[] charSet = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G',
                        'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
                StringBuffer serialno = new StringBuffer();
                for (int i = 0; i < 8; i++) {
                    int idx = (int) (charSet.length * Math.random());
                    serialno.append(charSet[idx]);
                }
                purchase.setSerialno(serialno.toString());
                purchaseDao.save(purchase);
                ///// 구매테이블에 저장/////

                Sell sell = new Sell();
                sell.setSemail(email);
                sell.setPid(pid);
                sell.setPemail(postDao.findByPid(pid).getEmail());
                sell.setCnt(1);
                sellDao.save(sell);
                ///// 판매테이블에 저장/////

                LikeList like = likeDao.findByEmailAndPidAndCart(email, pid, 1);
                likeDao.delete(like);
                ///// 장바구니테이블에서 삭제/////
            }
            return new ResponseEntity<>(pack, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/list/{email}")
    @ApiOperation("사용자 구매 리스트")
    public Object purchaseList(@PathVariable String email) throws SQLException, IOException {
        try {
            List<Pack> packlist = packDao.findByEmail(email);
            List<List<Purchase>> purlist = new LinkedList<>();
            for (Pack pack : packlist) {
                int packno = pack.getPackno();
                List<Purchase> list = purchaseDao.findByPackno(packno);
                purlist.add(list);
            }
            return new ResponseEntity<>(purlist, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}