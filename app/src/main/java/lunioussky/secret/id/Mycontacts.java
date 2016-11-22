package lunioussky.secret.id;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import lunioussky.secret.Config;
import lunioussky.secret.tools.MD5Tool;

/**
 * Created by 11645 on 2016/11/17.
 */

public class Mycontacts {
    public  static String getContactsJSONString(Context context){
        Cursor c = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,null,null,null);
        String phoneNum;
        JSONArray jsonArr = new JSONArray();
        JSONObject jsonObj;
        while (c.moveToNext()){
            phoneNum = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            if (phoneNum.charAt(0) == '+' && phoneNum.charAt(1) == '8' && phoneNum.charAt(2) == '6'){
                phoneNum = phoneNum.substring(3);
            }
            jsonObj = new JSONObject();
            try {
                jsonObj.put(Config.KEY_PHONE_MD5, MD5Tool.md5(phoneNum));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsonArr.put(jsonObj);

//            Log.d("TAG","PhoneNum:"+phoneNum);
        }
        return jsonArr.toString();
    }
}
