
package com.jjkj.guoyouchao.fangyou_food;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.*;
import android.content.*;
import android.os.*;
import org.json.*;

import com.jjkj.guoyouchao.fangyou_food.Activitys.AllActivityBase;
import com.jjkj.guoyouchao.fangyou_food.Activitys.FgjMainActivity;
import com.jjkj.guoyouchao.fangyou_food.Activitys.FindPasswordActivity;
import com.jjkj.guoyouchao.fangyou_food.Activitys.EmailLoginActivity;
import com.jjkj.guoyouchao.fangyou_food.Activitys.EmailNumberRegisterActivity;
import com.jjkj.guoyouchao.fangyou_food.Activitys.Main2Activity;
import com.jjkj.guoyouchao.fangyou_food.Activitys.PhoneNumberRegisterActivity;
import com.jjkj.guoyouchao.fangyou_food.Interface.JsonDataHandle;
import com.jjkj.guoyouchao.fangyou_food.TOOLS.HtppUrlPath;
import com.jjkj.guoyouchao.fangyou_food.TOOLS.Param;
import com.loopj.android.http.RequestParams;

import android.app.ActionBar;


import java.io.IOException;

public class MainActivity extends AllActivityBase {

    public EditText phoneNumber;
    public Button loginButton;
    public Button moreButton;
    public ActionBar actionbar;
    public EditText pwdText;
    public boolean canlogin = false;
    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_main);
        phoneNumber = (EditText)findViewById(R.id.input_phone_id);
        pwdText = (EditText)findViewById(R.id.password);

        phoneNumber.addTextChangedListener(new PhoneInputListeners() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    Log.d("输入文本 = ",s.toString());
                    if (phoneNumber.getText().length() == 11){
                        loginButton.setBackgroundColor(Color.GREEN);
                       canlogin = true;
                    }else{
                        loginButton.setBackgroundColor(Color.LTGRAY);
                        canlogin = false;
                    }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        loginButton = (Button)findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                canlogin = true;
                if (canlogin == true){
                    try {
                        loginServer();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(MainActivity.this,R.string.error_phone_number,Toast.LENGTH_SHORT).show();
                }
            }
        });
        moreButton = (Button)findViewById(R.id.more_button);
        moreButton.setBackgroundColor(Color.WHITE);
        moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                more();
            }
        });

        if(canLogin()){
            try {
                loginServer();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    // 发送登陆验证码
    public void loginServer() throws  JSONException{
        // 输入登陆电话
        String email             =   phoneNumber.getText().toString();
        // 设备名称
        String dev               =   Param.Device;
        // 网络请求地址
        String url               =   HtppUrlPath.phoneLoginPath;
        String yzm               =   pwdText.getText().toString();
        RequestParams params1    =   new RequestParams();
        final JSONObject obj     =   new JSONObject();
        try{
            obj.put("uid", email);
            obj.put("dev", dev);
            obj.put("pwd", yzm);
        }catch (JSONException ex){

        }
        params1.put("pj",obj.toString());
        Param.sendPost(this,url, params1, new JsonDataHandle() {
            @Override
            public void dealJSONData(JSONObject object) {
                try{
                    loginSuccess();
                }catch (Exception ex){

                }
                Log.d("收到的数据 = ",object.toString());
            }
        });
//        String state = Environment.getExternalStorageState();
//        if (state.equals(Environment.MEDIA_MOUNTED)) {
//            Intent getImageByCamera = new Intent("android.media.action.IMAGE_CAPTURE");
//            startActivityForResult(getImageByCamera,1);
//        }
//        else {
//            Toast.makeText(getApplicationContext(), "请确认已经插入SD卡", Toast.LENGTH_LONG).show();
//        }
    }

    // 登陆成功
    public void loginSuccess() throws IOException {

        Intent mainIntent = new Intent(this.getApplicationContext(),FgjMainActivity.class);
        startActivity(mainIntent);
        this.finish();
    }

    public void more(){
        setProgressBarIndeterminateVisibility(false);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        //    指定下拉列表的显示数据
        final String[] cities = {"邮箱登陆","注册帐号", "忘记密码"};
        //    设置一个下拉的列表选择项
        builder.setItems(cities, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                goWhere(which);
//                Toast.makeText(MainActivity.this, "选择的城市为：" + cities[which], Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }

    public void goWhere(int Index) {
        if (Index == 0) {
            Intent phoneIntent = new Intent(this,EmailLoginActivity.class);
            startActivity(phoneIntent);
        } else if (Index == 1) {
              AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            final String[] cities = {"手机注册","邮箱注册"};

            builder.setItems(cities, new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    registMethod(which);
                }
            });
            builder.show();

//            Intent phoneIntent = new Intent(this,MailRegister.class);
//            startActivity(phoneIntent);
        } else if (Index == 2) {
            Intent phoneIntent = new Intent(this,FindPasswordActivity.class);
            startActivity(phoneIntent);
        } else if (Index == 3) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

//            final String [] findpwdMethods = {"手机找回密码","邮箱找回密码"};
//            builder.setItems(findpwdMethods, new DialogInterface.OnClickListener()
//            {
//                @Override
//                public void onClick(DialogInterface dialog, int which)
//                {
//                    findPwdMethod(which);
////                Toast.makeText(MainActivity.this, "选择的城市为：" + cities[which], Toast.LENGTH_SHORT).show();
//                }
//            });
//
//            builder.show();

        }
    }

    // 注册的时候调用
    public void registMethod(int Index){
        if(Index == 0){
            Intent phoneIntent = new Intent(this,PhoneNumberRegisterActivity.class);
            startActivity(phoneIntent);
        }else if(Index == 1){
            Intent phoneIntent = new Intent(this,EmailNumberRegisterActivity.class);
            startActivity(phoneIntent);
        }
    }

    //
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.main_menu_bar, menu);
//
//
//        return super.onCreateOptionsMenu(menu);
//    }


    public boolean canLogin(){
        boolean yes = false;
        if(pwdText.getText().toString().length() > 0  && phoneNumber.getText().toString().length() == 11){
            yes = true;
        }

        return yes;
    }


}
