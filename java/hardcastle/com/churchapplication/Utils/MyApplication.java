package hardcastle.com.churchapplication.Utils;

import android.content.Context;
import android.support.multidex.MultiDexApplication;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Set;

public class MyApplication extends MultiDexApplication {

    public static MyApplication globalInstance = null;
    public HashMap<String, String> hashmapKeyValue = new HashMap<String, String>();


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //MultiDex.install(this);
    }


    public MyApplication() {
    }


    public static synchronized MyApplication getInstance() {

        if (globalInstance == null) {

            globalInstance = new MyApplication();
        }

        return globalInstance;
    }


    public JsonObject getInput(String TAG, HashMap<String, String> hashMap) {

        JsonObject paramObject = new JsonObject();
        Set<String> keys = hashMap.keySet();

        try {

            for (String key : keys) {
                paramObject.addProperty(key, hashMap.get(key));
            }

            Log.i(TAG, "INPUT JSON : " + paramObject.toString());
            String s = paramObject.toString().trim();
            String s1 = s;
        } catch (JsonParseException e) {
            e.printStackTrace();
        }

        return paramObject;
    }

    public static void showMessage(Context context, String message) {

        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
