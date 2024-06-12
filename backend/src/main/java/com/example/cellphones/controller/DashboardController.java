package com.example.cellphones.controller;

import com.example.cellphones.mapper.OrderDetailMapper;
import com.example.cellphones.model.OrderDetail;
import com.example.cellphones.repository.OrderDetailRepository;
import com.example.cellphones.repository.OrderRepository;
import com.example.cellphones.response.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


@RestController
@CrossOrigin
@RequestMapping(path = "/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;

    private String getMonthYearKey(Integer month, Integer year) {
        return month + "/" + year;
    }

    /**
     * Tổng doanh thu theo tháng
     * Tổng số lượng đơn hàng theo tháng
     * Tổng 3 thể loại sách được thuê nhiều nhất
     */


    @GetMapping(path = "/order-with-month")
    public ResponseEntity<?> getOrderWithMonth(@Param("year") Integer year, @Param("month") Integer month) {
        List<OrderDetail> orderDetails = this.orderDetailRepository.findWithMonth(month);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .status(HttpStatus.OK)
                        .data(orderDetails.stream().map(OrderDetailMapper::responseOrderDetailDtoFromModel)
                                .collect(Collectors.toSet()))
                        .build()
        );
    }

    @GetMapping(path = "/revenue-price")
    public ResponseEntity<?> getRevenuePriceWithMonth(@Param("year") Integer year) {
        List<Integer> months = new ArrayList<>();
        for (int i = 1; i <= 12; i++) months.add(i);
        Map<String, Integer> initMap = months.stream()
                .collect(Collectors.toMap(entry -> getMonthYearKey(entry, year), row -> 0, (k, v) -> v, LinkedHashMap::new));
        List<Object[]> dashboard = this.orderRepository.dashboard(year);
        if (!dashboard.isEmpty()) {
            dashboard.stream()
                    .collect(Collectors.groupingBy(row -> ((Integer) row[0])))
                    .forEach((status, key) -> key.forEach(row -> {
                        String month = getMonthYearKey((Integer) row[0], year);
                        int value = ((Long) row[1]).intValue();
                        initMap.put(month, value);
                    }));
        }
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .status(HttpStatus.OK)
                        .data(initMap)
                        .build()
        );
    }

    @GetMapping("/revenue-total")
    public ResponseEntity<?> getRevenueTotalWithMonth(@Param("year") Integer year) {
        List<Integer> months = new ArrayList<>();
        for (int i = 1; i <= 12; i++) months.add(i);
        Map<String, Integer> initMap = months.stream()
                .collect(Collectors.toMap(entry -> getMonthYearKey(entry, year), row -> 0, (k, v) -> v, LinkedHashMap::new));
        List<Object[]> dashboard = this.orderDetailRepository.dashboard(year);
        if (!dashboard.isEmpty()) {
            dashboard.stream()
                    .collect(Collectors.groupingBy(row -> ((Integer) row[0])))
                    .forEach((status, key) -> key.forEach(row -> {
                        String month = getMonthYearKey((Integer) row[0], year);
                        int value = ((Long) row[1]).intValue();
                        initMap.put(month, value);
                    }));
        }
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .status(HttpStatus.OK)
                        .data(initMap)
                        .build()
        );
    }

    @GetMapping("/category")
    public ResponseEntity<?> getRevenueWithMonth() {
        Map<String, Integer> result = new HashMap<>();
        List<Object[]> dashboard = this.orderDetailRepository.totalCategory();
        if (!dashboard.isEmpty()) {
            Map<String, Integer> initMap = dashboard.stream()
                    .collect(Collectors.groupingBy(row -> (String) row[0],
                            Collectors.summingInt(row -> ((Long) row[1]).intValue())));

            // Lấy top 3 phần tử và tính tổng giá trị của các phần tử còn lại
            AtomicInteger total = new AtomicInteger(0);
            initMap.entrySet().stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                    .limit(3)
                    .forEachOrdered(entry -> {
                        result.put(entry.getKey(), entry.getValue());
//                        total.addAndGet(entry.getValue());
                    });

            // Thêm tổng giá trị của các phần tử còn lại vào map
            initMap.entrySet().stream()
                    .skip(3)
                    .forEach(entry -> total.addAndGet(entry.getValue()));
            result.put("Khác", total.get());
        }
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .status(HttpStatus.OK)
                        .data(result)
                        .build()
        );
    }
}
