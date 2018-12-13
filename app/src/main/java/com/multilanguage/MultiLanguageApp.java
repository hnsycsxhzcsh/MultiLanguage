package com.multilanguage;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
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
        this.mAppContext = getApplicationContext();
        //初始化本地语言
//        changeLanguage();
        //注册Activity生命周期监听回调，此部分一定加上，因为有些版本不加的话多语言
        registerActivityLifecycleCallbacks(callbacks);
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

    ActivityLifecycleCallbacks callbacks = new ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            String language = SpUtil.getString(getApplicationContext(), ConstantGlobal.LOCALE_LANGUAGE);
            String country = SpUtil.getString(getApplicationContext(), ConstantGlobal.LOCALE_COUNTRY);
            if (!TextUtils.isEmpty(language) && !TextUtils.isEmpty(country)) {
                //强制修改应用语言
                if (!MultiLanguageUtil.isSameWithSetting(activity)) {
                    Locale locale = new Locale(language, country);
                    MultiLanguageUtil.changeAppLanguage(activity, locale, false);
//                    activity.recreate();
                }
            }
        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }
        //Activity 其它生命周期的回调
    };

    @Override
    protected void attachBaseContext(Context base) {
        //系统语言等设置发生改变时会调用此方法，需要要重置app语言
        super.attachBaseContext(MultiLanguageUtil.attachBaseContext(base));
    }

}
