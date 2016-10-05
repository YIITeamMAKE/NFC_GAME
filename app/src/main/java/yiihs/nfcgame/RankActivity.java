package yiihs.nfcgame;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.List;

import yiihs.nfcgame.nfc.NfcTagRank;

public class RankActivity extends AppCompatActivity {

    private List<Integer> rankList;
    private NfcTagRank rankTask;

    private ActionBar mActionBar;

    TextView r1,r2,r3,r4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setDefaultDisplayHomeAsUpEnabled(true);

        rankTask = new NfcTagRank();
        rankTask.execute();
        rankList = rankTask.getRank();
        r1=(TextView)findViewById(R.id.rank1_text);
        r2=(TextView)findViewById(R.id.rank2_text);
        r3=(TextView)findViewById(R.id.rank3_text);
        r4=(TextView)findViewById(R.id.rank4_text);
        r1.setText(rankList.get(0).toString());
        r2.setText(rankList.get(1).toString());
        r3.setText(rankList.get(2).toString());
        r4.setText(rankList.get(3).toString());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return true;
    }
}
