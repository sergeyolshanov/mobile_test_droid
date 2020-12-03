package com.demo.mobile_test_droid.converters;

import androidx.room.TypeConverter;
import com.demo.mobile_test_droid.pojo.EducationPeriod;

public class Converter {

    @TypeConverter
    public String educationPeriodStart(EducationPeriod educationPeriod) {
        if (educationPeriod == null) {
            return null;
        } else {
            String period = educationPeriod.getStart() + " - " + educationPeriod.getEnd();
            return period;
        }
    }

    @TypeConverter
    public EducationPeriod educationPeriodFromString(String period) {
        String[] words = period.split(" - ");
        String start = words[0];
        String end = words[1];

        String[] startClear = start.split("T");
        String startSplit = startClear[0];
        String[] startRevers = startSplit.split("-");
        String startFormat = startRevers[2] + "." + startRevers[1] + "." + startRevers[0];

        String[] endClear = end.split("T");
        String endSplit = endClear[0];
        String[] endRevers = endSplit.split("-");
        String endFormat = endRevers[2] + "." + endRevers[1] + "." + endRevers[0];

        return new EducationPeriod(startFormat, endFormat);
    }
}
