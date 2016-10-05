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
        toast = Toast.makeText(activity, this.activity.getResources().getString(R.string.exit_toast), Toast.LENGTH_SHORT);
        toast.show();
    }
}