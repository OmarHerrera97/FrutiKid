package com.uisrael.fruitkid;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class activityBestScore extends Activity {

    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splas_screem);
        lv = findViewById(R.id.lista);

        ArrayList<String> ranking = new ArrayList<>();
        AdminSQLiteOpenHelper juego = new AdminSQLiteOpenHelper(this, "BD", null, 1);
        SQLiteDatabase bd = juego.getWritableDatabase();
        Cursor fila = bd.rawQuery("select * from puntaje", null);
        if (fila.moveToFirst()) {
            do {
                ranking.add(fila.getString(0) + " - " + fila.getString(1));
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ranking);
                lv.setAdapter(adapter);
            } while (fila.moveToNext());

            {
            }
        }
    }
}

