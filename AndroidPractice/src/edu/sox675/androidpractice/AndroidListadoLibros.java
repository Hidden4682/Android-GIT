package edu.sox675.androidpractice;

import java.util.ArrayList;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AndroidListadoLibros extends Activity {

	ListView listView;
	ArrayList datos;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listadolibros);
	}

	@Override
	protected void onStart() {

		super.onStart();
		Bundle bundle = getIntent().getExtras();
		String persona_id = bundle.getString("idpersona");	
		listView = (ListView) findViewById(R.id.listView1);
		datos = new ArrayList();

		AndroidSQLite senaWizard = new AndroidSQLite(getApplicationContext(),
				"AndroidBD", null, 1);

		SQLiteDatabase db = senaWizard.getWritableDatabase();
		String[] args = new String[] { persona_id };
		Cursor c = db.rawQuery(
				"select NombreLibro from libros where personas_id=?", args);

		if (c.moveToFirst()) {
			do {
				datos.add(c.getString(0));
			} while (c.moveToNext());
		}

		ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, datos);
		listView.setAdapter(adaptador);
	}

}
