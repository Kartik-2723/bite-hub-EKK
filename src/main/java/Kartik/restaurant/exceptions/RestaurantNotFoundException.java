package Kartik.restaurant.exceptions;

public class RestaurantNotFoundException extends BaseException{
    public RestaurantNotFoundException() {
    }

    public RestaurantNotFoundException(Throwable cause) {
        super(cause);
    }

    public RestaurantNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public RestaurantNotFoundException(String message) {
        super(message);
    }
}
