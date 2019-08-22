package com.example.loginandro;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.SharedPreferences;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.SearchView;

public class ListView_Pagin extends Activity implements SearchView.OnQueryTextListener {

	private ListView listview;
    private TextView title;
    private String JSON_STRING_MYSQL, JSON_STRING_PG;
    EditText searchView;
    private String username, url, urlpg;
    SharedPreferences sharedpreferences;
    
    
    private ArrayList<HashMap<String, String>> data;
    
    ListAdapter sd;
     
     
    public int TOTAL_LIST_ITEMS = 1030;
    //jumlah data(item) per halaman
    public int NUM_ITEMS_PAGE   = 14;
    private int noOfBtns;
    private Button[] btns;
     
    public void onCreate(Bundle savedInstanceState) {
    	
    super.onCreate(savedInstanceState);
    setContentView(R.layout.listviewpagin);
    listview = (ListView)findViewById(R.id.list);
    
    //Fetching username from shared preferences
    sharedpreferences = getSharedPreferences(Proses_Login.my_shared_preferences, Context.MODE_PRIVATE);
    username = getIntent().getStringExtra(Konfigurasi.TAG_USERNAME);
	
    url = "http://172.16.3.22/android/allabsen.php?username="+username;
    urlpg = "http://172.16.3.22/android/pgabsen.php?nik="+username;
    //url = "http://172.16.3.22/Andro/allabsen.php?username="+username;
    
    
    title    = (TextView)findViewById(R.id.title);
    searchView = (EditText) findViewById(R.id.searchView);
     
    Btnfooter();
    }
    
    private void showEmployee(){
    
    JSONObject jsonObject = null;
    
    data = new ArrayList<HashMap<String, String>>();
    
    try {
        jsonObject = new JSONObject(JSON_STRING_PG);
        JSONArray result = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);
        
        for(int i = 0; i<TOTAL_LIST_ITEMS; i++){
            JSONObject jo = result.getJSONObject(i);
            String checktime = jo.getString(Konfigurasi.TAG_CHECKTIME);
            String status = jo.getString(Konfigurasi.TAG_STATUS);
            String warehouse = jo.getString(Konfigurasi.TAG_WAREHOUSE);
            String nik = jo.getString(Konfigurasi.TAG_NIK);

            HashMap<String,String> employees = new HashMap<String, String>();
            employees.put(Konfigurasi.TAG_CHECKTIME,checktime);
            employees.put(Konfigurasi.TAG_STATUS,status);
            employees.put(Konfigurasi.TAG_WAREHOUSE,warehouse);
            employees.put(Konfigurasi.TAG_NIK,nik);
            data.add(employees);
        }
    }catch (JSONException e) {
        e.printStackTrace();
    }
     
    loadList(0);
     
    CheckBtnBackGroud(0);
    
    try {
        jsonObject = new JSONObject(JSON_STRING_MYSQL);
        JSONArray result = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);
        
        for(int i = 0; i<TOTAL_LIST_ITEMS; i++){
            JSONObject jo = result.getJSONObject(i);
            String dtims = jo.getString(Konfigurasi.TAG_DTIME);
            String tipe = jo.getString(Konfigurasi.TAG_TYPE);
            String absen = jo.getString(Konfigurasi.TAG_MABSEN);
            String username = jo.getString(Konfigurasi.TAG_USERNAME);

            HashMap<String,String> employees = new HashMap<String, String>();
            employees.put(Konfigurasi.TAG_DTIME,dtims);
            employees.put(Konfigurasi.TAG_TYPE,tipe);
            employees.put(Konfigurasi.TAG_MABSEN,absen);
            employees.put(Konfigurasi.TAG_USERNAME,username);
            data.add(employees);
        }

    }catch (JSONException e) {
        e.printStackTrace();
    }
     
    loadList(0);
     
    CheckBtnBackGroud(0);
 
    }
     
    private void Btnfooter()
    {
    		class GetJSON extends AsyncTask<Void,Void,String>{
    			 
                ProgressDialog loading;
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    loading = (ProgressDialog) 
                    ProgressDialog.show(ListView_Pagin.this,"Fetching...","Wait...",false,false);
                }
     
                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    loading.dismiss();
                    String[] respon = s.split("/");
                    JSON_STRING_MYSQL=respon[0];
                    JSON_STRING_PG=respon[1];
                    showEmployee();
                }
                
            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(url);
                String h = rh.sendGetRequest(urlpg);
                return  s +"/"+ h;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    	
        int val = TOTAL_LIST_ITEMS%NUM_ITEMS_PAGE;
        val = val==0?0:1;
        noOfBtns=TOTAL_LIST_ITEMS/NUM_ITEMS_PAGE+val;
         
        LinearLayout ll = (LinearLayout)findViewById(R.id.btnLay);
         
        btns    =new Button[noOfBtns];
         
        for(int i=0;i<noOfBtns;i++)
        {
            btns[i] =   new Button(this);
            btns[i].setBackgroundColor(getResources().getColor(android.R.color.transparent));
            btns[i].setText(""+(i+1));
             
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            ll.addView(btns[i], lp);
             
            final int j = i;
            btns[j].setOnClickListener(new OnClickListener() {
                 
                public void onClick(View v) 
                {
                    loadList(j);
                    CheckBtnBackGroud(j);
                }
            });
        }
         
    }
    /**
     * Method for Checking Button Backgrounds
     */
    @SuppressWarnings("deprecation")
	private void CheckBtnBackGroud(int index)
    {
    	
        title.setText("Page "+(index+1)+" of "+noOfBtns);
        for(int i=0;i<noOfBtns;i++)
        {
            if(i==index)
            {
                btns[index].setBackgroundDrawable(getResources().getDrawable(R.drawable.box_green));
            	//btns[index].setBackgroundColor(getResources().getColor(
				//		android.R.color.darker_gray));
            	btns[i].setTextColor(getResources().getColor(
            			android.R.color.black));
            }
            else
            {	
                btns[i].setBackgroundColor(getResources().getColor(
                		android.R.color.transparent));
                btns[i].setTextColor(getResources().getColor(
                		android.R.color.white));
            }
        }
    }
     
    /**
     * Method for loading data in listview
     * @param number
     */
    private void loadList(int number)
    {
    	ArrayList<HashMap<String,String>> sort = new ArrayList<HashMap<String, String>>();
         
        int start = number * NUM_ITEMS_PAGE;
        for(int i=start;i<(start)+NUM_ITEMS_PAGE;i++)
        {
            if(i<data.size())
            {
                sort.add(data.get(i));
            }
            else
            {
                break;
            }
        }
        sd = new SimpleAdapter(ListView_Pagin.this, sort, R.layout.list_item,
                new String[]{Konfigurasi.TAG_DTIME,Konfigurasi.TAG_TYPE, Konfigurasi.TAG_MABSEN, Konfigurasi.TAG_USERNAME},
                new int[]{R.id.dtims, R.id.tipe, R.id.absen, R.id.nik});
        listview.setAdapter(sd);
        
        searchView.addTextChangedListener(new TextWatcher() {
            
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                ((Filterable) ListView_Pagin.this.sd).getFilter().filter(cs);   
            }
             
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                    int arg3) {
                // TODO Auto-generated method stub
                 
            }
             
            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub                          
            }
        });
        
        sd = new SimpleAdapter(ListView_Pagin.this, sort, R.layout.list_item,
                new String[]{Konfigurasi.TAG_CHECKTIME,Konfigurasi.TAG_STATUS, Konfigurasi.TAG_WAREHOUSE, Konfigurasi.TAG_NIK},
                new int[]{R.id.dtims, R.id.tipe, R.id.absen, R.id.nik});
        listview.setAdapter(sd);
        
        searchView.addTextChangedListener(new TextWatcher() {
            
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                ((Filterable) ListView_Pagin.this.sd).getFilter().filter(cs);   
            }
             
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                    int arg3) {
                // TODO Auto-generated method stub
                 
            }
             
            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub                          
            }
        });
    }

	@Override
	public boolean onQueryTextChange(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	
    	
}