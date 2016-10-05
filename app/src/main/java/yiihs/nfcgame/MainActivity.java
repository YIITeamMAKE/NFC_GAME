package yiihs.nfcgame;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;

import yiihs.nfcgame.info.PhoneInformation;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private OnBackPressHandler mOnBackPressHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set Button Onclick Listener
        Button help;
        FloatingActionButton tag,rank;
        tag=(FloatingActionButton)findViewById(R.id.fab_tag);
        tag.setOnClickListener(this);
        rank=(FloatingActionButton)findViewById(R.id.fab_rank);
        rank.setOnClickListener(this);
        help=(Button)findViewById(R.id.btn_help);
        help.setOnClickListener(this);
        /*
        adminRead = (Button)findViewById(R.id.admin_read);
        adminWrite = (Button)findViewById(R.id.admin_write);
        userRank = (Button)findViewById(R.id.user_rank);
        userRead = (Button)findViewById(R.id.user_read);
        adminRead.setOnClickListener(this);
        adminWrite.setOnClickListener(this);
        userRank.setOnClickListener(this);
        userRead.setOnClickListener(this);
        */

        mOnBackPressHandler = new OnBackPressHandler(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.admin_write:
                startActivity(new Intent(this, AdminTagWriteActivity.class));
                break;
            case R.id.admin_read:
                startActivity(new Intent(this, AdminTagReadActivity.class));
                break;
            case R.id.fab_tag:
                startActivity(new Intent(this, TagReaderActivity.class));
                break;
            case R.id.fab_rank:
                startActivity(new Intent(this, RankActivity.class));
                break;
            case R.id.btn_help:
                startActivity(new Intent(this, HelpActivity.class));
        }
    }

    @Override
    public void onBackPressed() {
        mOnBackPressHandler.onBackPressed();
    }
}