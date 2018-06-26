package com.oude.runtimepermission;

import android.app.*;
import android.os.*;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.view.View;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.content.ContextCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity 
{
    private Button nativeBn,dependBn;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        nativeBn=(Button) findViewById(R.id.mainButton1);
        dependBn=(Button) findViewById(R.id.mainButton2);
        
        //原生方式获取权限第二步：判定权限，通过则调用获取权限后的操作
        nativeBn.setOnClickListener(new OnClickListener(){

                @Override
                public void onClick(View p1)
                {
                    //判断权限是否已获取
                    if(ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                        //未获取则请求权限
                        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CALL_PHONE},1);
                    }
                    else{
                        //已获取直接执行操作
                        call();
                    }
                }
            });
    }

    //原生方式获取权限第一步：获取权限后的操作
    private void call(){
        try{
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:10086"));
        startActivity(intent);
        }catch(SecurityException e){
            e.printStackTrace();
        }
    }

    //原生方式获取权限第三步：权限处理
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        //这里的1即为上面原生获取方式自定义的返回码1，用于区分处理
        switch(requestCode){
            case 1:
                if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    call();
                }else
                {
                    Toast.makeText(MainActivity.this,"电话权限已拒绝",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }
    
}
