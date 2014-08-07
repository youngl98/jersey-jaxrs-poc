package com.visa.gpd.data.jpa.repository;

import com.visa.gpd.data.jpa.entity.Consumer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * User: younglee
 * Date: 8/5/14
 */
@Repository("consumerRepository")
public interface ConsumerRepository extends JpaRepository<Consumer, Long> {

}
