package Kartik.restaurant.services;

import Kartik.restaurant.domain.RestaurantCreateUpdateRequest;
import Kartik.restaurant.domain.entities.Restaurant;

public interface RestaurantService {
    Restaurant createRestaurant(RestaurantCreateUpdateRequest request);
}
