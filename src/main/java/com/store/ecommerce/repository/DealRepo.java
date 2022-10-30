package com.store.ecommerce.repository;

import com.store.ecommerce.model.Deal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DealRepo extends JpaRepository<Deal, Long> {

    @Query(value = "SELECT * FROM Deal d JOIN Product_Deal pd on d.deal_id = pd.deal_id WHERE pd.product_deal_type = 'ELIGIBLE_ITEM' and pd.product_id = ?1", nativeQuery = true)
    List<Deal> findByProductId(Long productId);
}
