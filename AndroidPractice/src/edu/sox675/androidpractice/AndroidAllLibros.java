package edu.sox675.androidpractice;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class AndroidAllLibros extends Activity {

	ListView listView;
	EditText prueb;
	ArrayList datos;
	public String libro_id = null;

	private static final int DIALOG_CONFIRM = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.librosall);
		cargarlistview();
		
	}

	private void cargarlistview() {
		super.onStart();
		listView = (ListView) findViewById(R.id.listView1);
		datos = new ArrayList();

		AndroidSQLite senaWizard = new AndroidSQLite(getApplicationContext(),
				"AndroidBD", null, 1);

		SQLiteDatabase db = senaWizard.getWritableDatabase();
		Cursor c = db.rawQuery("select NombreLibro from libros where id not in(select libros_id from prestamos)", null);
		
		if (c.moveToFirst()) {
			do {
				datos.add(c.getString(0));
			} while (c.moveToNext());
		}

		ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, datos);
		listView.setAdapter(adaptador);
		
	}

	@Override
	protected void onStart() {

		cargarlistview();
		listView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				showDialog(DIALOG_CONFIRM);
				libro_id = (String) arg0.getItemAtPosition(arg2);

			}
		});

	}

	protected Dialog onCreateDialog(int id) {
		Dialog dialogo = null;

		switch (id) {
		case DIALOG_CONFIRM:
			dialogo = crearDialogConfirm();
			break;
		default:
			dialogo = null;
			break;
		}
		return dialogo;
	}

	private Dialog crearDialogConfirm() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle("Prestar Libro");
		builder.setMessage("¿Desea Prestar Este Libro?");
		builder.setPositiveButton("Aceptar",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Bundle bundle = getIntent().getExtras();
						String persona_id = bundle.getString("idpersona");
						AndroidSQLite senaWizard = new AndroidSQLite(
								getApplicationContext(), "AndroidBD", null, 1);

						SQLiteDatabase db = senaWizard.getWritableDatabase();
						String[] args = new String[] { persona_id };
						Cursor c = db
								.rawQuery(
										"select count(personas_id) from prestamos where personas_id=?",
										args);
						String cantidad = "";

						if (c.moveToFirst()) {
							do {
								cantidad = c.getString(0);
							} while (c.moveToNext());
						}
						String[] argu = new String[] { libro_id };
						Cursor cursor = db.rawQuery("select id from libros where NombreLibro=?", argu);
						String idv = null;

						if (cursor.moveToFirst()) {
							do {
								idv = cursor.getString(0);
							} while (cursor.moveToNext());
						}

						int cantidadv = Integer.parseInt(cantidad);				
						
		//				Cursor cursorcontao = db.rawQuery("select li.NombreLibro from libros li,prestamos pe where li.id = pe.libros_id and not pe.libros_id=?", argu);

						if (cantidadv >= 3) {
							Toast.makeText(
									getApplicationContext(),
									"TIENE 3 LIBROS PRESTADOS,NO PUEDE PRESTAR MASS!!!!",
									Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(getApplicationContext(),
									"LIBRO PRESTADO CORRECTAMENTE!!!",
									Toast.LENGTH_SHORT).show();
							ContentValues nuevoRegistro = new ContentValues();
							nuevoRegistro.put("personas_id", persona_id);
							nuevoRegistro.put("libros_id", idv);
							db.insert("prestamos", null, nuevoRegistro);
						}
						cargarlistview();
						dialog.cancel();
					}
				});

		builder.setNegativeButton("Cancelar",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});

		return builder.create();
	}

}
