package com.nikameru.skinjsoneditor;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.core.splashscreen.SplashScreen;
import androidx.preference.PreferenceManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nikameru.skinjsoneditor.databinding.ActivityMainBinding;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

public class HomeActivity extends AppCompatActivity {

    public Intent menuIntent;
    private ActivityMainBinding binding;

    public void newProjectCreation(View view) {
        Intent fabIntent = new Intent(this, ProjectActivity.class);
        fabIntent.putExtra("action", "create");
        startActivity(fabIntent);
    }

    private String readJsonFromFile(String filepath) {
        try {
            String line;

            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath));

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append("\n");
            }

            bufferedReader.close();

            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();

            Toast.makeText(this, "Error! Please grant me an access to the storage!", Toast.LENGTH_LONG).show();

            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen.installSplashScreen(this);

        super.onCreate(savedInstanceState);

        menuIntent = new Intent(this, ProjectActivity.class);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = findViewById(R.id.editFragmentToolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_edit, R.id.navigation_help, R.id.navigation_settings
        ).build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {

            ActivityCompat.requestPermissions(
                    this, new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE }, 200
            );
        }
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.edit_toolbar_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected (@NonNull MenuItem menuItem) {
        int menuItemId = menuItem.getItemId();

        if (menuItemId == R.id.action_create_new) {
            menuIntent.putExtra("action", "create");
            startActivity(menuIntent);

            return true;
        } else if (menuItemId == R.id.action_open_file) {
            Intent fileIntent = new Intent(Intent.ACTION_GET_CONTENT);
            fileIntent.addCategory(Intent.CATEGORY_OPENABLE);
            fileIntent.setType("application/json");

            menuIntent.putExtra("action", "open");

            startActivityForResult(Intent.createChooser(fileIntent, "Choose file"), 100);

            return true;
        } else {
            return super.onOptionsItemSelected(menuItem);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100) {
            assert data != null;

            menuIntent.putExtra("json", readJsonFromFile("/storage/emulated/0/osu!droid/Log/json/skin.json"));
            startActivity(menuIntent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 200) {
            if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(
                        this, "Storage permission denied. I won't be able to save files!", Toast.LENGTH_LONG
                ).show();
            }
        }
    }
}