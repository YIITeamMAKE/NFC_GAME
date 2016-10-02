package yiihs.nfcgame;

import android.app.PendingIntent;
import android.content.Intent;
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
import yiihs.nfcgame.nfc.NfcTagAgent;
import yiihs.nfcgame.nfc.NfcTagChecker;

public class ReadUserActivity extends AppCompatActivity {

    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;

    private PhoneInformation mInformation;

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_user);

        tv = (TextView)findViewById(R.id.tag);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        Intent intent = new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        mInformation = new PhoneInformation(getApplicationContext());
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
        //Toast.makeText(this, "onIntent()", Toast.LENGTH_SHORT).show();

        Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

        if (rawMsgs != null) {
            NdefMessage msgs = (NdefMessage) rawMsgs[0];
            NdefRecord[] rec = msgs.getRecords();

            byte[] bt = rec[0].getPayload();
            String nfcText = new String(bt);
            tv.setText(nfcText);

            short tnf = rec[0].getTnf();
            String type = new String(rec[0].getType());
            String id = new String(rec[0].getId());

            //Toast.makeText(this, tnf + ":" + type + ":" + id + ":" + text, Toast.LENGTH_LONG).show();
            //Toast.makeText(this, rec[0].toString(), Toast.LENGTH_LONG).show();
            Log.i("NFC테스트", tnf + ":" + type + ":" + id + ":" + nfcText);
            Log.i("NFC테스트", rec[0].toString());

            NfcTagChecker tagChecker = new NfcTagChecker();
            tagChecker.execute(nfcText);
            if(tagChecker.get("code").equals("200")){
                NfcTagAgent tagAgent = new NfcTagAgent();
                tagAgent.execute(nfcText,mInformation.getPhoneNumber());
                if(tagAgent.get("code").equals("201")){
                    Toast.makeText(this, "Tag Success", Toast.LENGTH_SHORT).show();
                    mInformation.setStatus(true);
                } else if(tagAgent.get("code").equals("409")){
                    Toast.makeText(this,"This device already tagged other nfc",Toast.LENGTH_SHORT).show();
                    mInformation.setStatus(true);
                } else {
                    Toast.makeText(this,"Tag Failed. Try later",Toast.LENGTH_SHORT).show();
                }
            } else if(tagChecker.get("code").equals("409")){
                Toast.makeText(this,"This Tag already used",Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Unknown Error", Toast.LENGTH_SHORT).show();
            }

        }
    }

}
