package lunioussky.secret.net;

import org.json.JSONException;
import org.json.JSONObject;

import lunioussky.secret.Config;

/**
 * Created by Administrator on 2016/11/25.
 */

public class UploadContacts {

    public UploadContacts(String phone_md5,String token,String contacts,
                          final SuccessCallBack successCallBack,final FailCallBack failCallBack) {

        new NetConnection(Config.SERVER_URL, HttpMethod.POST, new NetConnection.SuccessCallBack() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);

                    switch (obj.getInt(Config.KEY_STATUS)){
                        case Config.RESULT_STATUS_SUCCESS:
                            if (successCallBack != null){
                                successCallBack.onSueecss();
                            }
                            break;
                        case Config.RESULT_STATUS_INVALID_TOKEN:
                            if (failCallBack != null){
                                failCallBack.OnFail(Config.RESULT_STATUS_INVALID_TOKEN);
                            }
                            break;
                        default:
                            if (failCallBack != null){
                                failCallBack.OnFail(Config.RESULT_STATUS_FAIL);
                            }
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    if (failCallBack != null){
                        failCallBack.OnFail(Config.RESULT_STATUS_FAIL);
                    }
                }
            }
        }, new NetConnection.FailCallBack() {
            @Override
            public void onFail() {
                if (failCallBack != null){
                    failCallBack.OnFail(Config.RESULT_STATUS_FAIL);
                }

            }
        },Config.KEY_ACTION,Config.ACTION_UPLOAD_CONTACTS,Config.KEY_PHONE_MD5,phone_md5,
                Config.KEY_TOKEN,token,Config.KEY_CONTACTS,contacts);
    }

    public static interface SuccessCallBack{
        void onSueecss();
    }

    public static interface FailCallBack{
        void OnFail(int errayCode);
    }

}
