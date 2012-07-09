package edu.sox675.androidpractice;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class AndroidTabLayoutActivity extends TabActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tablayout);
        
        TabHost tabHost = getTabHost();
        
        TabSpec home = tabHost.newTabSpec("Home");
        home.setIndicator("", getResources().getDrawable(R.drawable.home));
        Intent photosIntent = new Intent(this, AndroidIndex.class);
        home.setContent(photosIntent);
        

        Bundle bundle = getIntent().getExtras();
		String idvariable = bundle.getString("id");        
        
        TabSpec addLibro = tabHost.newTabSpec("Add");
        addLibro.setIndicator("", getResources().getDrawable(R.drawable.book_add));
        Intent songsIntent = new Intent(this, AndroidAddLibro.class);
        Bundle b = new Bundle();
		b.putString("idpersona", idvariable);
		songsIntent.putExtras(b);
		addLibro.setContent(songsIntent);
		
		TabSpec mislibros = tabHost.newTabSpec("Mis Libros");
		mislibros.setIndicator("", getResources().getDrawable(R.drawable.add_book));
        Intent librosIntent = new Intent(this, AndroidListadoLibros.class);
        Bundle mis = new Bundle();
        mis.putString("idpersona", idvariable);
        librosIntent.putExtras(mis);
        mislibros.setContent(librosIntent);
        
        TabSpec LibrosAll = tabHost.newTabSpec("Libreria");
        LibrosAll.setIndicator("", getResources().getDrawable(R.drawable.library));
        Intent LibrosAllIntent = new Intent(this, AndroidAllLibros.class);
        Bundle librosAll = new Bundle();
        librosAll.putString("idpersona", idvariable);
        LibrosAllIntent.putExtras(librosAll);
        LibrosAll.setContent(LibrosAllIntent);
        
        TabSpec AndroidPrestarLibros = tabHost.newTabSpec("Libreria");
        AndroidPrestarLibros.setIndicator("", getResources().getDrawable(R.drawable.devolver));
        Intent AndroidPrestarLibrosIntent = new Intent(this, AndroidPrestarLibros.class);
        Bundle BunAndroidPrestarLibros = new Bundle();
        BunAndroidPrestarLibros.putString("idpersona", idvariable);
        AndroidPrestarLibrosIntent.putExtras(BunAndroidPrestarLibros);
        AndroidPrestarLibros.setContent(AndroidPrestarLibrosIntent);
        
        
        tabHost.addTab(home); 
        tabHost.addTab(addLibro);  
        tabHost.addTab(mislibros); 
        tabHost.addTab(LibrosAll); 
        tabHost.addTab(AndroidPrestarLibros); 
    }
}