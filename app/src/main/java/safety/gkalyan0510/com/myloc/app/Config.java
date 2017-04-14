package safety.gkalyan0510.com.myloc.app;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Config {
    class UserList{
        ArrayList<String> users;
        UserList(ArrayList<String> users){
            this.users = users;
        }
    }
    // global topic to receive app wide push notifications
    public static final String TOPIC_GLOBAL = "global";

    // broadcast receiver intent filters
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String PUSH_NOTIFICATION = "pushNotification";

    // id to handle the notification in the notification tray
    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;

    public static final String SERVER_KEY = "AAAAmJMN39c:APA91bHmSSJCz2RCWeLWVTd1GdoGqfVWFfkHjqJP37VtbPlBaZB13_2A2BFVJjVkxpddWQCCcpu30A-hxdoyh0SuoQ1XnAgvvMbJ3xrOz6dhAyKYqrWVAmezynDZ8HbysLd4KaHxQHOK";
    public static final String SHARED_PREF = "ah_firebase";

    public static String getUsername(Context context) {
        AccountManager manager = AccountManager.get(context);
        Account[] accounts = manager.getAccountsByType("com.google");
        List<String> possibleEmails = new LinkedList<String>();

        for (Account account : accounts) {
            // TODO: Check possibleEmail against an email regex or treat
            // account.name as an email address only for certain account.type values.
            possibleEmails.add(account.name);
        }

        if (!possibleEmails.isEmpty() && possibleEmails.get(0) != null) {
            String email = possibleEmails.get(0);
            String[] parts = email.split("@");

            if (parts.length > 1)
                return parts[0];
        }
        return null;
    }
    public static ArrayList<String> getUsers(Context context){
        String x = context.getSharedPreferences(SHARED_PREF,0).getString("users",null);
        if(x!=null){
            UserList u = ((UserList)new Gson().fromJson(x, (Class)UserList.class));
            return u.users;
        }else
            return null;
    }
    public static void addUser(Context context,String user){
        String x = context.getSharedPreferences(SHARED_PREF,0).getString("users",null);
        if(x!=null){
            UserList u = ((UserList)new Gson().fromJson(x, (Class)UserList.class));
            u.users.add(user);
            context.getSharedPreferences(SHARED_PREF,0).edit().putString("users",u.toString()).commit();
        }

    }
    public static String getSSID(Context context){
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo;

        wifiInfo = wifiManager.getConnectionInfo();
        if (wifiInfo.getSupplicantState() == SupplicantState.COMPLETED) {
            return wifiInfo.getSSID();
        }
        return null;
    }
    public static String getmac(Context context){
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo;

        wifiInfo = wifiManager.getConnectionInfo();
        if (wifiInfo.getSupplicantState() == SupplicantState.COMPLETED) {

            return wifiInfo.getBSSID();
        }
        return null;
    }

}
