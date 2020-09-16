package com.example.proyectofinaldistribuidos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ArchivosActivity extends AppCompatActivity {

    public FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archivos);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.Archivos);
       // accion de botton flotante
        floatingActionButton = findViewById(R.id.floatingActionButton);

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

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ArchivosActivity.this,SubirArchivoActivity.class);
                startActivity(intent);
            }
        });
    }
}