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

public class ReadUserActivity extends AppCompatActivity {

    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_user);

        tv = (TextView)findViewById(R.id.tag);

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
        //Toast.makeText(this, "onIntent()", Toast.LENGTH_SHORT).show();

        Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

        if (rawMsgs != null) {
            NdefMessage msgs = (NdefMessage) rawMsgs[0];
            NdefRecord[] rec = msgs.getRecords();

            byte[] bt = rec[0].getPayload();
            String text = new String(bt);
            tv.setText(text);

            short tnf = rec[0].getTnf();
            String type = new String(rec[0].getType());
            String id = new String(rec[0].getId());

            //Toast.makeText(this, tnf + ":" + type + ":" + id + ":" + text, Toast.LENGTH_LONG).show();
            //Toast.makeText(this, rec[0].toString(), Toast.LENGTH_LONG).show();
            Log.i("NFC테스트", tnf + ":" + type + ":" + id + ":" + text);
            Log.i("NFC테스트", rec[0].toString());
        }
    }

}
