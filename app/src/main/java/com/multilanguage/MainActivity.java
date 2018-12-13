package com.multilanguage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import com.multilanguage.utils.MultiLanguageUtil;
import com.multilanguage.utils.SpUtil;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtZhCn;
    private Button mBtZhTw;
    private Button mBtEn;
    private Button mBtJa;
    private Button mBtSys;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtSys = findViewById(R.id.bt_sys);
        mBtZhCn = findViewById(R.id.bt_zh_cn);
        mBtZhTw = findViewById(R.id.bt_zh_tw);
        mBtEn = findViewById(R.id.bt_en);
        mBtJa = findViewById(R.id.bt_ja);

        mBtSys.setOnClickListener(this);
        mBtZhCn.setOnClickListener(this);
        mBtZhTw.setOnClickListener(this);
        mBtEn.setOnClickListener(this);
        mBtJa.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_sys:
                changeLanguage("", "");
                break;
            case R.id.bt_zh_cn:
                changeLanguage("zh", "CN");
                break;
            case R.id.bt_zh_tw:
                changeLanguage("zh", "TW");

                break;
            case R.id.bt_en:
                changeLanguage("en", "US");

                break;
            case R.id.bt_ja:
                changeLanguage("ja", "JP");

                break;
            default:
                break;
        }
    }

    //修改应用内语言设置
    private void changeLanguage(String language, String area) {
        if (TextUtils.isEmpty(language) && TextUtils.isEmpty(area)) {
            //如果语言和地区都是空，那么跟随系统
            SpUtil.saveString(ConstantGlobal.LOCALE_LANGUAGE, "");
            SpUtil.saveString(ConstantGlobal.LOCALE_COUNTRY, "");
        } else {
            //不为空，那么修改app语言，并true是把语言信息保存到sp中，false是不保存到sp中
            Locale newLocale = new Locale(language, area);
            MultiLanguageUtil.changeAppLanguage(MainActivity.this, newLocale, true);
            MultiLanguageUtil.changeAppLanguage(MultiLanguageApp.getContext(), newLocale, true);
        }
        //重启app,这一步一定要加上，如果不重启app，可能打开新的页面显示的语言会不正确
        Intent intent = new Intent(MultiLanguageApp.getContext(), GuideActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        MultiLanguageApp.getContext().startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
