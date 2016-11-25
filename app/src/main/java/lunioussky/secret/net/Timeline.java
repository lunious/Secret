package lunioussky.secret.net;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import lunioussky.secret.Config;

/**
 * Created by Administrator on 2016/11/25.
 */

public class Timeline {

    public Timeline(String phone_md5,String token,int page,int perpage,final SuccessCallBack
            successCallBack,final FailCallBack failCallBack) {

        new NetConnection(Config.SERVER_URL, HttpMethod.POST, new NetConnection.SuccessCallBack() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);

                    switch (obj.getInt(Config.KEY_STATUS)){
                        case Config.RESULT_STATUS_SUCCESS:
                            if (successCallBack != null){
                                successCallBack.onSuccess(obj.getInt(Config.KEY_PAGE),
                                        obj.getInt(Config.KEY_PERPAGE),obj.getJSONArray(Config.KEY_TIMELINE));
                            }
                            break;
                        default:
                            if (failCallBack != null){
                                failCallBack.onFail();
                            }
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    if (failCallBack != null){
                        failCallBack.onFail();
                    }
                }
            }
        }, new NetConnection.FailCallBack() {
            @Override
            public void onFail() {
                if (failCallBack != null){
                    failCallBack.onFail();
                }
            }
        },Config.KEY_TOKEN,Config.ACTION_TIMELINE,Config.KEY_PHONE_MD5,phone_md5,
                Config.KEY_TOKEN,token,Config.KEY_PAGE,page+"",Config.KEY_PERPAGE,perpage+"");
    }

    public  static interface SuccessCallBack{
        void onSuccess(int page, int perpage, JSONArray timeline);
    }
    public static interface FailCallBack{
        void onFail();
    }
}
