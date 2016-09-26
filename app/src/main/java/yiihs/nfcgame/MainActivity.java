package yiihs.nfcgame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private BackPressCloseHandler backPressCloseHandler;
    private SharedPreferences userInfo;
    private SharedPreferences.Editor userInfoEdit;
    boolean isSearched; //찾았는지 확인 여부
    TextView textDeviceNumber, textIsSearched;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        backPressCloseHandler = new BackPressCloseHandler(this);

        //device phonenumber read
        Context context = getApplicationContext();
        TelephonyManager telManager = (TelephonyManager)context.getSystemService(context.TELEPHONY_SERVICE);
        String phoneNum = telManager.getLine1Number();
        String phoneNo = new StringBuilder(new StringBuilder(phoneNum).reverse().substring(0,8)).reverse().toString();
        //Log.e("phoneNum", ""+phoneNum);

        textDeviceNumber = (TextView)findViewById(R.id.textDeviceNumber);
        textDeviceNumber.setText("device number : " +phoneNo);

        textIsSearched = (TextView)findViewById(R.id.textIsSearched);
        textIsSearched.setText("isSearched : " + isSearched);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //sharedpreferences set
        userInfo = getSharedPreferences("userInfo", MODE_PRIVATE);
        userInfoEdit = userInfo.edit();
        isSearched = userInfo.getBoolean("isSearched", false);

        textIsSearched.setText("isSearched : " + isSearched);
    }

    public void writebt_ok(View v) {
            Intent intent_02 = new Intent(getApplication(), WriteActivity.class); // NFC 쓰기 액티비티로 이동
            startActivity(intent_02);}

    public void readadminbt_ok(View v) {
        Intent intent_03 = new Intent(getApplication(), ReadAdminActivity.class); // NFC 읽기 액티비티로 이동
        startActivity(intent_03);}

    public void rankbt_ok(View v) {
        Intent intent_04 = new Intent(getApplication(), RankActivity.class); // 랭킹 액티비티로 이동
        startActivity(intent_04);}

    public void readuserbt_ok(View v) {
        if(isSearched == true) {
            Toast.makeText(this, "NFC Find Device!", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent_05 = new Intent(getApplication(), ReadUserActivity.class);
            startActivity(intent_05);
        }
    }

    public void defaultbt_ok(View v) {
        if(isSearched == true) {
            isSearched = false;
            userInfoEdit.putBoolean("isSearched", false);
            userInfoEdit.commit();
            Toast.makeText(this, "SharedPreferences default set", Toast.LENGTH_SHORT).show();
            textIsSearched.setText("isSearched : " + isSearched);
        } else if(isSearched == false){
            Toast.makeText(this, "isSearched false", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }

}