package yiihs.nfcgame;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    //TODO: change object name
    EditText editText3; // 변수 선언

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //TODO: change resource name
        editText3 = (EditText) findViewById(R.id.editTextPass); // ID로 값 찾기
        //verifyPermissions(LoginActivity.this);


        //부팅시 Permission 권한 설정 라이브러리
        //참고 : http://gun0912.tistory.com/55
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                //Toast.makeText(LoginActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                //Toast.makeText(LoginActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
                finish();
            }
        };
        new TedPermission(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("스마트폰의 본인 인증을 위해 전화 권한을 사용합니다.\n\n[설정]-[애플리케이션 관리자]에서 해당 앱의 전화 권한을 활성화 해 주세요.")
                .setPermissions(Manifest.permission.READ_PHONE_STATE)
                .check();
    }

    public void pass_ok(View v) {
        //TODO: change object name
        String str_num_01 = editText3.getText().toString(); // 문자형 값으로 받아옵니다.
        int num_01 = Integer.parseInt(str_num_01); // int로 변형.
        //TODO: type cast not needed, just compare two string using ".equals" method.
        if (num_01 == 1) { // editText3(num_01)이 1234이랑 같는지 판단.
            startActivity(new Intent(getApplication(), MainActivity.class));
            finish();
        } else {
            Toast.makeText(this, "비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
        }
    }
}