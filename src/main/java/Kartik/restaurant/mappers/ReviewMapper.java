package Kartik.restaurant.mappers;

import Kartik.restaurant.domain.ReviewCreateUpdateRequest;
import Kartik.restaurant.domain.dtos.ReviewCreateUpdateRequestDto;
import Kartik.restaurant.domain.dtos.ReviewDto;
import Kartik.restaurant.domain.entities.Review;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReviewMapper {

    ReviewCreateUpdateRequest toReviewCreateUpdateRequest(ReviewCreateUpdateRequestDto dto);

    ReviewDto toDto(Review review);
}
