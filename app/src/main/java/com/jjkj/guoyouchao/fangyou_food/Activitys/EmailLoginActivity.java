package com.jjkj.guoyouchao.fangyou_food.Activitys;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jjkj.guoyouchao.fangyou_food.*;
import com.jjkj.guoyouchao.fangyou_food.Interface.JsonDataHandle;
import com.jjkj.guoyouchao.fangyou_food.TOOLS.FileManager;
import com.jjkj.guoyouchao.fangyou_food.TOOLS.HtppUrlPath;
import com.jjkj.guoyouchao.fangyou_food.TOOLS.Param;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class EmailLoginActivity extends AllActivityBase {

    public EditText       emailAddress;
    public EditText       pwdText;
    public Button         emailloginButton;
    public boolean        canlogin;

    private JSONObject loginObj;
    private String        getemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_login);

        emailAddress       =      (EditText)findViewById(R.id.pleaseinputemailaddress);
        emailloginButton   =      (Button)findViewById(R.id.emailloginbtn);
        Intent intent  = getIntent();
        getemail = intent.getStringExtra("email");
        if (getemail != null){
            emailAddress.setText(getemail);
            emailloginButton.setBackgroundColor(Color.GREEN);
        }
        emailAddress.addTextChangedListener(new PhoneInputListeners() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("输入文本 = ",s.toString());
                if (isMail()){
                    emailloginButton.setBackgroundColor(Color.GREEN);
                }else{
                    emailloginButton.setBackgroundColor(Color.LTGRAY);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        pwdText            =      (EditText)findViewById(R.id.pleaseinputpassword);
        pwdText.addTextChangedListener(new PhoneInputListeners() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isMail()){
                    emailloginButton.setBackgroundColor(Color.GREEN);
                }else{
                    emailloginButton.setBackgroundColor(Color.LTGRAY);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        canlogin           =      false;

        emailloginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(canlogin){
                    loginServer();
                }else{
                    cantLogin();
                }
            }
        });

        loginServer();
    }


    // 登陆
    public boolean loginServer(){
        // 输入登陆电话
        String email = emailAddress.getText().toString();
        // 设备名称
        String dev = Param.Device;
        // 网络请求地址
        String url = HtppUrlPath.loginPath;
        String yzm = pwdText.getText().toString();
        RequestParams params1 = new RequestParams();
        loginObj = new JSONObject();
        String userData = "";
       try{
           userData = FileManager.readFileSdcardFile("loginuserdata.json");
           Log.d("读完文件=  ",userData);
       }catch (IOException io){

       }
       try{
//            JSONObject sdjson = new JSONObject(userData);
//            if((sdjson.getString("uid") != null) && (sdjson.getString("pwd") != null)){
//                email = sdjson.getString("uid");
//                yzm = sdjson.getString("pwd");
//                emailAddress.setText(email);
//                pwdText.setText(yzm);
//                emailloginButton.setBackgroundColor(Color.GREEN);
//            }else{
//                canlogin = false;
//            }
            loginObj.put("uid", email);
            loginObj.put("dev", dev);
            loginObj.put("pwd", yzm);
        }catch (JSONException ex){

        }
        if(canlogin == true){
            params1.put("pj",loginObj.toString());
            Log.d("网络请求地址 =",url);
            try{
                FileManager.writeFileSdcardFile("loginuserdata.json",loginObj.toString());
            }catch (IOException ex){

            }
            Param.sendPost(this,url, params1, new JsonDataHandle() {
                @Override
                public void dealJSONData(JSONObject object) {
                    try{
                        loginSuccess();
                    }catch (IOException io){

                    }
                }
            });
        }

        return true;
    }

    // 登陆成功
    public void loginSuccess() throws IOException{

        Intent mainIntent = new Intent(this.getApplicationContext(),Main2Activity.class);
        startActivity(mainIntent);
    }

    public void cantLogin(){
        Toast.makeText(getApplicationContext(),"请输入正确的帐号",Toast.LENGTH_SHORT).show();
    }

    public boolean isMail(){
        String email = emailAddress.getText().toString();
        if (email.indexOf("@") > 0){
            canlogin = true;
        }else{
            canlogin = false;
        }
        return  canlogin;
    }

}
