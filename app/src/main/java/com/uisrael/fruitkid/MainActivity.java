package com.uisrael.fruitkid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText et_nombre;
    private ImageView iv_personaje, imJugar,imInstru;
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
        imJugar = findViewById(R.id.imScore);
        imInstru = findViewById(R.id.imInstru);

        //Icono del ActionBar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        //Codigo para generar el aleatorio
        int id;
        if (num_aleatorio == 0 || num_aleatorio == 10) {
            id = getResources().getIdentifier("mango", "drawable", getPackageName());
            iv_personaje.setImageResource(id);
        } else if (num_aleatorio == 1 || num_aleatorio == 9) {
            id = getResources().getIdentifier("fresa", "drawable", getPackageName());
            iv_personaje.setImageResource(id);
        } else if (num_aleatorio == 2 || num_aleatorio == 8) {
            id = getResources().getIdentifier("manzana", "drawable", getPackageName());
            iv_personaje.setImageResource(id);
        } else if (num_aleatorio == 3 || num_aleatorio == 7) {
            id = getResources().getIdentifier("sandia", "drawable", getPackageName());
            iv_personaje.setImageResource(id);
        } else if (num_aleatorio == 4 || num_aleatorio == 5 || num_aleatorio == 6) {
            id = getResources().getIdentifier("uva", "drawable", getPackageName());
            iv_personaje.setImageResource(id);
        }

        //Crear la conexion a SQLite
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "BD", null, 1);
        SQLiteDatabase BD = admin.getWritableDatabase(); //Modo lectura BD

        Cursor consulta = BD.rawQuery(
                "select * from puntaje where score = (select max(score) from puntaje)", null);
        if (consulta.moveToFirst()) {
            String tempNombre = consulta.getString(0);
            String tempScore = consulta.getString(1);
            tv_bestScore.setText("Record: " + tempScore + " de " + tempNombre);
            BD.close();
        } else {
            BD.close();
        }

        //Musica de fondo
        mp = MediaPlayer.create(this, R.raw.alphabet_song);
        mp.start();
        mp.setLooping(true);
    }

    public void jugar(View v) {
        String nombre = et_nombre.getText().toString();
        if (!nombre.equals("")) {
            mp.stop();
            mp.release(); //Libera los recursos
            Intent i = new Intent(this, activityNivel1.class);
            i.putExtra("jugador", nombre);
            startActivity(i);
            finish();
        } else {
            Toast.makeText(this, "Debes escribir tu nombre ", Toast.LENGTH_SHORT).show();
            //Se abre el teclado y escribe en el editText
            et_nombre.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
            imm.showSoftInput(et_nombre, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    public void howToPlay(View v){
        Intent i = new Intent(this, activityBestScore.class);
        startActivity(i);
    }

    public void urlIns(View v){
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://drive.google.com/file/d/1QRnQK6UtkEmaCZ0NEhYHQN5eFtvp_y1y/view?usp=sharing"));
        startActivity(i);

    }

    @Override
    public void onBackPressed() {
    }
}