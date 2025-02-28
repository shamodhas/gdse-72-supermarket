package lk.ijse.gdse.supermarket.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * --------------------------------------------
 * Author: Shamodha Sahan
 * GitHub: https://github.com/shamodhas
 * Website: https://shamodha.live
 * --------------------------------------------
 * Created: 2/21/2025 2:23 PM
 * Project: Supermarket-72
 * --------------------------------------------
 **/

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class OrderDetailsId {
    private String orderId;
    private String itemId;
}
