package lunioussky.secret.atys;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import org.json.JSONArray;

import lunioussky.secret.Config;
import lunioussky.secret.R;
import lunioussky.secret.id.Mycontacts;
import lunioussky.secret.net.Timeline;
import lunioussky.secret.net.UploadContacts;
import lunioussky.secret.tools.MD5Tool;

/**
 * Created by 11645 on 2016/11/17.
 */

public class AtyTimeLine extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_timeline);

        phone_num = getIntent().getStringExtra(Config.KEY_PHONE_NUM);
        token = getIntent().getStringExtra(Config.KEY_TOKEN);
        phone_md5 = MD5Tool.md5(phone_num);

        final ProgressDialog pd = ProgressDialog.show(this,getResources().getString(R.string.connecting)
                                    ,getResources().getString(R.string.connecting_to_server));
        new UploadContacts(phone_md5, token, Mycontacts.getContactsJSONString(this),
                new UploadContacts.SuccessCallBack() {
            @Override
            public void onSueecss() {
                loadMessage();

                pd.dismiss();
            }
        }, new UploadContacts.FailCallBack() {
            @Override
            public void OnFail(int errayCode) {
                pd.dismiss();
                if (errayCode == Config.RESULT_STATUS_INVALID_TOKEN){
                    startActivity(new Intent(AtyTimeLine.this,AtyLogin.class));
                    finish();
                }else {
                    loadMessage();
                }
            }
        });
    }
    private void loadMessage(){
        final ProgressDialog pd = ProgressDialog.show(this,getResources().getString(R.string.connecting)
                ,getResources().getString(R.string.connecting_to_server));

        new Timeline(phone_md5, token, 1, 20, new Timeline.SuccessCallBack() {
            @Override
            public void onSuccess(int page, int perpage, JSONArray timeline) {
                pd.dismiss();
            }
        }, new Timeline.FailCallBack() {
            @Override
            public void onFail() {
                pd.dismiss();
            }
        });

    }
    private String phone_num,token,phone_md5;
}
