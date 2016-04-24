package tw.chikuo.vegetariantreasure.object;

import java.io.Serializable;
import java.util.List;

import tw.chikuo.vegetariantreasure.object.parse.Restaurant;

/**
 * Created by Chi on 2016/4/23.
 * Because we can pass parseObject to another activity, so we create this.
 */
public class MapItem implements Serializable {

    private String name;
    private String restaurantId;
    private double longitude;
    private double latitude;

    public MapItem() {
    }

    public MapItem(String restaurantId,String name,  double latitude, double longitude) {
        this.name = name;
        this.restaurantId = restaurantId;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
