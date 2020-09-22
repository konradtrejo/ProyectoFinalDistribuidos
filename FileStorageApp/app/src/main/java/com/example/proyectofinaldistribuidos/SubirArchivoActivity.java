package com.example.proyectofinaldistribuidos;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Permission;

public class SubirArchivoActivity extends AppCompatActivity {
    private Button upload_button;
    private Button select_file;
    private ProgressBar progress;

    String file_path = null;
    private static final int REQUEST_FILE_MANAGER =200;
    private static final int Permission_REQUEST_CODE = 1;
    TextView file_name;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subir_archivo);
        upload_button = findViewById(R.id.upload_file);
        select_file =findViewById(R.id.select_file);
        progress = findViewById(R.id.progress);
        file_name=findViewById(R.id.file_name);

        select_file.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT>=23){
                    if(permisosState()){
                        openArchivo();
                    }
                    else{
                        permisos();
                    }
                }
                else {
                    openArchivo();
                }
            }
        });
        upload_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (file_path!=null){
                    upload_Archivo();
                }
                else
                {
                    Toast.makeText(SubirArchivoActivity.this,"Seleccione un archivo",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private  void openArchivo(){
        Toast.makeText(SubirArchivoActivity.this,"File manager hola",Toast.LENGTH_SHORT).show();
        Intent openFileManager = new Intent(Intent.ACTION_PICK);
        openFileManager.setType("image/*");
        startActivityForResult(openFileManager,REQUEST_FILE_MANAGER);
    }
    private void upload_Archivo(){

    }
    private void permisos(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(SubirArchivoActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)){
            Toast.makeText(SubirArchivoActivity.this,"Por favor conceda los permisos para " +
                    "subir los archivos",Toast.LENGTH_SHORT).show();

        }
        else {
            ActivityCompat.requestPermissions(SubirArchivoActivity.this,
                   new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},Permission_REQUEST_CODE
            );
        }
    }
    private boolean permisosState (){
        int value = ContextCompat.checkSelfPermission(SubirArchivoActivity.this
        ,Manifest.permission.READ_EXTERNAL_STORAGE);
        if (value== PackageManager.PERMISSION_GRANTED){
            return  true;
        }
        else{
            return false;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case Permission_REQUEST_CODE:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(SubirArchivoActivity.this, "Permiso concedido", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(SubirArchivoActivity.this, "Permiso denegado", Toast.LENGTH_SHORT).show();
                }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_FILE_MANAGER && resultCode== Activity.RESULT_OK){
            String filePath =getRealPathFromUri(data.getData(),SubirArchivoActivity.this);
            Log.d("FILE PATH: "," "+ filePath);
            this.file_path = filePath;
            File file = new File(filePath);
            file_name.setText(file.getName());

            try {
                byte bytes[] = new byte[(int) file.length()];
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
                DataInputStream dis = new DataInputStream(bis);
                dis.readFully(bytes);
                Log.d("valor",dis.toString());

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    public String getRealPathFromUri(Uri uri, Activity activity){
        Cursor cursor = activity.getContentResolver().query(uri,null,null,null,null);
        if(cursor==null){
            return uri.getPath();
        }
        else{
            cursor.moveToFirst();
            int id =cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(id);
        }
    }
}

