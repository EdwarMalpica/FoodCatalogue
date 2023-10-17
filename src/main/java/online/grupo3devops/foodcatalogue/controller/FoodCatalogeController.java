package online.grupo3devops.foodcatalogue.controller;

import online.grupo3devops.foodcatalogue.dto.FoodCatalogePage;
import online.grupo3devops.foodcatalogue.dto.FoodItemDTO;
import online.grupo3devops.foodcatalogue.service.FoodCatalogeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@CrossOrigin
public class FoodCatalogeController {

    @Autowired
    FoodCatalogeService foodCatalogeService;

    @GetMapping("/fetchAllFoodItems")
    public ResponseEntity<List<FoodItemDTO>> fetchAllFoodItems(){
        List<FoodItemDTO> allFoodItems = foodCatalogeService.findAllFoodItems();
        return new ResponseEntity<>(allFoodItems, HttpStatus.OK);
    }
    @PostMapping("/addFoodItem")
    public ResponseEntity<FoodItemDTO> saveFoodItem (@RequestBody FoodItemDTO foodItemDTO){
        FoodItemDTO foodItemAdded = foodCatalogeService.addFoodItemInBD(foodItemDTO);
        return new ResponseEntity<>(foodItemAdded, HttpStatus.CREATED);
    }

    @GetMapping("/fetchById/{id}")
    public ResponseEntity<FoodItemDTO> findfoodItemById(@PathVariable Long id){
        return foodCatalogeService.fetchItemFoodById(id);
    }
    @GetMapping("fetchRestaurantAndFoodItemsById/{restaurantId}")
    public ResponseEntity<FoodCatalogePage> fetchRestaurantDetailsWithFoodMenu(
            @PathVariable Long restaurantId){
        FoodCatalogePage foodCatalogePage =
                foodCatalogeService.fetchFoodCatalogePageDetails(restaurantId);
        return new ResponseEntity<>(foodCatalogePage, HttpStatus.OK);
    }

}
