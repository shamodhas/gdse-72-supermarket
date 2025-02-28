package lk.ijse.gdse.supermarket.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * --------------------------------------------
 * Author: Shamodha Sahan
 * GitHub: https://github.com/shamodhas
 * Website: https://shamodha.live
 * --------------------------------------------
 * Created: 2/21/2025 1:38 PM
 * Project: Supermarket-72
 * --------------------------------------------
 **/

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "customer")
public class Customer implements SuperEntity{
    @Id
    @Column(name = "cus_id")
    private String id;

    private String name;
    private String email;
    private String nic;
    private String phone;

//    @OneToMany(
//            mappedBy = "customer",
//            cascade = CascadeType.ALL
//    )
//    private List<Order> orders;
}
