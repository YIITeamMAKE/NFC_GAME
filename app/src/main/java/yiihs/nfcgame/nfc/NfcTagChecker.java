package yiihs.nfcgame.nfc;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import yiihs.nfcgame.tools.JSONFetchBroker;

/**
 * Created by whdgm on 9/27/2016.
 */

public class NfcTagChecker {

    private JSONObject mResult;

    private String message;
    private String code;

    public NfcTagChecker(){
    }

    public void execute(String tagIdValue){
        try {
            mResult = new JSONFetchBroker().execute("http://218.39.83.130/nfc/check.php",tagIdValue).get();
            code = mResult.getString("cod");
            message = mResult.getString("msg");
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        }
    }

    public String get(String name){
        if(name.equals("code")) {
            return code;
        }
        else{
            return message;
        }
    }

}
