package yiihs.nfcgame;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class WriteActivity extends AppCompatActivity {

    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;
    Intent intent;
    EditText et;
    boolean mode = false;
    TextView textNFCState;
    String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        et = (EditText)findViewById(R.id.tag);
        textNFCState = (TextView)findViewById(R.id.textNFCState);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this); //NFC Device 초기화
        intent = new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP); //MainActivity를 기동시킬 때 MainActivity가 TOP에 있거든 그것을 실행시킴
        pendingIntent = PendingIntent.getActivity(this, 0, intent, 0); //PendingIntent 객체로 만듬(지금 당장 수행시키는 것이 아니라 누군가에게 등록해놓고 특정 이벤트 발생시 자동 실행)
    }

    public void btnWrite1st(View v) {
        str = "1";
    }

    public void btnWrite2nd(View v) {
        str = "2";
    }

    public void btnWrite3rd(View v) {
        str = "3";
    }

    public void btnWrite4th(View v) {;
        str = "4";
    }

    @Override
    protected void onNewIntent(Intent intent) { //SINGLE_TOP으로 자기 자신을 자동 호출할 때
        super.onNewIntent(intent);

        Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        str = et.getText().toString();
        NdefMessage message = getNdefMessage(str);
        write(message, tagFromIntent);
    }

    private NdefMessage getNdefMessage(String text) {
        byte[] textBytes = text.getBytes();
        NdefRecord textRecord = new NdefRecord(NdefRecord.TNF_MIME_MEDIA, "text/plain".getBytes(), new byte[] {}, textBytes); //지정한 타입 저장->text/plain
        NdefMessage message = new NdefMessage(textRecord);
        return message;
    }

    private boolean write(NdefMessage message, Tag tagFromIntent) {
        try {
            Ndef ndef = Ndef.get(tagFromIntent);
            Toast.makeText(this, ndef.getType(), Toast.LENGTH_LONG).show();
            if (ndef != null) { //실제로 Write하는 부분
                ndef.connect();
                ndef.writeNdefMessage(message);
                ndef.close();
                //Toast.makeText(this, "태그에 기록했습니다!", Toast.LENGTH_LONG).show();
                textNFCState.setText("NFC State : Tag Write Success");
                return true;
            }
            return false;
        } catch (Exception e) {
            //Toast.makeText(this, "태그에 쓰기 실패했습니다!", Toast.LENGTH_LONG).show();
            textNFCState.setText("NFC State : Tag Write Failed");
            return false;
        }
    }

    public void onResume() {
        super.onResume();
        if (nfcAdapter != null) {
            Toast.makeText(this, "NFC어댑터 사용이 가능합니다!", Toast.LENGTH_LONG).show();
            nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null);
        } else {
            Toast.makeText(this, "NFC어댑터 사용이 불가합니다!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (nfcAdapter != null) {
            nfcAdapter.disableForegroundDispatch(this);
        }
    }
}
