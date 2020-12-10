package com.uisrael.fruitkid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class activityNivel5 extends AppCompatActivity {

    private TextView tvNombre, tvScore;
    private ImageView ivAuno, ivAdos, ivVidas;
    private EditText etRespuesta;
    private MediaPlayer mp, mpGreat, mpBad;
    int score, numAleatorioUno, numAleatorioDos, resultado, vidas = 3;
    String nombreJugador, stringScore, stringVidas;
    String numero[] = {"cero", "uno", "dos", "tres", "cuatro", "cinco", "seis", "siete", "ocho", "nueve"}; //Llama las imagenes aleatorias

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nivel5);
        Toast.makeText(this, "Nivel 5 - Multiplicaciones", Toast.LENGTH_SHORT).show();
        //crear los objetos y hacer un cast
        tvNombre = findViewById(R.id.textView_nombre);
        tvScore = findViewById(R.id.textView_score);
        ivVidas = findViewById(R.id.imageView_vidas);
        ivAuno = findViewById(R.id.imageView_NumUno);
        ivAdos = findViewById(R.id.imageView_NumDos);
        etRespuesta = findViewById(R.id.editTextResultado);
        //Recupera el nombre del jugador
        nombreJugador = getIntent().getStringExtra("jugador");
        tvNombre.setText("Jugador: " + nombreJugador);
        // Recuperamos el score del nuvel 1
        stringScore = getIntent().getStringExtra("score");
        score = Integer.parseInt(stringScore);
        tvScore.setText("Score: " + score);
        //Recupermos las vidas del nivel 1
        stringVidas = getIntent().getStringExtra("vidas");
        vidas = Integer.parseInt(stringVidas);
        if (vidas == 3) {
            ivVidas.setImageResource(R.drawable.tresvidas);
        }
        if (vidas == 2) {
            ivVidas.setImageResource(R.drawable.dosvidas);
        }
        if (vidas == 1) {
            ivVidas.setImageResource(R.drawable.unavida);
        }

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        mp = MediaPlayer.create(this, R.raw.goats);
        mp.start();
        mp.setLooping(true);

        mpGreat = MediaPlayer.create(this, R.raw.wonderful); // Solo lo declaramos
        mpBad = MediaPlayer.create(this, R.raw.bad); // Solo lo declaramos

        numAleatorio();
    }

    public void comparar(View v) {
        String respuesta = etRespuesta.getText().toString();
        if (!respuesta.equals("")) {
            int respuestaJugador = Integer.parseInt(respuesta);
            if (resultado == respuestaJugador) {
                //Si esta bien la respuesta
                mpGreat.start();
                score++;
                tvScore.setText("Score: " + score);
                etRespuesta.setText("");
                puntajeBase();
            } else {
                //Si esta mal la respuesta
                mpBad.start();
                vidas--;
                puntajeBase();
                switch (vidas) {
                    case 3:
                        //Manzanas----
                        ivVidas.setImageResource(R.drawable.tresvidas);
                        break;
                    case 2:
                        Toast.makeText(this, "Te quedan 2 Manzanas", Toast.LENGTH_SHORT).show();
                        ivVidas.setImageResource(R.drawable.dosvidas);
                        break;
                    case 1:
                        Toast.makeText(this, "Te queda 1 Manzana", Toast.LENGTH_SHORT).show();
                        ivVidas.setImageResource(R.drawable.unavida);
                        break;
                    case 0:
                        Toast.makeText(this, "Has perdido todas tus manzanas", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(this, MainActivity.class);
                        startActivity(i);
                        finish();
                        mp.stop();
                        mp.release();
                        break;
                }
                etRespuesta.setText("");
            }
            numAleatorio();
        } else {
            Toast.makeText(this, "Debes escribir tu respuesta..", Toast.LENGTH_SHORT).show();
        }
    }

    public void numAleatorio() {
        if (score <= 49) {
            numAleatorioUno = (int) (Math.random() * 10);
            numAleatorioDos = (int) (Math.random() * 10);
            resultado = numAleatorioUno * numAleatorioDos;

            for (int i = 0; i < numero.length; i++) {
                int id = getResources().getIdentifier(numero[i], "drawable", getPackageName());
                if (numAleatorioUno == i) {
                    ivAuno.setImageResource(id);
                }
                if (numAleatorioDos == i) {
                    ivAdos.setImageResource(id);
                }
            }
        } else {
            Intent i = new Intent(this, activityNivel6.class);
            stringScore = String.valueOf(score);
            stringVidas = String.valueOf(vidas);
            i.putExtra("jugador", nombreJugador);
            i.putExtra("score", stringScore);
            i.putExtra("vidas", stringVidas);
            startActivity(i);
            finish();
            mp.stop();
            mp.release();
        }
    }

    public void puntajeBase() {
        //Apertura y escritura de la base de datos
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "BD", null, 1);
        SQLiteDatabase BD = admin.getWritableDatabase();
        Cursor query = BD.rawQuery("select * from puntaje where score = (select max (score) from puntaje)", null);
        if (query.moveToFirst()) { //hay respuesta de la base
            String tempNombre = query.getString(0); //Columna 0 - 1
            String tempScore = query.getString(1);
            int bestScore = Integer.parseInt(tempScore);
            if (score > bestScore) {
                ContentValues modifi = new ContentValues();
                modifi.put("nombre", nombreJugador);
                modifi.put("score", score);
                BD.update("puntaje", modifi, "score=" + bestScore, null);
            }
            BD.close();
        } else {
            ContentValues insert = new ContentValues();
            insert.put("nombre", nombreJugador);
            insert.put("score", score);
            BD.insert("puntaje", null, insert);
            BD.close();
        }
    }

    @Override
    public void onBackPressed() {

    }
}