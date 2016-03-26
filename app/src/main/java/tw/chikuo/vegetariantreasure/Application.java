package tw.chikuo.vegetariantreasure;

import com.parse.Parse;
import com.parse.ParseObject;

import tw.chikuo.vegetariantreasure.Object.parse.Restaurant;

/**
 * Created by Chi on 2016/3/26.
 */
public class Application extends android.app.Application {

    public static final String PARSE_APP_ID = "xnexJCqbtttbL0maj1FzWfyz1uNe6RcYSkb6GTAj";
    public static final String PARSE_CLIENT_KEY = "AszenGyRN8oJV5Pg6zGuAn5xSAjUBE232A4JmDiO";

    @Override
    public void onCreate() {
        super.onCreate();

        // Parse
        Parse.enableLocalDatastore(this);
        ParseObject.registerSubclass(Restaurant.class);
        Parse.initialize(this, PARSE_APP_ID, PARSE_CLIENT_KEY);
    }
}
