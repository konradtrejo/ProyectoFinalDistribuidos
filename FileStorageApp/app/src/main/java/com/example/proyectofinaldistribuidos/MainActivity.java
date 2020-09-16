package com.example.proyectofinaldistribuidos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.Archivos);

        // selecionamos el activity deacuerdo al item
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.Compartidos:
                        startActivity(new Intent(getApplicationContext()
                        ,CompartidosActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.Archivos:
                        return true;

                    case  R.id.Favoritos:
                        startActivity(new Intent( getApplicationContext(), FavoritosActivity.class
                        ));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
}