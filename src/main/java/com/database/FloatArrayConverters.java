package com.database;

import androidx.room.TypeConverter;

public class FloatArrayConverters {
    @TypeConverter
    public static String floatArrayToString(float[] floatArray) {
        StringBuilder builder = new StringBuilder();
        for (float value : floatArray) {
            builder.append(value).append(",");
        }
        // Remove the trailing comma
        if (builder.length() > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }
        return builder.toString();
    }

    @TypeConverter
    public static float[] stringToFloatArray(String value) {
        String[] stringArray = value.split(",");
        float[] floatArray = new float[stringArray.length];
        for (int i = 0; i < stringArray.length; i++) {
            floatArray[i] = Float.parseFloat(stringArray[i]);
        }
        return floatArray;
    }
}
