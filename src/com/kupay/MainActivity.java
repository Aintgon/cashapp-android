package com.kupay;





import org.json.JSONException;
import org.json.JSONObject;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.kupay.Post.OnResponseAsync;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager.LayoutParams;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;

public class MainActivity extends Fragment {
	ProgressDialog	progress;
	TabHost mTabHost;
	Button navicon;
	Activity actividad;
	 ActualizarCC cc;
	 Button actCC;
	 //Post monitorCC;
	private boolean camaraCargada = false;
	
	private View mRoot;

	
	



	@Override
	  public View onCreateView(LayoutInflater inflater, ViewGroup container,
	      Bundle savedInstanceState) {
	    mRoot = inflater.inflate(R.layout.activity_main,null);
	   
		actividad = getActivity();
		Log.v("app", "1");
		  mTabHost = (TabHost) mRoot.findViewById (android.R.id.tabhost);
		  mTabHost.setup();
		  navicon = (Button)  mRoot.findViewById(R.id.navicon);
		  actCC = (Button)  mRoot.findViewById(R.id.actCC); 

		
		 cc = new ActualizarCC(actividad);
		 cc.execute();
		 
		    actCC.setOnClickListener(new OnClickListener() {
		    	          public void onClick(View view) { 
		    	        	  Log.v("app", "actualizar cc..");
		    	        	  ActualizarCC cc = new ActualizarCC(actividad);
		    	        	 cc.execute();
		    	        	 
		    	        	 
		    	        	 int duracion=Toast.LENGTH_SHORT;
		    	             Toast mensaje=Toast.makeText(getActivity(), "¡Saldo Actualizado!", duracion);
		    	             mensaje.show();
		    	             mensaje.setGravity(Gravity.TOP|Gravity.RIGHT, 0, 0);
		    	        	 
		    	        	 
		    	          }} );
		  
		// monitorCC = new Post();

		  Eventos();
		 
		 tabs();
	    return mRoot;
	  }

	
	
    public void Eventos(){
   //cambia el estado        
    	
    /*	
    	monitorCC.setOnResponseAsync(new OnResponseAsync() {
			
			@Override
			public void onResponseAsync(JSONObject response) {
				// TODO Auto-generated method stub
				
				try {
			    	//	progress.dismiss();
						 String resultado = response.getString("RESULTADO");
						Log.v("app","RESult: "+ resultado.toString());
						JSONObject datos = response.getJSONObject("DATOS");
			     		if (resultado.equals("ACTUALIZACION_CC_EXITOSA") ){
			     			
			     			
			 				Log.v("app","Datos: "+ datos.toString());
			 				TextView cc = (TextView) getActivity().findViewById(R.id.cantidad);
			 				cc.setText("$"+Double.toString(datos.getDouble("SALDO")));
			 				//AnimaSaldo actcc = new AnimaSaldo(context);
			 				//actcc.equals(datos.getInt("SALDO"));
			 				
			     		}else if(resultado.equals("ACTUALIZACION_CC_FALLIDA")){
			     			Log.v("app", "Actulaizacion fallida");
			     			int duracion=Toast.LENGTH_SHORT;
			                Toast mensaje=Toast.makeText(getActivity(), "error en actualización", duracion);
			                mensaje.show();
			     		}else if(resultado.equals("CONEXION_FALLIDA")){
			     			Log.v("app", "Conexion fallida");
			     			int duracion=Toast.LENGTH_SHORT;
			                Toast mensaje=Toast.makeText(getActivity(), "error en conexion", duracion);
			                mensaje.show();
			     		}else{
			     			Log.v("app", "Error desconosido");
			     			int duracion=Toast.LENGTH_SHORT;
			                 Toast mensaje=Toast.makeText(getActivity(), "error desconosido", duracion);
			                 mensaje.show();
			     		}
			       	} catch (JSONException e) {
							// TODO Auto-generated catch block
							
							}
				
				
				
				
				  JSONObject data = new JSONObject();
				  try {
					data.put("emisor", MiUsuario());
					data.put("imei", MiImei());
					monitorCC.setData(5, data);
					monitorCC.execAsync(getActivity());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
        
    */  
      
      navicon.setOnClickListener(new OnClickListener() {
          public void onClick(View view) { 
        	  Activity act = getActivity();

        	  if(act instanceof MainConteiner) {
        	      ((MainConteiner) act).togle();
        	  }
         	 
          }} );
      
    }
	
    @Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setRetainInstance(true);
		FragmentManager fm = getFragmentManager();
		fm.beginTransaction()
		.replace(R.id.tab_4, new Consulta(), "consultar")
		.replace(R.id.tab_3, new Cobrar(), "cobrar")
		.replace(R.id.tab_1, new capturaQR(), "comprar")
		.replace(R.id.tab_2, new transferencia(), "transferir")
		.commit();
		mTabHost.setCurrentTab(1);
		
		
		
	}
    
    @Override
    public void onPause(){
    	//monitorCC.stopAsync();
    	super.onPause();
    }
    
    @Override
    public void onResume(){
    	JSONObject data = new JSONObject();
		  try {
			data.put("emisor", MiUsuario());
			data.put("imei", MiImei());
			
			//monitorCC.setData(5, data);
			Log.v("app", "2");
			//monitorCC.execAsync(getActivity().getApplicationContext());
			Log.v("app", "3-a");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
		  super.onResume();
    }
  
    @Override
    public void onDestroy() {
    	
        Log.v("app", " MAIN ACTIVITIE onDestroy()");
        //monitorCC.stopAsync();
        capturaQR fragment = (capturaQR) getFragmentManager().findFragmentById(R.id.tab_1);
        if(camaraCargada){
			fragment.stopCamera();
			}
        //cc.cancel(true);
   super.onDestroy();
    }


    
	private void tabs(){
		Log.v("app", "5");
		Bundle b = new Bundle();
		b.putString("key", "comprar");
		mTabHost.addTab(mTabHost.newTabSpec("comprar").setIndicator("", getResources().getDrawable(R.layout.compratab)).setContent(R.id.tab_1));
		mTabHost.getTabWidget().getChildAt(0).setBackgroundResource(R.layout.bgcomp); 
		mTabHost.getTabWidget().getChildAt(0).getLayoutParams().height = LayoutParams.MATCH_PARENT;
		mTabHost.getTabWidget().getChildAt(0).getLayoutParams().width = LayoutParams.MATCH_PARENT;
		mTabHost.getTabWidget().setStripEnabled(false);
		
		//mTabHost.getChildAt(0).getLayoutParams().height = 80;
		
		Log.v("app", "3");
		b = new Bundle();
		b.putString("key", "transferir");
		mTabHost.addTab(mTabHost.newTabSpec("transferir").setIndicator("", getResources().getDrawable(R.layout.transferitab)).setContent(R.id.tab_2));
		mTabHost.getTabWidget().getChildAt(1).setBackgroundResource(R.layout.bgcomp); 
		mTabHost.getTabWidget().getChildAt(1).getLayoutParams().height = LayoutParams.MATCH_PARENT;
		mTabHost.getTabWidget().getChildAt(1).getLayoutParams().width = LayoutParams.MATCH_PARENT;
		mTabHost.getTabWidget().setStripEnabled(false);
		
		//mTabHost.getChildAt(1).getLayoutParams().height = 80;
		Log.v("app", "4");
		
		b = new Bundle();
		b.putString("key", "cobrar");
		mTabHost.addTab(mTabHost.newTabSpec("cobrar").setIndicator("",getResources().getDrawable(R.layout.cobrotab)).setContent(R.id.tab_3));
		mTabHost.getTabWidget().getChildAt(2).setBackgroundResource(R.layout.bgcomp);
		mTabHost.getTabWidget().getChildAt(2).getLayoutParams().height =  LayoutParams.MATCH_PARENT;
		mTabHost.getTabWidget().getChildAt(2).getLayoutParams().width = LayoutParams.MATCH_PARENT;
		mTabHost.getTabWidget().setStripEnabled(false);
		
		
	Log.v("app", "7");
		
		b = new Bundle();
		b.putString("key", "consultar");
		mTabHost.addTab(mTabHost.newTabSpec("consultar").setIndicator("",getResources().getDrawable(R.layout.consultatab)).setContent(R.id.tab_4));
		mTabHost.getTabWidget().getChildAt(3).setBackgroundResource(R.layout.bgcomp);
		mTabHost.getTabWidget().getChildAt(3).getLayoutParams().height =  LayoutParams.MATCH_PARENT;
		mTabHost.getTabWidget().getChildAt(3).getLayoutParams().width = LayoutParams.MATCH_PARENT;
		mTabHost.getTabWidget().setStripEnabled(false);
		
		
		mTabHost.setOnTabChangedListener(new OnTabChangeListener() {
			
			@Override
			public void onTabChanged(String tabId) {
				// TODO Auto-generated method stub
				Log.v("app", tabId);
				
				
				FragmentManager fm = getFragmentManager();
				if (tabId.equals("cobrar")){
					if (fm.findFragmentByTag(tabId) == null) {
				
					fm.beginTransaction()
							.replace(R.id.tab_3, new Cobrar(), tabId)
							.commit();
					
					}
					capturaQR camaraPointer = (capturaQR) getFragmentManager().findFragmentById(R.id.tab_1);
					if(camaraCargada){
						camaraPointer.stopCamera(); 
					}
					
				}else if (tabId.equals("transferir")){
					if (fm.findFragmentByTag(tabId) == null) {
				
					fm.beginTransaction()
							.replace(R.id.tab_2, new transferencia(), tabId)
							.commit();
					
					}
					capturaQR camaraPointer = (capturaQR) getFragmentManager().findFragmentById(R.id.tab_1);
					if(camaraCargada){
						camaraPointer.stopCamera(); 
					}
					
				}else if(tabId.equals("comprar")){
					Log.v("app","CAMBIANDO a comprar");
					if (fm.findFragmentByTag(tabId) == null) {
						Log.v("app", "cargando captura qr fragment");
						Log.v("app", "el fragment ES nulo");
						fm.beginTransaction()
						.replace(R.id.tab_1, new capturaQR(), tabId)
						.commit();
					}
						capturaQR camaraPointer = (capturaQR) getFragmentManager().findFragmentById(R.id.tab_1);
						if(!camaraCargada){
							Log.v("app", "nunca antes se ha cargado la camara");
							Log.v("app", "ENSENDIENDO camara");
							camaraPointer.startPreview();
						} else{
							Log.v("app", "ya antes se ha cargado la camara");
							camaraPointer.restartCam();
						}
						camaraCargada = true;
				}else if(tabId.equals("consultar")) {
					if (fm.findFragmentByTag(tabId) == null) {
						fm.beginTransaction()
						.replace(R.id.tab_4, new Consulta(), tabId)
						.commit();
					}
					capturaQR camaraPointer = (capturaQR) getFragmentManager().findFragmentById(R.id.tab_1);
					if(camaraCargada ){
						Log.v("app", "Apagando camara");
						camaraPointer.stopCamera(); 
					}
					
				}
				
				
				
			}
		});
		
		
		Log.v("app", "5");

	}
	

	
	
	

	
	///////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////

	private String MiUsuario(){
    	String usr = null;
        BDD dbh = new BDD(getActivity(),"kupay",null,1);
        SQLiteDatabase db= dbh.getReadableDatabase();
        Cursor reg = db.query("kupay",new String[]{"usr"},null,null,null,null,null,"1");
        if(reg.moveToFirst()){
            usr=reg.getString(0);
           
        
        }
	 return usr;
    }
    
    private String MiImei(){
    	String imei = null;
        BDD dbh = new BDD(getActivity(),"kupay",null,1);
        SQLiteDatabase db= dbh.getReadableDatabase();
        Cursor reg = db.query("kupay",new String[]{"imei"},null,null,null,null,null,"1");
        if(reg.moveToFirst()){
            imei=reg.getString(0);
           
        
        }
	 return imei;
    }

}
