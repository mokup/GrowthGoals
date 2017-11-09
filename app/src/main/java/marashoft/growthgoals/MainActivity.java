package marashoft.growthgoals;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import marashoft.growthgoals.fragments.AbstractFragment;
import marashoft.growthgoals.fragments.custom.CustomDateFragment;
import marashoft.growthgoals.fragments.daily.DailyFragment;
import marashoft.growthgoals.fragments.monthly.MonthlyFragment;
import marashoft.growthgoals.fragments.weekly.WeeklyFragment;
import marashoft.growthgoals.fragments.yearly.YearlyFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            int ACCESS_EXTERNAL_STORAGE_STATE = 1;

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    ACCESS_EXTERNAL_STORAGE_STATE);
        }

        setContentView(R.layout.activity_main);
        displayFragment(R.id.nav_daily);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);



//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    private void displayFragment(int item){
        AbstractFragment frag=null;
        switch (item){
            case R.id.nav_daily:
                frag=new DailyFragment();

                break;
            case R.id.nav_weekly:
                frag=new WeeklyFragment();
                break;
            case R.id.nav_monthly:
                frag=new MonthlyFragment();
                break;
            case R.id.nav_yearly:
                frag=new YearlyFragment();
                break;
            case R.id.nav_custom:
                frag=new CustomDateFragment();
                break;
        }
        if(frag!=null){
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            frag.setMenu(drawer);
            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.include,frag);
            ft.commit();
        }

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

       displayFragment(id);


        return true;
    }


    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Write to the storage (ex: call appendByteBuffer(byte[] data) here)

                } else {
                    Toast.makeText(getApplicationContext(), "Please grant permission.", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }





}
