package com.multilanguage;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import com.multilanguage.utils.MultiLanguageUtil;
import com.multilanguage.utils.SpUtil;
import java.util.Locale;

/**
 * Created by HARRY on 2018/12/13 0013.
 */

public class MultiLanguageApp extends Application {

    private static Context mAppContext;

    public static Context getContext() {
        return mAppContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = getApplicationContext();
        //初始化的时候也初始化语言
        changeLanguage();
    }

    private void changeLanguage() {
        Locale newLocale = MultiLanguageUtil.getAppLocale(this);
        String appLanguage = newLocale.getLanguage();
        String appCountry = newLocale.getCountry();

        String spLanguage = SpUtil.getString(getApplicationContext(), ConstantGlobal.LOCALE_LANGUAGE);
        String spCountry = SpUtil.getString(getApplicationContext(), ConstantGlobal.LOCALE_COUNTRY);

        if (!TextUtils.isEmpty(spLanguage) && !TextUtils.isEmpty(spCountry)) {
            // 如果有一个不同
            if (!MultiLanguageUtil.isSameWithSetting(this)) {
                Locale locale = new Locale(spLanguage, spCountry);
                MultiLanguageUtil.changeAppLanguage(getApplicationContext(), locale, false);
            }
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        //系统语言等设置发生改变时会调用此方法，需要要重置app语言
        super.attachBaseContext(MultiLanguageUtil.attachBaseContext(base));
    }

}
