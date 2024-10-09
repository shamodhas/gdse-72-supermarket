package lk.ijse.gdse.supermarket.dto.tm;

import lombok.*;

/**
 * --------------------------------------------
 * Author: R.I.B. Shamodha Sahan Rathnamalala
 * GitHub: https://github.com/shamodhas
 * Website: https://shamodha.live
 * --------------------------------------------
 * Created: 9/30/2024 12:50 PM
 * Project: supermarketfx
 * --------------------------------------------
 **/

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ItemTM {
    private String itemId;
    private String name;
    private int quantity;
    private double price;
}
