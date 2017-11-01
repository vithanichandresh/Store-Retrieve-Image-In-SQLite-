package com.example.akshar.imagedatabase;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<ImageDataStoreg> arrayList;
    SQliteHelper sqliteHelepr;
    ImageAdepter imageAdepter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.MyList);
        sqliteHelepr = new SQliteHelper(MainActivity.this);
        arrayList = sqliteHelepr.displayData();
        imageAdepter = new ImageAdepter(MainActivity.this, arrayList);
        listView.setAdapter(imageAdepter);
        registerForContextMenu(listView);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add("Insert");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getTitle().equals("Insert")) {
            startActivity(new Intent(MainActivity.this, InsertImage.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add("Update");
        menu.add("Delete");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final int position = adapterContextMenuInfo.position;
        final ImageDataStoreg studBean = arrayList.get(position);
        if (item.getTitle().equals("Update")) {

            Intent intent = new Intent(MainActivity.this, UpdateImage.class);
            intent.putExtra("id", studBean.getId());
            intent.putExtra("name", studBean.getName());
            intent.putExtra("number", studBean.getNumber());
            startActivity(intent);
            finish();
        } else if (item.getTitle().equals("Delete")) {

            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
            alert.setTitle("Delete?");
            alert.setMessage("Are u sure want to delete?");
            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    sqliteHelepr.deleteData(studBean.getId());
                    arrayList.remove(position);
                    imageAdepter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "Data Delete Success...", Toast.LENGTH_SHORT).show();
                }
            });
            alert.setNegativeButton("No", null);
            alert.show();
        }
        return super.onContextItemSelected(item);
    }
}
