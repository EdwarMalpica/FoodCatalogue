package online.grupo3devops.foodcatalogue.service;

import online.grupo3devops.foodcatalogue.dto.FoodCatalogePage;
import online.grupo3devops.foodcatalogue.dto.FoodItemDTO;
import online.grupo3devops.foodcatalogue.dto.Restaurant;
import online.grupo3devops.foodcatalogue.entity.FoodItem;
import online.grupo3devops.foodcatalogue.mapper.FoodItemMapper;
import online.grupo3devops.foodcatalogue.repo.FoodItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FoodCatalogeService {
    @Autowired
    FoodItemRepo foodItemRepo;
    @Autowired
    RestTemplate restTemplate;


    public List<FoodItemDTO> findAllFoodItems(){
        List<FoodItem> foodItems = foodItemRepo.findAll();
        return foodItems.stream().map(FoodItemMapper.INSTANCE::mapFoodItemToFoodItemDto).collect(Collectors.toList());
    }
    public FoodItemDTO addFoodItemInBD(FoodItemDTO foodItemDTO){
        FoodItem saveFoodItem = foodItemRepo.save(FoodItemMapper.INSTANCE.mapFoodItemDTOToFoodItem(foodItemDTO));
        return FoodItemMapper.INSTANCE.mapFoodItemToFoodItemDto(saveFoodItem);
    }

    public ResponseEntity<FoodItemDTO> fetchItemFoodById(Long id){
        Optional<FoodItem> foodItem = foodItemRepo.findById(id);
        if(foodItem.isEmpty()){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(FoodItemMapper.INSTANCE.mapFoodItemToFoodItemDto(foodItem.get()),HttpStatus.OK);
    }
    public FoodCatalogePage fetchFoodCatalogePageDetails(Long restaurantId){
        List<FoodItem> foodItemList = fetchFoodItemList(restaurantId);
        Restaurant restaurant = fetchRestaurantDetailsFromRestaurantMS(restaurantId);
        return createFoodCatalogePage(foodItemList, restaurant);
    }
    private FoodCatalogePage createFoodCatalogePage(
            List<FoodItem> foodItemList, Restaurant restaurant
    ){
        FoodCatalogePage foodCatalogePage = new FoodCatalogePage();
        foodCatalogePage.setFoodItemList(foodItemList);
        foodCatalogePage.setRestaurant(restaurant);
        return foodCatalogePage;
    }

    private Restaurant fetchRestaurantDetailsFromRestaurantMS(Long restauranId){
        return restTemplate.getForObject(
                "http://RESTAURANT-SERVICE/restaurant/fetchById/"+restauranId,Restaurant.class
        );
    }

    private List<FoodItem> fetchFoodItemList(Long restaurantId){
        return foodItemRepo.findByRestaurantId(restaurantId);
    }
}
