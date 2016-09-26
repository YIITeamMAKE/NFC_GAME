package yiihs.nfcgame;

import android.app.Activity;
import android.widget.Toast;

//TODO: Change class name like OnBackPressListener or OnBackPressHandler
public class OnBackPressHandler {

    private long backKeyPressedTime = 0;
    private Toast toast;

    private Activity activity;

    public OnBackPressHandler(Activity context) {
        this.activity = context;
    }

    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            showGuide();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            activity.finish();
            toast.cancel();
        }
    }

    public void showGuide() {
        toast = Toast.makeText(activity,
                "\'뒤로\'버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
        toast.show();
    }
}