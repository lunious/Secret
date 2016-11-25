package lunioussky.secret.net;

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

            }
        }, new NetConnection.FailCallBack() {
            @Override
            public void onFail() {

            }
        },Config.KEY_TOKEN,Config.ACTION_TIMELINE,Config.KEY_PHONE_MD5,phone_md5,
                Config.KEY_TOKEN,token);
    }

    public  static interface SuccessCallBack{
        void onSuccess();
    }
    public static interface FailCallBack{
        void onFail();
    }
}
