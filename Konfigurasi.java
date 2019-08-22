package com.example.loginandro;


public class Konfigurasi {

	/*API PHP*/
	public static final String URL_GET_SLIP="http://172.16.3.22/android/slipgaji.php?username=";
    public static final String URL_GET_ALL = "http://172.16.3.22/android/allabsen.php?username=";
    public static final String URL_GET_ALL_PG = "http://172.16.3.22/android/pg_allabsen.php?username=";
    public static final String URL_UPDATE_EMP = "http://172.16.3.22/android/changepassword.php?username=";
    public static final String URL_GET_EMP ="http://172.16.3.22/android/getusername.php?username=";
    public static final String URL_GET_LOGIN = "http://172.16.3.22/android/login.php?username=";
    public static final String URL_GET_LOGIN_PG = "http://172.16.3.22/android/pglogin.php?username=";
    
	/*MYSQL DB*/
    public static final String KEY_EMP_ID = "id";
    public static final String KEY_EMP_DTIMS = "dtims";	
    public static final String KEY_EMP_TIPE = "tipe";
    public static final String KEY_EMP_ABSEN = "absen";
    public static final String KEY_EMP_NIK = "nik";
    public static final String KEY_EMP_ROWID = "rowid";
    public static final String KEY_EMP_GAPOK = "gapok";
    public static final String KEY_EMP_KENAIKAN = "kenaikan";
    public static final String KEY_EMP_GAJIBARU = "gajibaru";
    public static final String KEY_EMP_BLNGAJI = "blngaji";
    public static final String KEY_EMP_USERNAME = "username";
    public static final String KEY_EMP_PASSWORD = "password";
    
    /*POSTGRE DB*/
    public static final String KEY_EMP_CHECKTIME = "checktime";	
    public static final String KEY_EMP_STATUS = "status";
    public static final String KEY_EMP_WAREHOUSE = "warehouse";
    
    /*=====================-SCI-=======================*/
    
    /*MYSQL DB*/
    public static final String TAG_JSON_ARRAY="result";
    public static final String TAG_ID = "id";
    public static final String TAG_DTIME = "dtims";
    public static final String TAG_TYPE = "tipe";
    public static final String TAG_MABSEN = "absen";
    public static final String TAG_NIK = "nik";
    public static final String TAG_ROWID = "rowid";
    public static final String TAG_GAPOK = "gapok";
    public static final String TAG_KENAIKAN = "kenaikan";
    public static final String TAG_GAJIBARU = "gajibaru";
    public static final String TAG_BLNGAJI = "blngaji";
    public static final String TAG_USERNAME = "username";
    public static final String TAG_PASSWORD = "password";
    public static final String EMP_ID = "emp_id";
    
    /*POSTGRE DB*/
    public static final String TAG_CHECKTIME = "checktime";
    public static final String TAG_STATUS = "status";
    public static final String TAG_WAREHOUSE = "warehouse";
}