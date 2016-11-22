package lunioussky.secret;

import android.app.Activity;
import android.os.Bundle;

import lunioussky.secret.id.Mycontacts;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        System.out.println(Mycontacts.getContactsJSONString(this));

//        String token = Config.getCachedToken(this);
//        if (token != null){
//            Intent i = new Intent(this, AtyTimeLine.class);
//            i.putExtra(Config.KEY_TOKEN,token);
//            startActivity(i);
//        }else{
//            startActivity(new Intent(this, AtyLogin.class));
//        }
//        finish();
    }
}
