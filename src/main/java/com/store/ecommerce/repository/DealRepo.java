package com.store.ecommerce.repository;

import com.store.ecommerce.model.Deal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DealRepo extends JpaRepository<Deal, Long> {

}
