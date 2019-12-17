package hardcastle.com.churchapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import hardcastle.com.churchapplication.Fragment.BibleFragment;
import hardcastle.com.churchapplication.Fragment.ChangePasswordFragment;
import hardcastle.com.churchapplication.Fragment.DonationFragment;
import hardcastle.com.churchapplication.Fragment.EditProfileFragment;
import hardcastle.com.churchapplication.Fragment.EventDetailsFragment;
import hardcastle.com.churchapplication.Fragment.EventFragment;
import hardcastle.com.churchapplication.Fragment.EventPaymentFragment;
import hardcastle.com.churchapplication.Fragment.FAQsFragment;
import hardcastle.com.churchapplication.Fragment.GalleryFragment;
import hardcastle.com.churchapplication.Fragment.HomeFragment;
import hardcastle.com.churchapplication.Fragment.NotificationFragment;
import hardcastle.com.churchapplication.Fragment.PaymentFragment;
import hardcastle.com.churchapplication.Fragment.PaymentMethodFragment;
import hardcastle.com.churchapplication.Fragment.PrayerFragment;
import hardcastle.com.churchapplication.Fragment.SermonsFragment;
import hardcastle.com.churchapplication.Fragment.TodaysEventFragment;
import hardcastle.com.churchapplication.Fragment.ViewProfileFragment;

public class DashBoardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SharedPreferences sharedPreferences;
    private boolean doubleBackToExitPressedOnce = false;
    private TextView mTextMessage;
    //G+ Login
    private GoogleSignInOptions gso;
    private Fragment selectedFragment = null;
    private FragmentTransaction transaction;
    private GoogleSignInAccount account;
    private GoogleSignInClient mGoogleSignInClient;
    private Toolbar toolbar;
    private String lastName;
    private String firstName;
    private BottomNavigationView navigation;
    private int selectedItemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
     /*   toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(null);
        setTitle("Church App");
        setSupportActionBar(toolbar);*/

        sharedPreferences = getSharedPreferences(getResources().getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        //Navigation Drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        // get menu from navigationView
        Menu menu = navigationView.getMenu();
        // find MenuItem you want to change
        MenuItem nav_camara = menu.findItem(R.id.nav_sign_in);
        if (sharedPreferences.getBoolean(getResources().getString(R.string.preference_is_login), false)) {
            // set new title to the MenuItem
            nav_camara.setTitle("Sign Out");
        } else {
            // set new title to the MenuItem
            //nav_camara.setTitle("NewTitleForCamera");
        }

        navigationView.setNavigationItemSelectedListener(this);

        //Bottom Navigation
        mTextMessage = (TextView) findViewById(R.id.message);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        //navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (savedInstanceState == null) {
            selectedFragment = new HomeFragment();
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, selectedFragment);
            //transaction.addToBackStack("HomeFragment");
            transaction.commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //disableShiftMode(navigation);
        navigation.setSelectedItemId(selectedItemId);

        if ((account = GoogleSignIn.getLastSignedInAccount(this)) != null) {
            mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
            lastName = account.getFamilyName();
            firstName = account.getGivenName();
        } else {
            lastName = "";
            firstName = "";
        }
    }

  /*  @SuppressLint("RestrictedApi")
    public static void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                //noinspection RestrictedApi
                item.setShifting(false);
                // set once again checked value, so view will be updated
                //noinspection RestrictedApi
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("BNVHelper", "Unable to get shift mode field", e);
        } catch (IllegalAccessException e) {
            Log.e("BNVHelper", "Unable to change value of shift mode", e);
        }
    }*/

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        String tag = null;

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //toolbar.setTitle("Home");
                    selectedItemId = item.getItemId();
                    selectedFragment = new HomeFragment();
                    tag = "HomeFragment";
                    // mTextMessage.setText(R.string.title_home);
                    break;
                case R.id.navigation_cross:
                    //mTextMessage.setText(R.string.title_dashboard);
                    selectedItemId = item.getItemId();
                    selectedFragment = BibleFragment.newInstance();
                    break;
                case R.id.navigation_event:
                    selectedItemId = item.getItemId();
                    selectedFragment = TodaysEventFragment.newInstance();
                    tag = "TodaysEventFragment";
                    Bundle bundle=new Bundle();
                    bundle.putInt("TAB",0);
                    selectedFragment.setArguments(bundle);
                    //mTextMessage.setText(R.string.title_notifications);
                    break;
                case R.id.navigation_donation:
                    selectedItemId = item.getItemId();
                    selectedFragment = DonationFragment.newInstance();
                    tag = "DonationFragment";
                    //mTextMessage.setText(R.string.title_dashboard);
                    // return true;
                    break;
                case R.id.navigation_profile:
                    selectedItemId = item.getItemId();
                    selectedFragment = /*PrayerFragment.newInstance();*/ViewProfileFragment.newInstance(firstName, lastName);
                    tag = "EditProfileFragment";
                    break;
            }

            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, selectedFragment);
            transaction.addToBackStack(tag);
            transaction.commit();
            return true;
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        Fragment currentFrag = getSupportFragmentManager().findFragmentById(R.id.frame_layout);
        if (drawer.isDrawerOpen(Gravity.RIGHT)) {
            drawer.closeDrawer(Gravity.RIGHT);
        } else if (currentFrag instanceof SermonsFragment) {
            showFragment(new HomeFragment(), DashBoardActivity.this);
        } else if (currentFrag instanceof GalleryFragment) {
            showFragment(new HomeFragment(), DashBoardActivity.this);
        } else if (currentFrag instanceof EventFragment) {

            showFragment(new HomeFragment(), DashBoardActivity.this);

        } else if (currentFrag instanceof EventDetailsFragment) {
            showFragment(new TodaysEventFragment(), DashBoardActivity.this);
        } else if (currentFrag instanceof ViewProfileFragment && navigation.getSelectedItemId() == R.id.navigation_profile) {
            navigation.setSelectedItemId(R.id.navigation_home);
            showFragment(new HomeFragment(), DashBoardActivity.this);
        } else if (currentFrag instanceof HomeFragment) {
            finish();
            /*    int count = getSupportFragmentManager().getBackStackEntryCount();
             *//*   for (int i=count;i>=1;i--){
                getSupportFragmentManager().popBackStack();
            }*//*
            //navigation.setSelectedItemId(R.id.navigation_home);
            super.onBackPressed();*/
        } else if (currentFrag instanceof DonationFragment && navigation.getSelectedItemId() == R.id.navigation_donation) {

            navigation.setSelectedItemId(R.id.navigation_home);
            showFragment(new HomeFragment(), DashBoardActivity.this);

        } else if (currentFrag instanceof PaymentMethodFragment) {
            showFragment(new DonationFragment(), DashBoardActivity.this);

        } else if (currentFrag instanceof EventPaymentFragment) {
            showFragment(new EventDetailsFragment(), DashBoardActivity.this);

        } else if (currentFrag instanceof PaymentFragment) {
            showFragment(new PaymentMethodFragment(), DashBoardActivity.this);
        } else if (currentFrag instanceof TodaysEventFragment && navigation.getSelectedItemId() == R.id.navigation_event) {
            navigation.setSelectedItemId(R.id.navigation_home);
            showFragment(new HomeFragment(), DashBoardActivity.this);
        } /*else {
            getSupportFragmentManager().popBackStack();
        }*/ else {
            showFragment(new HomeFragment(), DashBoardActivity.this);
        }
      /*  int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            // super.onBackPressed();
            //additional code
            selectedFragment = new HomeFragment();
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, selectedFragment);
            //transaction.addToBackStack("HomeFragment");
            transaction.commit();
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            //Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 1000);
        } else {

           *//* Fragment currentFrag = getSupportFragmentManager().findFragmentById(R.id.frame_layout);
            if (count == 1) {
                selectedFragment = new HomeFragment();
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                //transaction.addToBackStack("HomeFragment");
                transaction.commit();*//*
            //  } else {
            getSupportFragmentManager().popBackStack();
            // }


        }
*/
    }

    public void showFragment(Fragment selectedFragment, DashBoardActivity dashBoardActivity) {
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, selectedFragment).addToBackStack(null);
        //transaction.addToBackStack("HomeFragment");
        transaction.commit();
    }

 /*   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_sign_in) {
            if (item.getTitle().equals("Sign Out")) {
                // Check for existing Google Sign In account, if the user is already signed in
// the GoogleSignInAccount will be non-null.

                if ((account = GoogleSignIn.getLastSignedInAccount(this)) != null) {
                    mGoogleSignInClient.signOut();
                }
                //signOut();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                Intent loginIntent = new Intent(DashBoardActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                finish();
            } else {
                Intent loginIntent = new Intent(DashBoardActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                this.finish();
            }
            // Handle the camera action
        } else if (id == R.id.nav_notification) {
            selectedFragment = NotificationFragment.newInstance();

        } else if (id == R.id.nav_change_password) {
            selectedFragment = ChangePasswordFragment.newInstance();

        } else if (id == R.id.nav_edit_profile) {
            selectedFragment = EditProfileFragment.newInstance(firstName, lastName);

        } else if (id == R.id.nav_faqs) {
            selectedFragment = FAQsFragment.newInstance();

        } else if (id == R.id.nav_prayer) {
            selectedFragment = PrayerFragment.newInstance();

        }
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, selectedFragment);
        transaction.addToBackStack(null);
        transaction.commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(Gravity.RIGHT);
        return true;
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
