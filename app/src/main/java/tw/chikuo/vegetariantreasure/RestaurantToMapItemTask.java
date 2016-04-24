package tw.chikuo.vegetariantreasure;

import java.util.ArrayList;
import java.util.List;

import tw.chikuo.vegetariantreasure.object.MapItem;
import tw.chikuo.vegetariantreasure.object.parse.Restaurant;

/**
 * Created by Chi on 2016/4/23.
 */
public class RestaurantToMapItemTask {

    public static List<MapItem> translation(List<Restaurant> restaurantList){

        // Translation the restaurant object to MapObject with MapItem
        List<MapItem> mapItemList = new ArrayList<MapItem>();
        for (Restaurant restaurant : restaurantList){
            if (restaurant.getGeoPoint() != null
                    && restaurant.getGeoPoint().getLatitude() != 0
                    && restaurant.getGeoPoint().getLongitude() != 0){

                MapItem mapItem = new MapItem(
                        restaurant.getObjectId(),
                        restaurant.getName(),
                        restaurant.getGeoPoint().getLatitude(),
                        restaurant.getGeoPoint().getLongitude());
                mapItemList.add(mapItem);
            }
        }

        return mapItemList;
    }

    public static List<MapItem> translation(Restaurant restaurant){

        // Translation the restaurant object to MapObject with MapItem
        List<MapItem> mapItemList = new ArrayList<MapItem>();
        if (restaurant.getGeoPoint() != null
                && restaurant.getGeoPoint().getLatitude() != 0
                && restaurant.getGeoPoint().getLongitude() != 0){

            MapItem mapItem = new MapItem(
                    restaurant.getObjectId(),
                    restaurant.getName(),
                    restaurant.getGeoPoint().getLatitude(),
                    restaurant.getGeoPoint().getLongitude());
            mapItemList.add(mapItem);
        }

        return mapItemList;
    }

}
