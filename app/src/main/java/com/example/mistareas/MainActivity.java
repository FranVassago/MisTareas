package com.example.mistareas;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mistareas.db.ControladorDB;

public class MainActivity extends AppCompatActivity {


    ControladorDB controladorDB;
    private ArrayAdapter<String> myAdapter;
    ListView listViewTareas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        controladorDB  = new ControladorDB(this);
        listViewTareas = (ListView) findViewById(R.id.listaTareas);
        actualizarUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        final EditText cajaTexto = new EditText(this);

        AlertDialog dialog = new AlertDialog.Builder(this)
        .setTitle("Nueva Tarea")
        .setMessage("¿Qúe vas a dejar para luego?")
        .setView(cajaTexto)
        .setPositiveButton("Añadir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String tarea = cajaTexto.getText().toString();
                controladorDB.addTarea(tarea);
                actualizarUI();
            }
        })
        .setNegativeButton("Cancelar", null)
        .create();

        dialog.show();
        return super.onOptionsItemSelected(item);
    }
    private void actualizarUI(){
        if(controladorDB.numeroRegistros()==0){
            listViewTareas.setAdapter(null);
        }else {
            myAdapter = new ArrayAdapter<>(this, R.layout.item_tarea, R.id.task_title, controladorDB.obtenerTareas());
            listViewTareas.setAdapter(myAdapter);
        }

    }

    public void borrarTarea(View view){
        View parent = (View) view.getParent();
        TextView tareaTextView = (TextView) parent.findViewById(R.id.task_title);
        String tarea  = tareaTextView.getText().toString();
        controladorDB.borrarTarea(tarea);
        actualizarUI();

    }
}
