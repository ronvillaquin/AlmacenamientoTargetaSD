package com.example.almacenamientotargetasd;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private EditText caja_titulo, caja_descripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        caja_titulo = (EditText)findViewById(R.id.caja_titulo);
        caja_descripcion = (EditText)findViewById(R.id.caja_descripcion);

        // en el archivo manifest le damos los permisos para que pueda tener acceso al almacenamiento sd

    }

    //metodo para el boton guardar
    public void btn_guardar(View vista){
        // se crean variables para poder ecibir y guardar lo que introdujo el usuario
        String c_titulo = caja_titulo.getText().toString();
        String c_descripcion = caja_descripcion.getText().toString();

        // ahora se crea la structura try cash para poder hacer el guardado en la sd
        try {
            // para que puedo guardar el archivo en la targeta sd hay que mostrarle la ruta donde esta la SD
            // con file y una variable guardamos la ruta temporalmente
            //enviroment permite usar el metodo getexternalStorageDirectory
            File targetaSD = Environment.getExternalStorageDirectory(); // esto nos ayuda a recuperar la ruta de la SD
            //mediante un toas mostramos la ruta donde se guardara y en la variable targeta colocamos el metodo
            //.getPath() que recupera lo q esta dentro del file o de la variable targetaSD
            Toast.makeText(this, targetaSD.getPath(), Toast.LENGTH_LONG).show();
            //ahora para guardar se crea objeto file y se pasan dos parametros donde esta la ruta de la targeta y el nombre
            //que introdujo el usario en caja_titulo
            File rutaArchivo = new File(targetaSD.getPath(), c_titulo);
            // ahora para poder escribir o guardar lo que el usuario introdujo lo hacemos mediante el metodo outputstreamwriter
            // pasamos dos variable la de titulo q es la que deseamos que encuentre y la de que el activity sea privado
            OutputStreamWriter crearArchivo = new OutputStreamWriter(openFileOutput(c_titulo, Activity.MODE_PRIVATE));
            crearArchivo.write(c_descripcion); //aqui guardamos lo que el usuario introdujo en la descripcion
            crearArchivo.flush();
            crearArchivo.close();

            // ahora le enviamos un emnsaje que ya se guardo
            //Toast.makeText(this, "Registro Guardado", Toast.LENGTH_LONG).show();
            caja_titulo.setText("");
            caja_descripcion.setText("");

        }catch (IOException e){
            Toast.makeText(this, "No se Pudo Guardar", Toast.LENGTH_LONG).show();
        }
    }

    public void btn_buscar(View vista){
        String c_titulo = caja_titulo.getText().toString();

        //usamos de nuevo try chat siempre que trate de escribir o guardar archivos
        try {
            // hay que indicarle donde es la ubicacion d ela targeta sd donde esta el archvi para que lo puedda buscar
            File targetaSD = Environment.getExternalStorageDirectory();
            //luego que obtuvimos la ruta de se busca el archivo creando otro objeto file
            //mediante dos parametros pasamos la ruta de la targeta sd y el nombre del archivo que deseamos
            File rutaArchivo = new File(targetaSD.getPath(), c_titulo);
            //ahora creamos creamos un objeto imputtstreamreader para poder mostrar lo q esta en el archivo o leer el archivo
            InputStreamReader abrirArchivo = new InputStreamReader(openFileInput(c_titulo));
            // ahora verificamos que el archivo no este vacio para poder mostrarlo leyendo linea por linea para
            // que inicie o pare donde este vacio
            BufferedReader leerArchivo = new BufferedReader(abrirArchivo);
            String linea = leerArchivo.readLine();
            String contenidoCompleto = "";
            while (linea != null ){
                contenidoCompleto = contenidoCompleto + linea + "\n";
                linea = leerArchivo.readLine();
            }

            leerArchivo.close();
            abrirArchivo.close();

            //ahora mostramos lo q recuperamos
            caja_descripcion.setText(contenidoCompleto);

        }catch (IOException e){
            Toast.makeText(this,"Error al leer el Archivo", Toast.LENGTH_LONG).show();
        }
    }
}
