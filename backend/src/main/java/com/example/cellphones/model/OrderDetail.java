package com.example.cellphones.model;
import javax.persistence.*;
import lombok.*;


@Entity
@Table(name = "tbl_order_detail")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Order order;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Product product;

    private int quantity;
}
