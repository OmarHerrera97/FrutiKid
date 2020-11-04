package com.uisrael.fruitkid;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText et_nombre;
    private ImageView iv_personaje;
    private TextView tv_bestScore;
    private MediaPlayer mp;

    //Se genera un numero y aparece una imagen diferente cada vez que se abre la app
    int num_aleatorio = (int) (Math.random() * 10);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_nombre = findViewById(R.id.txt_Nombre);
        iv_personaje = findViewById(R.id.imageView_Personaje);
        tv_bestScore = findViewById(R.id.textView_bestScore);

        //Icono del ActionBar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        //Codigo para generar el aleatorio
        int id;
        if(num_aleatorio == 0 || num_aleatorio == 10){
            id = getResources().getIdentifier("mango", "drawable",getPackageName());
            iv_personaje.setImageResource(id);
        }else   if(num_aleatorio == 1 || num_aleatorio == 9) {
            id = getResources().getIdentifier("fresa", "drawable", getPackageName());
            iv_personaje.setImageResource(id);
        }else   if(num_aleatorio == 2 || num_aleatorio == 8) {
            id = getResources().getIdentifier("manzana", "drawable", getPackageName());
            iv_personaje.setImageResource(id);
        }else if(num_aleatorio == 3 || num_aleatorio == 7) {
            id = getResources().getIdentifier("sandia", "drawable", getPackageName());
            iv_personaje.setImageResource(id);
        }else if(num_aleatorio == 4 || num_aleatorio == 5 || num_aleatorio == 6) {
            id = getResources().getIdentifier("uva", "drawable", getPackageName());
            iv_personaje.setImageResource(id);
        }
    }
}