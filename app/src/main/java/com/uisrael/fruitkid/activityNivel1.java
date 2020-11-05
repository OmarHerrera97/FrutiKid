package com.uisrael.fruitkid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class activityNivel1 extends AppCompatActivity {
    private TextView tvNombre, tvScore;
    private ImageView ivAuno, ivAdos, ivVidas;
    private EditText etRespuesta;
    private MediaPlayer mp, mpGreat, mpBad;

    int score, numAleatorioUno, numAleatorioDos, resultado, vidas = 3;
    String nombreJugador, stringScore, stringVidas;
    String numero [] = {"cero","uno","dos","tres","cuatro","cinco","seis","siete","ocho","nueve"}; //Llama las imagenes


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nivel1);
        Toast.makeText(this, "Nivel 1 - Sumas Básicas", Toast.LENGTH_SHORT).show();
        //crear los objetos y hacer un cast
        tvNombre = findViewById(R.id.textView_nombre);
        tvScore = findViewById(R.id.textView_score);
        ivVidas = findViewById(R.id.imageView_vidas);
        ivAuno = findViewById(R.id.imageView_NumUno);
        ivAdos = findViewById(R.id.imageView_NumDos);
        etRespuesta = findViewById(R.id.editTextResultado);

        nombreJugador = getIntent().getStringExtra("jugador");
        tvNombre.setText("Jugador: "+nombreJugador);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        mp = MediaPlayer.create(this, R.raw.goats);
        mp.start();
        mp.setLooping(true);

        mpGreat = MediaPlayer.create(this,R.raw.wonderful); // Solo lo declaramos
        mpBad = MediaPlayer.create(this,R.raw.bad); // Solo lo declaramos

        numAleatorio();
    }

    public void numAleatorio(){
        if(score <=9){
            numAleatorioUno = (int) (Math.random() * 10);
            numAleatorioDos = (int) (Math.random() * 10);
            resultado = numAleatorioUno + numAleatorioDos;
            if(resultado <= 10 ){
                for(int i = 0; i <numero.length; i++){
                    int id = getResources().getIdentifier(numero[i], "drawable", getPackageName());
                    if(numAleatorioUno == i){
                        ivAuno.setImageResource(id);
                    }if(numAleatorioDos == i){
                        ivAdos.setImageResource(id);
                    }
                }
            }else{
                numAleatorio();
            }
        }else{
            Intent i = new Intent(this, activityNivel2.class);

            stringScore = String.valueOf(score);
            stringVidas = String.valueOf(vidas);
            i.putExtra("jugador",nombreJugador);
            i.putExtra("score",stringScore);
            i.putExtra("vidas",stringVidas);
            startActivity(i);
            finish();
            mp.stop();
            mp.release();

        }
    }
}