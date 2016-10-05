package yiihs.nfcgame;

import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import yiihs.nfcgame.info.PhoneInformation;
import yiihs.nfcgame.nfc.NfcTagAgent;
import yiihs.nfcgame.nfc.NfcTagChecker;

public class TagReaderActivity extends AppCompatActivity {

    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;

    private PhoneInformation mInformation;

    private ActionBar mActionBar;
    private String nfcText;

    private TextView statusText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_user);

        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setDefaultDisplayHomeAsUpEnabled(true);

        statusText = (TextView)findViewById(R.id.status_text);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        Intent intent = new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        mInformation = new PhoneInformation(getApplicationContext());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return true;
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
            nfcText = new String(bt);
            if(mInformation.getStatus()){
                Toast.makeText(this,getResources().getString(R.string.err_tagged),Toast.LENGTH_SHORT).show();
                statusText.setText(R.string.err_tagged);
            } else {
                NfcTagChecker tagChecker = new NfcTagChecker();
                tagChecker.execute(nfcText);
                if (tagChecker.get("code").equals("200")) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
                    dialog.setTitle("태그 알림")
                            .setMessage("NFC태그가 발견되었습니다. 등록하시겠습니까?")
                            .setPositiveButton("예", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    tagNfc();
                                }
                            })
                            .setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                } else if (tagChecker.get("code").equals("409")) {
                    Toast.makeText(this, getResources().getString(R.string.err_taken), Toast.LENGTH_SHORT).show();
                    statusText.setText(R.string.err_taken);
                } else {
                    Toast.makeText(this, getResources().getString(R.string.err_unknown), Toast.LENGTH_SHORT).show();
                    statusText.setText(R.string.err_unknown);
                }
            }
        }
    }

    public void tagNfc(){
        NfcTagAgent tagAgent = new NfcTagAgent();
        tagAgent.execute(nfcText,mInformation.getPhoneNumber());
        if(tagAgent.get("code").equals("201")){
            Toast.makeText(this, getResources().getString(R.string.tag_success), Toast.LENGTH_SHORT).show();
            statusText.setText(R.string.tag_success);
            mInformation.setStatus(true);
        } else if(tagAgent.get("code").equals("409")){
            Toast.makeText(this,getResources().getString(R.string.err_tagged),Toast.LENGTH_SHORT).show();
            statusText.setText(R.string.err_tagged);
            mInformation.setStatus(true);
        } else {
            Toast.makeText(this,getResources().getString(R.string.err_tag_failed),Toast.LENGTH_SHORT).show();
            statusText.setText(R.string.err_tag_failed);
        }
    }

}
