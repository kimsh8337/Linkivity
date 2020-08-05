package com.web.blog.dao.packaging;

import com.web.blog.model.packaging.Purchase;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseDao extends JpaRepository<Purchase, String> {
    
}