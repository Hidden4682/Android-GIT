package edu.sox675.androidpractice;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AndroidRegistrarse extends Activity {

	EditText login;
	EditText apellidos;
	EditText edad;
	EditText telefono;
	EditText celular;
	EditText direccion;
	EditText pass;
	EditText confirm_pass;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registrarse);
		traerdatos();
	}

	private void traerdatos() {
		login = (EditText) findViewById(R.id.editText_login);
		apellidos = (EditText) findViewById(R.id.editText_apellidos);
		edad = (EditText) findViewById(R.id.editText_edad);
		telefono = (EditText) findViewById(R.id.editText_telefono);
		celular = (EditText) findViewById(R.id.EditText_celular);
		direccion = (EditText) findViewById(R.id.editText_direccion);
		pass = (EditText) findViewById(R.id.editText_pass);
		confirm_pass = (EditText) findViewById(R.id.editText_confirm);
	}

	public void guardar(View view) {
		AndroidSQLite senaWizard = new AndroidSQLite(getApplicationContext(),
				"AndroidBD", null, 1);

		SQLiteDatabase db = senaWizard.getWritableDatabase();
		ContentValues nuevoRegistro = new ContentValues();

		String loginv = login.getText().toString();
		String apellidosv = apellidos.getText().toString();
		String edadv = edad.getText().toString();
		String telefonov = telefono.getText().toString();
		String celularv = celular.getText().toString();
		String direccionv = direccion.getText().toString();
		String passv = pass.getText().toString();
		String confirm_passv = confirm_pass.getText().toString();
		
		if (passv.equals(confirm_passv)) {
			nuevoRegistro.put("login", loginv);
			nuevoRegistro.put("apellidos", apellidosv);
			nuevoRegistro.put("password", passv);
			nuevoRegistro.put("confirmar_password", confirm_passv);		
			nuevoRegistro.put("edad", edadv);
			nuevoRegistro.put("sexo", 1);
			nuevoRegistro.put("telefono", telefonov);
			nuevoRegistro.put("celular", celularv);
			nuevoRegistro.put("direccion", direccionv);

			db.insert("personas", null, nuevoRegistro);
			Toast.makeText(getApplicationContext(), "Datos Correctos!!!!",
					Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(AndroidRegistrarse.this,
			        AndroidPracticeActivity.class);
			    startActivity(intent);
		}else {
			Toast.makeText(getApplicationContext(), "El password debe ser identico al confirmar password!!!",
					Toast.LENGTH_SHORT).show();
		}
		db.close();
	}

	public void atras(View view) {
		 Intent intent = new Intent(AndroidRegistrarse.this,
			        AndroidPracticeActivity.class);
			    startActivity(intent);
	}

}
