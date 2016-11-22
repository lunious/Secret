package lunioussky.secret.net;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import lunioussky.secret.Config;

/**
 * Created by 11645 on 2016/11/17.
 */

public class NetConnection {

    public NetConnection(final String url, final HttpMethod method, final SuccessCallBack successCallBack,
                         final FailCallBack failCallBack, final String...kvs) {

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                StringBuffer paramsStr = new StringBuffer();
                for (int i = 0; i <kvs.length; i+=2) {
                    paramsStr.append(kvs[i]).append("=").append(kvs[i+1]).append("&");
                }

                try {
                    URLConnection uc;
                    switch (method){
                        case POST:
                            uc =new URL(url).openConnection();
                            uc.setDoOutput(true);
                            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter
                                    (uc.getOutputStream(), Config.CHARSET));
                            bw.write(paramsStr.toString());
                            bw.flush();
                            break;
                        default:
                            uc = new URL(url+"?"+paramsStr.toString()).openConnection();
                            break;
                    }
                    System.out.print("Request url:"+uc.getURL());
                    System.out.print("Request data:"+paramsStr);

                    BufferedReader br =  new BufferedReader(new InputStreamReader(
                            uc.getInputStream(),Config.CHARSET));
                    String line = null;
                    StringBuffer result = new StringBuffer();
                    while ((line=br.readLine())!=null){
                        result.append(line);

                    }
                    System.out.print("Result:"+result);
                    return result.toString();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                if (result!=null){
                    if (successCallBack!=null){
                        successCallBack.onSuccess(result);
                    }else{
                        if (failCallBack!=null){
                            failCallBack.onFail();
                        }
                    }
                }
                super.onPostExecute(result);
            }
        }.execute();

    }

    public static interface SuccessCallBack{
        void onSuccess(String result);
    }
    public  static interface FailCallBack{
        void onFail();
    }
}
