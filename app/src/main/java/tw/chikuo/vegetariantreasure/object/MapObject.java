package tw.chikuo.vegetariantreasure.object;

import java.io.Serializable;
import java.util.List;

import tw.chikuo.vegetariantreasure.object.parse.Restaurant;

/**
 * Created by Chi on 2016/4/23.
 */
public class MapObject implements Serializable {

    private List<MapItem> mapItemList;
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<MapItem> getMapItemList() {
        return mapItemList;
    }

    public void setMapItemList(List<MapItem> mapItemList) {
        this.mapItemList = mapItemList;
    }
}
