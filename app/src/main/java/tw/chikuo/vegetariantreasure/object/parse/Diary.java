package tw.chikuo.vegetariantreasure.object.parse;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

/**
 * Created by Chi on 2016/3/26.
 */
@ParseClassName("Diary")
public class Diary extends ParseObject{

    // title
    public String getTitle() {
        return getString("title");
    }
    public void setTitle(String title) {
        put("title", title);
    }
    // url
    public String getUrl() {
        return getString("url");
    }
    public void setUrl(String url) {
        put("url", url);
    }
    // restaurantPointer
    public Restaurant getRestaurantPointer() {
        return (Restaurant) getParseObject("restaurantPointer");
    }
    public void setRestaurantPointer(Restaurant restaurantPointer) {
        put("restaurantPointer", restaurantPointer);
    }
}
