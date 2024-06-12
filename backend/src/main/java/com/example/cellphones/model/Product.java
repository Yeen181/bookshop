package com.example.cellphones.model;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tbl_product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(unique = true, nullable = false, columnDefinition = "varchar(255) CHARACTER SET utf8")
    @Column(unique = true, nullable = false)
    private String name;

//    @Column(columnDefinition = "varchar(255) CHARACTER SET utf8")
    private String describe;

    private String author;

    private int price;

    private int deposit;

    private int quantity;

    private Boolean isDelete;


    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(referencedColumnName = "id")
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<CartDetail> cartDetails;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<OrderDetail> orderDetails;

    @Lob
    private String image;
}
