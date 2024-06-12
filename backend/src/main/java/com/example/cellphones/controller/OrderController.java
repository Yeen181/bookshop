package com.example.cellphones.controller;

import com.example.cellphones.dto.request.order.CreateOrderReq;
import com.example.cellphones.dto.request.order.UpdateOrderStatusReq;
import com.example.cellphones.dto.request.product.SearchProductReq;
import com.example.cellphones.model.user.User;
import com.example.cellphones.response.ResponseObject;
import com.example.cellphones.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@CrossOrigin
@RequestMapping(path = "/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping(path = "/create")
    public ResponseEntity<?> createOrder(@RequestBody CreateOrderReq req) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .status(HttpStatus.OK)
                        .data(orderService.createOrder(req, currentUser.getId()))
                        .build()
        );
    }

    @PostMapping(path = "/update-status")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> updateStatusOrder(@RequestBody UpdateOrderStatusReq req) {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .status(HttpStatus.OK)
                        .data(orderService.updateOrderStatus(req))
                        .build()
        );
    }

    @GetMapping(path = "/search")
    public ResponseEntity<?> searchOrder(@Param("searchText") String searchText,
                                         @Param("userId") Long userId,
                                         @Param("page") Integer page,
                                         @Param("limit") Integer limit,
                                         @Param("isPaginate") Boolean isPaginate,
                                         @Param("month") Integer month) throws ParseException {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .status(HttpStatus.OK)
                        .data(orderService.searchOrder(searchText, userId, month, page, limit, isPaginate))
                        .build()
        );
    }
}
