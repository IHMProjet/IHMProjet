package Viewui;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import java.util.Locale;

/**
 * Created by KathLine on 2017/10/13.
 */

public class LocaleHelper {

    private static final String SELECTED_LANGUAGE = "Locale.Helper.Selected.Language";

    /**
     * 中文
     */
    public static final Locale LOCALE_CHINESE = Locale.CHINESE;
    /**
     * 简体中文
     */
    public static final Locale LOCALE_SIMPLIFIED_CHINESE = Locale.SIMPLIFIED_CHINESE;
    /**
     * 繁体中文
     */
    public static final Locale LOCALE_TRADITIONAL_CHINESE = Locale.TRADITIONAL_CHINESE;
    /**
     * 英文
     */
    public static final Locale LOCALE_ENGLISH = Locale.ENGLISH;

    public static void onAttach(Context context) {
        Locale locale = getUserLocale(context);
        setLocale(context, locale);
    }

    public static boolean setLocale(Context context, Locale locale) {
        saveUserLocale(context, locale);
        return updateResources(context, locale);
    }

    private static Locale getUserLocale(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String localeJson = preferences.getString(SELECTED_LANGUAGE, "");
        return jsonToLocale(localeJson);
    }

    private static void saveUserLocale(Context context, Locale locale) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(SELECTED_LANGUAGE, localeToJson(locale));
        editor.apply();
    }

    private static boolean updateResources(Context context, Locale locale) {

        if (needUpdateLocale(context, locale)) {
            Resources resources = context.getResources();

            Configuration configuration = resources.getConfiguration();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                configuration.setLocale(locale);
            } else {
                configuration.locale = locale;
            }

            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
            return true;
        }else {
            return false;
        }
    }

    /**
     * 获取当前的Locale
     * @param context Context
     * @return Locale
     */
    public static Locale getCurrentLocale(Context context) {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { //7.0有多语言设置获取顶部的语言
            locale = context.getResources().getConfiguration().getLocales().get(0);
        } else {
            locale = context.getResources().getConfiguration().locale;
        }
        return locale;
    }

    /**
     * 判断需不需要更新
     * @param context Context
     * @param locale New User Locale
     * @return true / false
     */
    public static boolean needUpdateLocale(Context context, Locale locale) {
        return locale != null && !getCurrentLocale(context).equals(locale);
    }

    /**
     * Locale转成json
     * @param userLocale UserLocale
     * @return json String
     */
    private static String localeToJson(Locale userLocale) {
        Gson gson = new Gson();
        return gson.toJson(userLocale);
    }

    /**
     * json转成Locale
     * @param localeJson LocaleJson
     * @return Locale
     */
    private static Locale jsonToLocale(String localeJson) {
        Gson gson = new Gson();
        return gson.fromJson(localeJson, Locale.class);
    }
}
