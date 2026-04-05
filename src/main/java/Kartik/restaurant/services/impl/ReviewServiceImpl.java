package Kartik.restaurant.services.impl;

import Kartik.restaurant.domain.ReviewCreateUpdateRequest;
import Kartik.restaurant.domain.entities.Photo;
import Kartik.restaurant.domain.entities.Restaurant;
import Kartik.restaurant.domain.entities.Review;
import Kartik.restaurant.domain.entities.User;
import Kartik.restaurant.exceptions.RestaurantNotFoundException;
import Kartik.restaurant.exceptions.ReviewNotAllowedException;
import Kartik.restaurant.repositories.RestaurantRepository;
import Kartik.restaurant.services.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final RestaurantRepository restaurantRepository;

    @Override
    public Review createReview(User author, String restaurantId, ReviewCreateUpdateRequest review) {
        Restaurant restaurant = getRestaurantOrThrow(restaurantId);

        boolean hasExistingReviews = restaurant.getReviews().stream()
                .anyMatch(r -> r.getWrittenBy().getId().equals(author.getId()));

        if(hasExistingReviews){
            throw new ReviewNotAllowedException("User has already reviewed this restaurant");
        }

        LocalDateTime now = LocalDateTime.now();

        List<Photo> photos = review.getPhotoIds().stream().map(
                url -> {
                    return Photo.builder()
                            .url(url)
                            .uploadDate(now)
                            .build();
                }).toList();

        String reviewId = UUID.randomUUID().toString();
        Review reviewToCreate = Review.builder()
                .id(reviewId)
                .content(review.getContent())
                .rating(review.getRating())
                .photos(photos)
                .datePosted(now)
                .lastEdited(now)
                .writtenBy(author)
                .build();

        restaurant.getReviews().add(reviewToCreate);

        updateRestaurantAverageRating(restaurant);

        Restaurant saveRestaurant = restaurantRepository.save(restaurant);

        return getReviewFromRestaurant(reviewId, saveRestaurant)
                .orElseThrow(()-> new RuntimeException("Error retrieving created review"));
    }

    @Override
    public Page<Review> listReviews(String restaurantId, Pageable pageable) {
        Restaurant restaurant = getRestaurantOrThrow(restaurantId);
        List<Review> reviews = restaurant.getReviews();

        Sort sort = pageable.getSort();


        if(sort.isSorted()){
            Sort.Order order = sort.iterator().next();
            String property  = order.getProperty();
            boolean isAscending = order.getDirection().isAscending();

            Comparator<Review> comparator = switch (property) {
                case "datePosted" -> Comparator.comparing(Review::getDatePosted);
                case "rating"     -> Comparator.comparing(Review::getRating);
                default -> Comparator.comparing(Review::getDatePosted);
            };

            reviews.sort(isAscending ? comparator : comparator.reversed());

        }else{
            reviews.sort(Comparator.comparing(Review::getDatePosted));
        }

        int start = (int) pageable.getOffset();

        if(start>= reviews.size()){
            return new PageImpl<>(Collections.emptyList(),pageable, reviews.size());
        }

        int end = Math.min(start + pageable.getPageSize(), reviews.size());

        return new PageImpl<>(reviews.subList(start,end),pageable, reviews.size());
    }

    @Override
    public Optional<Review> getReview(String restaurantId, String reviewId) {
        Restaurant restaurant = getRestaurantOrThrow(restaurantId);
        return getReviewFromRestaurant(reviewId, restaurant);
    }

    private static Optional<Review> getReviewFromRestaurant(String reviewId, Restaurant restaurant) {
        return restaurant.getReviews()
                .stream().filter(r -> reviewId.equals(r.getId()))
                .findFirst();
    }

    @Override
    public Review updateReview(User author, String restaurantId, String reviewId, ReviewCreateUpdateRequest review) {
        Restaurant restaurant = getRestaurantOrThrow(restaurantId);

        String authorId = author.getId();
        Review existingReview = getReviewFromRestaurant(reviewId, restaurant)
                .orElseThrow(() -> new ReviewNotAllowedException("Review does not exist"));

        if(!authorId.equals(existingReview.getWrittenBy().getId())){
            throw new ReviewNotAllowedException("Cannot update another user's review");
        }

        if(LocalDateTime.now().isAfter(existingReview.getDatePosted().plusHours(48))){
            throw new ReviewNotAllowedException("Review can no longer be edited");
        }

        existingReview.setContent(review.getContent());
        existingReview.setRating(review.getRating());
        existingReview.setLastEdited(LocalDateTime.now());

        existingReview.setPhotos(review.getPhotoIds().stream()
                .map(photoId ->{
                    return Photo.builder()
                            .url(photoId)
                            .uploadDate(LocalDateTime.now())
                            .build();
                }).toList());

        updateRestaurantAverageRating(restaurant);

        List<Review> updatedReviews = restaurant.getReviews().stream().filter(r -> !reviewId.equals(r.getId()))
                .collect(Collectors.toList());

        updatedReviews.add(existingReview);

        restaurant.setReviews(updatedReviews);

        restaurantRepository.save(restaurant);

        return existingReview;

    }

    @Override
    public void deleteReview(String restaurantId, String reviewId) {

        Restaurant restaurant = getRestaurantOrThrow(restaurantId);

        List<Review> list = restaurant.getReviews().stream()
                .filter(r -> !reviewId.equals(r.getId()))
                .toList();

        restaurant.setReviews(list);
        updateRestaurantAverageRating(restaurant);
        restaurantRepository.save(restaurant);
    }

    private Restaurant getRestaurantOrThrow(String restaurantId) {
        return restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant with id not found" + restaurantId)
                );
    }

    private void updateRestaurantAverageRating(Restaurant restaurant){
        List<Review> reviews = restaurant.getReviews();
        if(reviews.isEmpty()){
            restaurant.setAverageRating(0.0f);
        }else{
            double avgRating = reviews.stream().mapToDouble(Review::getRating)
                    .average()
                    .orElse(0.0);

            restaurant.setAverageRating((float)avgRating);
        }

    }

}
