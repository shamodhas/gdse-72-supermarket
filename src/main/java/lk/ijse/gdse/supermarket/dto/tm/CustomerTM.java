package lk.ijse.gdse.supermarket.dto.tm;

import lombok.*;

/**
 * --------------------------------------------
 * Author: R.I.B. Shamodha Sahan Rathnamalala
 * GitHub: https://github.com/shamodhas
 * Website: https://shamodha.live
 * --------------------------------------------
 * Created: 10/9/2024 9:49 AM
 * Project: Supermarket
 * --------------------------------------------
 **/

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerTM {
    private String id;
    private String name;
    private String nic;
    private String email;
    private String phone;
}
