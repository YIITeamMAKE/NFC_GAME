package yiihs.nfcgame;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import yiihs.nfcgame.info.PhoneInformation;

public class AdminTagReadActivity extends AppCompatActivity {

    private PhoneInformation mPhoneInformation;

    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;
    private TextView tagInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_admin);

        mPhoneInformation = new PhoneInformation(getApplicationContext());
        tagInfo = (TextView)findViewById(R.id.tag_info_text);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        Intent intent = new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (nfcAdapter != null) {
            //Toast.makeText(this, "onResume()", Toast.LENGTH_SHORT).show();
            nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null);

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (nfcAdapter != null) {
            //Toast.makeText(this, "onPause()", Toast.LENGTH_SHORT).show();
            nfcAdapter.disableForegroundDispatch(this);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

        if (rawMsgs != null) {
            NdefMessage msgs = (NdefMessage) rawMsgs[0];
            NdefRecord[] rec = msgs.getRecords();

            byte[] bt = rec[0].getPayload();
            String text = new String(bt);
            tagInfo.setText(text);

            mPhoneInformation.setStatus(true);
        }
    }

}
