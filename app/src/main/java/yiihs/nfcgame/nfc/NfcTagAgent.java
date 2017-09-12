package yiihs.nfcgame.nfc;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.util.Pair;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.ExecutionException;

import yiihs.nfcgame.tools.JSONFetchBroker;

/**
 * Created by whdgm on 9/27/2016.
 */

public class NfcTagAgent {

    private JSONObject mResult;

    private String code;
    private String message;

    public NfcTagAgent(){
    }

    public void execute(String tagIdValue, String phoneNumber){
        JSONFetchBroker mJSONFetchBroker = new JSONFetchBroker();
        try {
            mResult = mJSONFetchBroker.execute("http://218.39.83.130/nfc/tag.php",tagIdValue,phoneNumber).get();
            code = mResult.getString("cod");
            message = mResult.getString("msg");
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        }
    }

    public String get(String name){
        if(name.equals("code")) {
            return code;
        } else {
            return message;
        }
    }

}
