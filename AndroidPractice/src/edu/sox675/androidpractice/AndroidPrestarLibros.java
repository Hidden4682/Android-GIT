package edu.sox675.androidpractice;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class AndroidPrestarLibros extends Activity {
	
	ListView listView;
	ArrayList datos;
	public String libro_id = null;
	private static final int DIALOG_CONFIRM = 1;
	static String[] Usuariocampos = new String[] { "id", "personas_id","libros_id"};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.prestarlibros);
		cargarlistView();
	}

	private void cargarlistView() {
		listView = (ListView) findViewById(R.id.listView1);
		datos = new ArrayList();

		AndroidSQLite senaWizard = new AndroidSQLite(getApplicationContext(),
				"AndroidBD", null, 1);

		SQLiteDatabase db = senaWizard.getWritableDatabase();
		Bundle bundle = getIntent().getExtras();
		String persona_id = bundle.getString("idpersona");
		String[] args = new String[] { persona_id };
		Cursor c = db.rawQuery("select li.NombreLibro from libros li,prestamos pe where li.id = pe.libros_id and pe.personas_id=?", args);

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

		super.onStart();
		cargarlistView();
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

		builder.setTitle("Devolver Libro");
		builder.setMessage("¿Desea Devolver Este Libro?");
		builder.setPositiveButton("Aceptar",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Bundle bundle = getIntent().getExtras();
						String persona_id = bundle.getString("idpersona");
						String libro_idv = libro_id.toString();
						AndroidSQLite senaWizard = new AndroidSQLite(
								getApplicationContext(), "AndroidBD", null, 1);
						SQLiteDatabase db = senaWizard.getWritableDatabase();
						
						
						String[] args = new String[] { libro_idv };
						Cursor cursor = db.rawQuery("select id from libros where NombreLibro=?", args);
						String idv = null;

						if (cursor.moveToFirst()) {
							do {
								idv = cursor.getString(0);
							} while (cursor.moveToNext());
						}
						
						
						String[] argsWhere = new String[] { persona_id, idv };
						
						Cursor c = db.query("prestamos", Usuariocampos,
								"personas_id=? and libros_id=?", argsWhere, null, null, null);
						String cantidad = "";

						if (c.moveToFirst()) {
							do {
								cantidad = c.getString(0);
							} while (c.moveToNext());
						}

						String[] argu = new String[] { cantidad };
						db.delete("prestamos", "id=?", argu);
						db.close();
						cargarlistView();
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
