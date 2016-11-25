package lunioussky.secret.atys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import lunioussky.secret.Config;
import lunioussky.secret.R;
import lunioussky.secret.id.Mycontacts;
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

        new UploadContacts(phone_md5, token, Mycontacts.getContactsJSONString(this),
                new UploadContacts.SuccessCallBack() {
            @Override
            public void onSueecss() {
                loadMessage();
            }
        }, new UploadContacts.FailCallBack() {
            @Override
            public void OnFail(int errayCode) {
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
        System.out.println(">>>>>>loadMessage");
    }
    private String phone_num,token,phone_md5;
}
