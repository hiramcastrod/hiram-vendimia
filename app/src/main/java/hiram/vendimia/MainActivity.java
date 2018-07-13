package hiram.vendimia;

import android.app.FragmentManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.Toolbar;

import hiram.vendimia.fragments.ArticlesFragment;
import hiram.vendimia.fragments.ClientsFragment;
import hiram.vendimia.fragments.ConfigurationFragment;
import hiram.vendimia.fragments.SalesFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle( this, drawerLayout, null, R.string.open_nav_drawer, R.string.close_nav_drawer);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
        onNavigationItemSelected(navigationView.getMenu().getItem(0));

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        final android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        final android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (item.getItemId()){
            case R.id.salesItem:
                fragmentTransaction.replace(R.id.mainContainer, new SalesFragment()).commit();
                showToolbar(getString(R.string.tabSales));
                break;
            case R.id.clientsItem:
                fragmentTransaction.replace(R.id.mainContainer, new ClientsFragment()).commit();
                showToolbar(getString(R.string.clientTab));
                break;
            case R.id.articlesItem:
                fragmentTransaction.replace(R.id.mainContainer, new ArticlesFragment()).commit();
                showToolbar(getString(R.string.articleTab));
                break;
            case R.id.configItem:
                fragmentTransaction.replace(R.id.mainContainer, new ConfigurationFragment()).commit();
                showToolbar(getString(R.string.configTab));
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void showToolbar(String title){
        try {
            android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(title);
        } catch (NullPointerException e ){
            Toast.makeText(getApplicationContext(), ("Error " + e), Toast.LENGTH_SHORT).show();
        }
    }
}
