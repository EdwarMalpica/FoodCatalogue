package online.grupo3devops.foodcatalogue.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodItemDTO {
    private long id;
    private String itemName;
    private String itemDescription;
    private boolean isVeg;
    private Number price;
    private Long restaurantId;
    private Integer quantity;
}
