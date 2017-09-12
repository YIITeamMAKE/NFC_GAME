package yiihs.nfcgame.tools;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

/**
 * Created by whdgm on 9/27/2016.
 */

public class JSONFetchBroker extends AsyncTask<String,Void,JSONObject> {

    private String result;
    private JSONObject jsonResult;

    @Override
    protected JSONObject doInBackground(String... params) {
        Connection mConnection = Jsoup.connect(params[0]).ignoreContentType(true);
        try {
            switch(params.length){
                case 3:
                    mConnection.data("tagUSER",params[2]);
                case 2:
                    mConnection.data("tagID",params[1]);
                    break;
                default:
                    break;
            }
            result = mConnection.execute().body();
            jsonResult = new JSONObject(result);

        } catch (Exception e){
            e.printStackTrace();
        }
        return jsonResult;
    }
}
