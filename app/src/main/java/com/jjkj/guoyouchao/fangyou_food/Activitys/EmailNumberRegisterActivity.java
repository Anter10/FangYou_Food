package com.jjkj.guoyouchao.fangyou_food.Activitys;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import com.jjkj.guoyouchao.fangyou_food.*;
import com.jjkj.guoyouchao.fangyou_food.Interface.JsonDataHandle;
import com.jjkj.guoyouchao.fangyou_food.TOOLS.HtppUrlPath;
import com.jjkj.guoyouchao.fangyou_food.TOOLS.Param;
import com.loopj.android.http.RequestParams;

import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.*;

import org.json.JSONException;
import org.json.JSONObject;


public class EmailNumberRegisterActivity extends AllActivityBase {

    public Button emailBtn;
    public Button registerBtn;
    public Button sendBtn;
    private EditText emailNumber;
    private EditText emailCode;

    private boolean emailIsVai = false;
    private boolean codeIsVai  = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_number_register);


        emailCode   = (EditText)findViewById(R.id.input_email_code);

        emailCode.addTextChangedListener(new PhoneInputListeners() {
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

        emailNumber = (EditText)findViewById(R.id.emailNumber);
        emailNumber.addTextChangedListener(new PhoneInputListeners() {
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
        emailBtn = (Button)findViewById(R.id.phoneregister);
        emailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneRegister();
            }
        });

        // 注册按钮
        registerBtn = (Button)findViewById(R.id.email_register_btn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                okRegister();
            }
        });

        // 发送手机验证码
        sendBtn = (Button)findViewById(R.id.send_phone_code);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendYzm();
            }
        });

    }


    // 发送验证码
    public void sendYzm(){
           String email               =    emailNumber.getText().toString();
           String dev                 =    Param.Device;
           String url                 =    HtppUrlPath.sendYZMPath;

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
        final String email               =    emailNumber.getText().toString();
        String code                =    emailCode.getText().toString();
        String dev                 =    Param.Device;
        String url                 =    HtppUrlPath.registerUserPath;

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
        String email               =    emailNumber.getText().toString();
        Intent emailLogin = new Intent(this, EmailLoginActivity.class);
        emailLogin.putExtra("email",email);
        emailLogin.setClass(EmailNumberRegisterActivity.this, EmailLoginActivity.class);
        startActivity(emailLogin);

    }


    // 判读验证码是否正确
    public boolean isCodeIsVai(){
        String codeString = emailCode.getText().toString();
        if(codeString.length() == 6){
            codeIsVai = true;
        }else{
            codeIsVai = false;
        }
        return codeIsVai;
    }

    // 判断邮箱是否正确
    public boolean isMail(){
        String email = emailNumber.getText().toString();
        if (email.indexOf("@") > 0){
            emailIsVai = true;
        }else{
            emailIsVai = false;
        }
        return  emailIsVai;
    }
}
