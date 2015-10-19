package net.datafans.android.common.helper.face;

import android.content.Context;
import android.util.Log;

import net.datafans.android.common.helper.PListParser;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhonganyun on 15/9/15.
 */
public class FaceHelper {


    private static Map<String, String> reverseMap = new HashMap<>();
    private static Map<String, String> plistMap;

    public static String getEmojiText(Context context, int index) {

        Map<String, String> map = getPlistMap(context);
        Set<String> set = map.keySet();
        for (String key : set) {
            String value = map.get(key);
            reverseMap.put(value, key);
        }
        return reverseMap.get("expression_" + index);
    }


    public static String getResNameByTag(Context context, String tag) {
        Map<String, String> map = getPlistMap(context);
        return map.get(tag);
    }


    private static String regex = "\\[[^\\]]+\\]";
    private static Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);


    public static String replace(Context context, String source) {
        try {
            return dealExpression(context, source, pattern, 0);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Android-Common", e.toString());
        }

        return null;
    }


    private static String dealExpression(Context context,
                                         String source, Pattern patten, int start)
            throws Exception {
        Matcher matcher = patten.matcher(source);
        while (matcher.find()) {
            String key = matcher.group();
            if (matcher.start() < start) {
                continue;
            }

            String resName = FaceHelper.getResNameByTag(context, key);
            if (resName != null) {
                int end = matcher.start() + key.length();
                source = source.substring(0, matcher.start()) + "<img src='" + resName + "'>" + source.substring(end, source.length());
                if (end < source.length()) {
                    source = dealExpression(context, source, patten, end);
                }
                break;
            }

        }

        return source;
    }

    @SuppressWarnings("unchecked")
    private static Map<String, String> getPlistMap(Context context) {
        if (plistMap == null) {
            try {
                PListParser parser = new PListParser(context.getAssets().open("expression.xml"));
                plistMap = (HashMap<String, String>) parser.root;
            } catch (IOException e) {
                Log.e("Android-Common", e.toString());
            }

        }
        return plistMap;
    }


}
