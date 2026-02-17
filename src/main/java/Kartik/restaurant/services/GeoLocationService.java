package Kartik.restaurant.services;

import Kartik.restaurant.domain.GeoLocation;
import Kartik.restaurant.domain.entities.Address;

public interface GeoLocationService {
    GeoLocation geoLocate(Address address);
}
