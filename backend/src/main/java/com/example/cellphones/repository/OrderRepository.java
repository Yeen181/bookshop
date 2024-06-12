package com.example.cellphones.repository;

import com.example.cellphones.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o" +
            " WHERE o.user.id = :userId order by o.timeOrder desc")
    List<Order> findByUserId(@Param("userId") Long userId);

    @Query("select o from Order o where ((:contains is null or o.receiverName like %:contains% ) " +
            "and (:userId is null or o.user.id = :userId) " +
            "and (:parse is null or month(o.timeOrder) = :parse)" +
            ")")
    Page<Order> search(String contains, Long userId, Integer parse, Pageable pageable);

    @Query("select month(o.timeOrder), sum(o.total) from Order o " +
            "where year(o.timeOrder) =:year " +
            "group by month(o.timeOrder)")
    List<Object[]> dashboard(Integer year);
}
