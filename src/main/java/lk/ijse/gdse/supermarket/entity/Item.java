package lk.ijse.gdse.supermarket.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * --------------------------------------------
 * Author: Shamodha Sahan
 * GitHub: https://github.com/shamodhas
 * Website: https://shamodha.live
 * --------------------------------------------
 * Created: 2/21/2025 1:46 PM
 * Project: Supermarket-72
 * --------------------------------------------
 **/

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "item")
public class Item  implements SuperEntity{
    @Id
    @Column(name = "item_id")
    private String id;

    private String name;
    private int quantity;

    // 0000000000.00 - format
    // 100 -> 100.00
    @Column(precision = 10, scale = 2)
    private BigDecimal price;

//    @OneToMany
//    private List<OrderDetails> orderDetails;
}
