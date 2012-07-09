package edu.sox675.androidpractice;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AndroidPracticeActivity extends Activity {

	Button login;
	Button registrarse;
	EditText nombre;
	EditText pass;
	String datos1="";
	String datos2="";
	String idv="";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		traerdatos();
	}

	private void traerdatos() {
		registrarse = (Button) findViewById(R.id.button_registrarse);
		login = (Button) findViewById(R.id.button_iniciar);
		nombre = (EditText) findViewById(R.id.editText1);
		pass = (EditText) findViewById(R.id.editText2);
	}

	public void registrarse(View view) {
		Intent intent = new Intent(AndroidPracticeActivity.this,
				AndroidRegistrarse.class);
		startActivity(intent);
	}

	public void iniciarSesion(View view) {
		
		String nombrev = nombre.getText().toString();
		String passv = pass.getText().toString();

		AndroidSQLite senaWizard = new AndroidSQLite(getApplicationContext(),
				"AndroidBD", null, 1);

		SQLiteDatabase db = senaWizard.getWritableDatabase();
		String[] args = new String[] { nombrev };
		Cursor c = db.rawQuery("select login,password,id from personas where login=?", args);

		if (c.moveToFirst()) {
			do {
				
				datos1 = c.getString(0);
				datos2 = c.getString(1);
				idv = c.getString(2);
			} while (c.moveToNext());

		}
		if (nombrev.equalsIgnoreCase("") || passv.equalsIgnoreCase("")){
			Toast.makeText(this, "DEBE LLENAR LOS CAMPOS", Toast.LENGTH_SHORT)
			.show();
		
		}else {
			if (datos1.equals(nombrev) && datos2.equals(passv) ) {
				Toast.makeText(this, "Datos Correctos,Iniciando Sesion...!", Toast.LENGTH_SHORT)
						.show();
				nombre.setText("");
				pass.setText("");
				
				Intent intent = new Intent(AndroidPracticeActivity.this,
						AndroidTabLayoutActivity.class);
				Bundle b = new Bundle();
				b.putString("id", idv);
				intent.putExtras(b);
				startActivity(intent);
				
			} else {
				Toast.makeText(this, "Login o Pass Incorrectos!!", Toast.LENGTH_SHORT)
				.show();
				nombre.setText("");
				pass.setText("");
			}
		}
	}

}