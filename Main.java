package com.xuexue.vince.prevent;

import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sp;
    boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sp=getSharedPreferences("usersetting",MODE_PRIVATE);
        if (TextUtils.isEmpty(sp.getString("username",""))){
            showSetPasswordDialog("初次登录请设置用户名和密码");
        }else {
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.password_main, null);
            builder.setView(dialogView);
            builder.setTitle("再次登录，请输入用户名与密码");

            final AlertDialog dialog=builder.create();

            final EditText et_username = (EditText) dialogView.findViewById(R.id.et_username);
            final EditText et_password = (EditText) dialogView.findViewById(R.id.et_password);

            Button bt_ok= (Button) dialogView.findViewById(R.id.bt_ok);
            bt_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("--------->点击了确认按钮");
                    String inputUsername=et_username.getText().toString();
                    String inputPassword=et_password.getText().toString();

                    String safeUsername=sp.getString("username","hahaha");
                    String safePassword=sp.getString("password","hahaha");

                    if (safeUsername.equals(inputUsername) && safePassword.equals(inputPassword)){
                        dialog.dismiss();
                    }else {
                        Toast.makeText(MainActivity.this, "用户名和密码不匹配", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            });

            Button bt_cancel= (Button) dialogView.findViewById(R.id.bt_cancel);
            bt_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("-------->点击取消按钮");
                        finish();
                }
            });
            dialog.show();
        }


        final EditText et_safeNumber= (EditText) findViewById(R.id.et_safenumber);
        final Button bt_ok_main = (Button) findViewById(R.id.bt_ok_main);
        final Button bt_modify_main= (Button) findViewById(R.id.bt_modify_main);

        bt_ok_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag==false){
                    flag=true;
                    String safeNumber=et_safeNumber.getText().toString();
                    if (TextUtils.isEmpty(safeNumber)){
                        Toast.makeText(MainActivity.this, "安全密码不能为空", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    SharedPreferences.Editor editor=sp.edit();
                    editor.putString("safeNumber",safeNumber);
                    editor.putBoolean("safe",flag);
                    editor.commit();


                    et_safeNumber.setEnabled(false);
                    bt_ok_main.setText("停止保护");
                    bt_modify_main.setEnabled(false);
                }else {
                    flag=false;

                    SharedPreferences.Editor editor=sp.edit();
                    editor.putBoolean("safe",flag);
                    editor.commit();

                    et_safeNumber.setEnabled(true);
                    bt_ok_main.setText("开启保护");
                    bt_modify_main.setEnabled(true);
                }
            }
        });

        bt_modify_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSetPasswordDialog("请设置新的用户名与密码");

            }
        });
    }

    private void showSetPasswordDialog(String title) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.password_main, null);
        builder.setView(dialogView);
        builder.setTitle(title);

        final EditText et_username = (EditText) dialogView.findViewById(R.id.et_username);
        final EditText et_password = (EditText) dialogView.findViewById(R.id.et_password);

        final AlertDialog dialog=builder.create();
        Button bt_ok = (Button) dialogView.findViewById(R.id.bt_ok);
        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("-------->点击了确认");
                String username=et_username.getText().toString();
                String password=et_password.getText().toString();

                System.out.println("---------->username: "+username);
                System.out.println("---------->password: "+password);
                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)){
                    Toast.makeText(MainActivity.this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("username",username);
                editor.putString("password",password);
                editor.commit();
                dialog.dismiss();
            }
        });

        Button bt_cancel= (Button) dialogView.findViewById(R.id.bt_cancel);
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("-------->点击取消按钮");
                if (TextUtils.isEmpty(sp.getString("username",""))){
                    finish();
                }else {
                    dialog.dismiss();
                }

            }
        });
        dialog.show();
    }
}
