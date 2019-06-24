package com.aide.financial;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.tencent.stat.StatConfig;
import com.tencent.stat.StatService;

import java.util.Properties;

public class MainActivity extends AppCompatActivity {

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_main);

        Button btnLog = findViewById(R.id.btn_log);
        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = FinancialApplication.sPackageName + "\r\n"
                        + FinancialApplication.sVersionName + "\r\n"
                        + FinancialApplication.sChannelId;
                Toast.makeText(mContext, text, Toast.LENGTH_LONG).show();
            }
        });

        Button btnRegister = findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(FinancialApplication.sChannelId == "huawei"){
                    StatService.trackRegAccountEvent( mContext,"18401234567", StatConfig.AccountType.MOBILE);
                }else{
                    StatService.trackRegAccountEvent( mContext,"18826249876", StatConfig.AccountType.MOBILE);
                }
            }
        });

        Button btnPay = findViewById(R.id.btn_pay);
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(FinancialApplication.sChannelId == "huawei"){
                    StatService.trackPayEvent(mContext, "wx", "fn1213955152", 9.9, StatConfig.CurrencyType.CNY);
                }else{
                    StatService.trackPayEvent(mContext, "Alipay", "ml3848956265", 86, StatConfig.CurrencyType.CNY);
                }
            }
        });

        Button btnLogin = findViewById(R.id.btn_custom_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Properties properties = new Properties();
                if(FinancialApplication.sChannelId == "huawei"){
                    properties.setProperty("username", "xhd");
                    properties.setProperty("userpwd", "********");
                }else{
                    properties.setProperty("username", "lmy");
                    properties.setProperty("userpwd", "********");
                }
                StatService.trackCustomKVEvent(mContext, "login", properties);
            }
        });

    }
}
