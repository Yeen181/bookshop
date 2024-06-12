package com.example.cellphones.repository;

import com.example.cellphones.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    @Query("select month(o.order.timeOrder), sum(o.quantity) from OrderDetail o " +
            "where year(o.order.timeOrder) =:year " +
            "group by month(o.order.timeOrder)")
    List<Object[]> dashboard(Integer year);

    @Query("select o.product.category.name, count(o.quantity) from OrderDetail o " +
            "group by o.product.category.name")
    List<Object[]> totalCategory();

    @Query("select o from OrderDetail o where month(o.order.timeOrder) = :month")
    List<OrderDetail> findWithMonth(Integer month);
}
