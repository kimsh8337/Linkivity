package com.web.blog.dao.packaging;

import java.util.List;

import com.web.blog.model.packaging.Purchase;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseDao extends JpaRepository<Purchase, String> {
    List<Purchase> findByPackno(int packno);
    List<Purchase> findBySeller(String seller);
}