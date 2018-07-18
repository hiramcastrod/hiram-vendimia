package hiram.vendimia.services;

import android.content.Context;
import android.content.SharedPreferences;

public class LocalStorage {

    public static String getLocalData(Context context, String id){
        SharedPreferences sharedPreferences=context.getSharedPreferences(LocalDictionary.DICTIONARY_FILE, context.MODE_PRIVATE);
        String restoredValue = sharedPreferences.getString(id, null);
        return restoredValue;
    }

    public static void setLocalData(Context context, String id, String value) {
        SharedPreferences.Editor shpEditor = context.getSharedPreferences(LocalDictionary.DICTIONARY_FILE, context.MODE_PRIVATE).edit();
        shpEditor.putString(id,value);
        shpEditor.apply();
    }

}
