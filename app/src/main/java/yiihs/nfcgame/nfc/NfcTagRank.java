package yiihs.nfcgame.nfc;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import yiihs.nfcgame.tools.JSONFetchBroker;

/**
 * Created by whdgm on 9/27/2016.
 */

public class NfcTagRank {

    private JSONObject mResult;

    private ArrayList<Integer> mRank = new ArrayList<>();
    private String code;

    public NfcTagRank(){
    }

    public void execute(){
        try {
            mResult = new JSONFetchBroker().execute("http://218.39.83.130/nfc/rank.php").get();
            mRank.add(0,mResult.getInt("rank1"));
            mRank.add(1,mResult.getInt("rank2"));
            mRank.add(2,mResult.getInt("rank3"));
            mRank.add(3,mResult.getInt("rank4"));
            code = mResult.getString("cod");
//            message = mResult.getString("msg");
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Integer> getRank() {
        return mRank;
    }

    public String get(String name){
        if(name.equals("cod")) {
            return code;
        } else {
            return null;
        }
    }
}
