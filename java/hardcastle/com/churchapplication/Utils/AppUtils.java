package hardcastle.com.churchapplication.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;

public class AppUtils {

    public static boolean isConnected() {
        NetworkInfo activeNetwork = null;
        try {
            ConnectivityManager
                    cm = (ConnectivityManager) MyApplication.getInstance().getApplicationContext()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            activeNetwork = cm.getActiveNetworkInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();
    }
}
