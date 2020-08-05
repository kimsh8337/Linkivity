package com.web.blog.dao.packaging;

import com.web.blog.model.packaging.Sell;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SellDao extends JpaRepository<Sell, String> {
    
}