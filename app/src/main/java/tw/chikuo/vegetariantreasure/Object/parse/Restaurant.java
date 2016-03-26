package tw.chikuo.vegetariantreasure.Object.parse;

import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

/**
 * Created by Chi on 2016/3/26.
 */
@ParseClassName("Restaurant")
public class Restaurant extends ParseObject {

    // name
    public String getName() {
        return getString("name");
    }
    public void setName(String name) {
        put("name", name);
    }
    // address
    public String getAddress() {
        return getString("address");
    }
    public void setAddress(String address) {
        put("address", address);
    }
    // phone
    public String getPhone() {
        return getString("phone");
    }
    public void setPhone(String phone) {
        put("phone", phone);
    }

    // geoPoint
    public ParseGeoPoint getGeoPoint() {
        return getParseGeoPoint("geoPoint");
    }

    public void setGeoPoint(ParseGeoPoint geoPoint) {
        put("geoPoint", geoPoint);
    }
}
