package hardcastle.com.churchapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SplashActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private ImageView image_1;
    private Animation animation1;
    private MessageDigest md;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sharedPreferences = getSharedPreferences(getResources().getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        animation1 = AnimationUtils.loadAnimation(this, R.anim.blink_animation);

        image_1 = (ImageView) findViewById(R.id.splash_image);


       /* try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "hardcastle.com.churchapplication",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i("", "printHashKey() Hash Key: " + hashKey);
            }*//*2jmj7l5rSw0yVb/vlWAYkK/YBwk=*//*

        } catch (PackageManager.NameNotFoundException e) {
            Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
        } catch (NoSuchAlgorithmException e) {
            Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
        }*/


      /*  final Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                image_1.startAnimation(animation1);
            }
        }, 1000);*/
        image_1.startAnimation(animation1);
        animation1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (sharedPreferences.getBoolean(getResources().getString(R.string.preference_is_login), false)) {
                    Intent intent = new Intent(SplashActivity.this, DashBoardActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
