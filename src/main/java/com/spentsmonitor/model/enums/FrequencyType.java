package com.spentsmonitor.model.enums;

import java.util.HashMap;
import java.util.Map;

public enum FrequencyType {
	NONE(0),
	YEAR(1),
	MONTH(2),
	WEEK(3),
	DAY(4);
	
	private int value;
	private static Map map = new HashMap<>();
	
    private FrequencyType(int value) {
        this.value = value;
    }

    static {
        for (FrequencyType FrequencyTipe : FrequencyType.values()) {
            map.put(FrequencyTipe.value, FrequencyTipe);
        }
    }
    
    public static FrequencyType valueOf(int FrequencyTipe) {
        return (FrequencyType) map.get(FrequencyTipe);
    }

    public int getValue() {
        return value;
    }
}
