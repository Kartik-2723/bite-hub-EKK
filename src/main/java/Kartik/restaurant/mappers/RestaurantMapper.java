package Kartik.restaurant.mappers;

import Kartik.restaurant.domain.RestaurantCreateUpdateRequest;
import Kartik.restaurant.domain.dtos.GeoPointDto;
import Kartik.restaurant.domain.dtos.RestaurantCreateUpdateRequestDto;
import Kartik.restaurant.domain.dtos.RestaurantDto;
import Kartik.restaurant.domain.entities.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RestaurantMapper {

    RestaurantCreateUpdateRequest toRestaurantCreateUpdateRequest(RestaurantCreateUpdateRequestDto dto);

    RestaurantDto toRestaurantDto(Restaurant restaurant);

    @Mapping(target="latitude",expression = "java(geoPoint.getLat())")
    @Mapping(target="longitude",expression = "java(geoPoint.getLon())")
    GeoPointDto toGeoPointdto(GeoPoint geoPoint);

}
