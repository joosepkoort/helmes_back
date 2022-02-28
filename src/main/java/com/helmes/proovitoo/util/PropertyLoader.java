package com.helmes.proovitoo.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PropertyLoader {

    private static final String PROPERTIES_FILENAME = "application.properties";

    private static Properties cache;

    public static HashMap<String, String> getPropertiesAsMap() {
        Properties properties = getProperties();

        HashMap<String, String> hashMap = new HashMap<>();

        for (Entry<Object, Object> each : properties.entrySet()) {
            String value = each.getValue().toString();
            hashMap.put(each.getKey().toString(), replaceSystemVariables(value));
        }

        return hashMap;
    }

    public static String getProperty(String key) {
        return getProperties().getProperty(key);
    }

    public static Properties getProperties() {
        if (cache != null) {
            return cache;
        }

        Properties properties = new Properties();

        try {
            String contents = FileUtil.readFileFromClasspath(PROPERTIES_FILENAME);
            properties.load(new ByteArrayInputStream(contents.getBytes()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        cache = properties;

        return properties;
    }

    private static String replaceSystemVariables(String value) {
        Pattern pattern = Pattern.compile("\\$\\{([^}]+)\\}");
        Matcher matcher = pattern.matcher(value);
        StringBuffer buf = new StringBuffer();
        while (matcher.find()) {
        	String v = System.getProperty(matcher.group(1));
        	v = v.replace("\\", "\\\\");
        	matcher.appendReplacement(buf, v);
        }
        matcher.appendTail(buf);

        return buf.toString();
    }
}
