package com.example.cellphones.model;

import com.example.cellphones.model.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbl_cart")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cart{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<CartDetail> cartDetails = new ArrayList<>();
}
