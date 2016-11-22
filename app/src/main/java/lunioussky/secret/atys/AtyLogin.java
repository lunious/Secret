package lunioussky.secret.atys;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import lunioussky.secret.Config;
import lunioussky.secret.R;
import lunioussky.secret.net.GetCode;
import lunioussky.secret.net.Login;
import lunioussky.secret.tools.MD5Tool;

/**
 * Created by 11645 on 2016/11/17.
 */

public class AtyLogin extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_login);

        etPhone = (EditText) findViewById(R.id.etPhoneNum);
        etCode = (EditText) findViewById(R.id.etCode);

        findViewById(R.id.btnGetCode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etPhone.getText())){
                    Toast.makeText(AtyLogin.this,R.string.phone_num_can_not_be_empty,
                            Toast.LENGTH_LONG).show();
                    return;
                }

                final ProgressDialog pd = ProgressDialog.show(AtyLogin.this,getResources().
                        getString(R.string.connecting),getResources().
                        getString(R.string.connecting_to_server));
                new GetCode(etPhone.getText().toString(), new GetCode.SuccessCallBack() {
                    @Override
                    public void onSuccess() {
                        pd.dismiss();
                        Toast.makeText(AtyLogin.this,R.string.suc_to_get_code,
                                Toast.LENGTH_LONG).show();
                    }
                }, new GetCode.FailCallBack() {
                    @Override
                    public void onFail() {
                        pd.dismiss();
                        Toast.makeText(AtyLogin.this,R.string.fail_to_get_code,
                                Toast.LENGTH_LONG).show();
                    }
                });

            }
        });
        findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etPhone.getText())){
                    Toast.makeText(AtyLogin.this,R.string.phone_num_can_not_be_empty,
                            Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(etCode.getText())){
                    Toast.makeText(AtyLogin.this,R.string.code_can_not_be_empty,Toast.LENGTH_LONG)
                            .show();
                    return;
                }

                new Login(MD5Tool.md5(etPhone.getText().toString()), etCode.getText().toString(),
                        new Login.SuccessCallBack() {
                    @Override
                    public void onSuccess(String token) {

                        Config.cacheToken(AtyLogin.this,token);
                        Config.cachePhoneNum(AtyLogin.this,etPhone.getText().toString());

                        Intent i = new Intent(AtyLogin.this,AtyTimeLine.class);
                        i.putExtra(Config.KEY_TOKEN,token);
                        i.putExtra(Config.KEY_PHONE_NUM,etPhone.getText().toString());
                        startActivity(i);

                        finish();
                    }
                }, new Login.FailCallBack() {
                    @Override
                    public void onFail() {
                        Toast.makeText(AtyLogin.this,R.string.fail_to_login,Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }
        });
    }
    private EditText etPhone = null,etCode;
}
