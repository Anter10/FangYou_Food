package com.jjkj.guoyouchao.fangyou_food.Activitys;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jjkj.guoyouchao.fangyou_food.*;
import com.jjkj.guoyouchao.fangyou_food.Interface.JsonDataHandle;
import com.jjkj.guoyouchao.fangyou_food.TOOLS.HtppUrlPath;
import com.jjkj.guoyouchao.fangyou_food.TOOLS.Param;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

public class PhoneNumberRegisterActivity extends AllActivityBase {


    private EditText phoneNumber;
    private EditText phoneCode;

    private boolean phoneIsVai = false;
    private boolean codeIsVai  = false;

    public Button emailBtn;
    public Button registerBtn;
    public Button sendBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number_register);


        // 邮箱注册按钮
        emailBtn = (Button)findViewById(R.id.emailregisterbutton);
        registerBtn = (Button)findViewById(R.id.user_phone_register);
        sendBtn = (Button)findViewById(R.id.phone_send_yzm_code);

        phoneCode   = (EditText)findViewById(R.id.phoneyzm);
        phoneNumber = (EditText)findViewById(R.id.phoneNumber);

        emailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailRegister();
            }
        });




        phoneCode.addTextChangedListener(new PhoneInputListeners() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(isMail() && isCodeIsVai()){
                    sendBtn.setBackgroundColor(Color.GREEN);
                    registerBtn.setBackgroundColor(Color.GREEN);
                }else{
                    if(isMail() == false){
                        sendBtn.setBackgroundColor(Color.LTGRAY);
                    }
                    if(isCodeIsVai()  == false){
                        registerBtn.setBackgroundColor(Color.LTGRAY);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        phoneNumber.addTextChangedListener(new PhoneInputListeners() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(isMail()){
                    sendBtn.setBackgroundColor(Color.GREEN);
                }else{
                    sendBtn.setBackgroundColor(Color.LTGRAY);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        // 手机注册按钮

        emailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneRegister();
            }
        });

        // 注册按钮

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                okRegister();
            }
        });

        // 发送手机验证码

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendYzm();
            }
        });

    }

    // 发送验证码
    public void sendYzm(){
        String email               =    phoneNumber.getText().toString();
        String dev                 =    Param.Device;
        String url                 =    HtppUrlPath.sendYZMByPhone;

        RequestParams params1     =   new RequestParams();
        final JSONObject obj      =   new JSONObject();
        try{
            obj.put("uid", email);
            obj.put("dev", dev);
        }catch (JSONException ex){

        }
        params1.put("pj",obj.toString());
        Param.sendPost(this,url, params1, new JsonDataHandle() {
            @Override
            public void dealJSONData(JSONObject object) {
                Log.d("收到的数据 = ",object.toString());
            }
        });

    }

    // 手机注册
    public void phoneRegister(){
        Intent phoneIntent = new Intent(this,PhoneNumberRegisterActivity.class);
        startActivity(phoneIntent);
        this.finish();
    }

    // 确定注册
    public void okRegister(){
        final String email               =    phoneNumber.getText().toString();
        String code                =    phoneCode.getText().toString();
        String dev                 =    Param.Device;
        String url                 =    HtppUrlPath.registerByPhonePath;

        RequestParams params1     =   new RequestParams();
        final JSONObject obj      =   new JSONObject();
        try{
            obj.put("uid", email);
            obj.put("dev", dev);
            obj.put("yzm", code);
        }catch (JSONException ex){

        }

        params1.put("pj",obj.toString());
        Param.sendPost(this,url, params1, new JsonDataHandle() {
            @Override
            public void dealJSONData(JSONObject object) {
                loginSuccess();
            }
        });
    }

    public void loginSuccess(){
        Log.d("收到的数据 = ","Hello  ABC");
        String email               =    phoneNumber.getText().toString();
        Intent emailLogin = new Intent(this, EmailLoginActivity.class);
        emailLogin.putExtra("email",email);
        emailLogin.setClass(PhoneNumberRegisterActivity.this, MainActivity.class);
        startActivity(emailLogin);

    }


    // 判读验证码是否正确
    public boolean isCodeIsVai(){
        String codeString = phoneCode.getText().toString();
        if(codeString.length() == 6){
            codeIsVai = true;
        }else{
            codeIsVai = false;
        }
        return codeIsVai;
    }

    // 判断邮箱是否正确
    public boolean isMail(){
        String email = phoneNumber.getText().toString();
        if (email.length() == 11){
            phoneIsVai = true;
        }else{
            phoneIsVai = false;
        }
        return  phoneIsVai;
    }



    // 手机注册
    public void emailRegister(){
        Intent phoneIntent = new Intent(this,EmailNumberRegisterActivity.class);
        startActivity(phoneIntent);
        this.finish();
    }


}
