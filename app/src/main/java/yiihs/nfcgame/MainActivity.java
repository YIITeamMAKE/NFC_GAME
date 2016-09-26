package yiihs.nfcgame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import yiihs.nfcgame.info.PhoneInformation;

public class MainActivity extends AppCompatActivity {

    private OnBackPressHandler mOnBackPressHandler;
    private boolean isSearched; //찾았는지 확인 여부
    private TextView textDeviceNumber, textIsSearched;

    private PhoneInformation mPhoneInformation;

    private String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mOnBackPressHandler = new OnBackPressHandler(this);
        mPhoneInformation = new PhoneInformation(getApplicationContext());

        // get device phone number
        phoneNumber = mPhoneInformation.getPhoneNumber();
        // get preference
        isSearched = mPhoneInformation.getStatus();

        //TODO: change resource name like text_device_number. use Lower Case and Under-Score for resource name.
        textDeviceNumber = (TextView)findViewById(R.id.textDeviceNumber);
        textDeviceNumber.setText("device number : " +phoneNumber);

        textIsSearched = (TextView)findViewById(R.id.textIsSearched);
        textIsSearched.setText("isSearched : " + isSearched);
    }

    // TODO: change below method names like OnWriteBtnClick
    public void writebt_ok(View v) {
        startActivity(new Intent(getApplication(), WriteActivity.class));
    }

    public void readadminbt_ok(View v) {
        startActivity(new Intent(getApplication(), ReadAdminActivity.class));
    }

    public void rankbt_ok(View v) {
        startActivity(new Intent(getApplication(), RankActivity.class));
    }

    public void readuserbt_ok(View v) {
        if(isSearched == true) {
            Toast.makeText(this, "NFC Tagged Device", Toast.LENGTH_SHORT).show();
        } else {
            startActivity(new Intent(getApplication(), ReadUserActivity.class));
        }
    }

    public void defaultbt_ok(View v) {
        if(isSearched == true) {
            isSearched = false;
            mPhoneInformation.setStatus(isSearched);
            Toast.makeText(this, "Reset Status Success", Toast.LENGTH_SHORT).show();
            textIsSearched.setText("isSearched : " + isSearched);
        } else if(isSearched == false){
            Toast.makeText(this, "isSearched false", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        mOnBackPressHandler.onBackPressed();
    }

}