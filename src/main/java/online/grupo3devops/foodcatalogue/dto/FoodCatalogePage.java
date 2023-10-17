package online.grupo3devops.foodcatalogue.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import online.grupo3devops.foodcatalogue.entity.FoodItem;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodCatalogePage {
    private List<FoodItem> foodItemList;
    private Restaurant restaurant;

}
