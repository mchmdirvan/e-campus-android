package com.example.ecampus_irvan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;

import android.os.Bundle;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class datamahasiswa extends AppCompatActivity {

    String[] daftar;
    ListView ListView01;

    Menu menu;
    protected Cursor cursor;
    DataHelper dbcenter;
    public static datamahasiswa da;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datamahasiswa);

        Button btn=(Button)findViewById(R.id.button2);
        btn.setOnClickListener(arg0 -> {
    // TODO Auto-generated method stub
            Intent inte = new Intent(datamahasiswa.this, inputdata.class);
            startActivity(inte);
        });
        da = this;
        dbcenter = new DataHelper(this);
        RefreshList();
    }

    public void RefreshList(){
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM biodata",null);
        daftar = new String[cursor.getCount()]; cursor.moveToFirst();
        for (int cc=0; cc < cursor.getCount(); cc++){
            cursor.moveToPosition(cc);
            daftar[cc] = cursor.getString(1).toString();
        }
        ListView01 = (ListView)findViewById(R.id.listview1);
        ListView01.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, daftar));
        ListView01.setSelected(true);
        ListView01.setOnItemClickListener((arg0, arg1, arg2, arg3) -> { final String selection = daftar[arg2];
            //.getItemAtPosition(arg2).toString();
            final CharSequence[] dialogitem = {"Lihat Data", "Update Data", "Hapus Data"};

            AlertDialog.Builder builder = new AlertDialog.Builder(datamahasiswa.this);
            builder.setTitle("Pilihan");
            builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                    switch(item){ case 0 : Intent i = new Intent(getApplicationContext(), detaildata.class);

                        i.putExtra("nama", selection);
                        startActivity(i);
                        break; case 1 : Intent in = new Intent(getApplicationContext(), updatedata.class);
                        in.putExtra("nama", selection);
                        startActivity(in); break; case 2 : SQLiteDatabase db1 = dbcenter.getWritableDatabase();
                        db1.execSQL("delete from biodata where nama = '"+selection+"'");
                        RefreshList();
                        break;
                    }
                }
            });
            builder.create().show();
        });
        ((ArrayAdapter)ListView01.getAdapter()).notifyDataSetInvalidated();
    }
}

