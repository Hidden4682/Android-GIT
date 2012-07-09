package edu.sox675.androidpractice;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AndroidAddLibro extends Activity {

	EditText nombreArtista;
	EditText Nombrelibro;
	EditText genero;
	EditText editorial;
	EditText hojas;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addlibro);
		traerdatos();
		vaciarcampos();
	}

	private void vaciarcampos() {
		nombreArtista.setText("");
		Nombrelibro.setText("");
		genero.setText("");
		editorial.setText("");
		hojas.setText("");
	}

	private void traerdatos() {

		Nombrelibro = (EditText) findViewById(R.id.editText_libro);
		nombreArtista = (EditText) findViewById(R.id.editText_Nom_Art);
		genero = (EditText) findViewById(R.id.editText_genero);
		editorial = (EditText) findViewById(R.id.editText_editorial);
		hojas = (EditText) findViewById(R.id.editText_hojas);
	}

	public void guardar(View view) {

		Bundle bundle = getIntent().getExtras();
		String persona_id = bundle.getString("idpersona");
		AndroidSQLite senaWizard = new AndroidSQLite(getApplicationContext(),
				"AndroidBD", null, 1);

		SQLiteDatabase db = senaWizard.getWritableDatabase();
		ContentValues nuevoRegistro = new ContentValues();

		String nombreArtistav = nombreArtista.getText().toString();
		String Nombrelibrov = Nombrelibro.getText().toString();
		String edadv = genero.getText().toString();
		String generov = editorial.getText().toString();
		String editorialv = hojas.getText().toString();

		if (nombreArtistav.equalsIgnoreCase("")
				|| Nombrelibrov.equalsIgnoreCase("")
				|| edadv.equalsIgnoreCase("") 
				|| generov.equalsIgnoreCase("")
				|| editorialv.equalsIgnoreCase("")) {
			Toast.makeText(this, "DEBE LLENAR TODOS LOS CAMPOS", Toast.LENGTH_SHORT)
					.show();

		}else {
			nuevoRegistro.put("NombreLibro", Nombrelibrov);
			nuevoRegistro.put("nombreArtista", nombreArtistav);
			nuevoRegistro.put("generoLibro", edadv);
			nuevoRegistro.put("editorial", generov);
			nuevoRegistro.put("numHojas", editorialv);
			nuevoRegistro.put("personas_id", persona_id);

			db.insert("libros", null, nuevoRegistro);
			Toast.makeText(getApplicationContext(), "Datos Correctos!!!!",
					Toast.LENGTH_SHORT).show();
			vaciarcampos();
		}
			

		
	}
}
