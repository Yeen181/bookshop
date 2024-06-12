package com.example.cellphones.model;

import javax.persistence.*;

import com.example.cellphones.model.user.User;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tbl_order")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<OrderDetail> listOrderDetail = new ArrayList<>();

    private Integer total;

//    @Column(columnDefinition = "varchar(255) CHARACTER SET utf8")
    private String payment;

//    @Column(columnDefinition = "varchar(255) CHARACTER SET utf8")
    private String receiverName;

    private String receiverPhone;

//    @Column(columnDefinition = "varchar(255) CHARACTER SET utf8")
    private String receiverAddress;

    @Temporal(TemporalType.TIMESTAMP)
    private Date giveBackDay;

//    @Column(columnDefinition = "varchar(255) CHARACTER SET utf8")
    private String note;

    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date timeOrder;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
