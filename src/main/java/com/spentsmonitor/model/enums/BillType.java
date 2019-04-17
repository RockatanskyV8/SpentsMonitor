package com.spentsmonitor.model.enums;

import java.util.HashMap;
import java.util.Map;

public enum BillType {
	CONTA(1),
	BOLETO(2),
	FATURA(3);
	
	private int value;
	private static Map map = new HashMap<>();
	
    private BillType(int value) {
        this.value = value;
    }

    static {
        for (BillType billType : BillType.values()) {
            map.put(billType.value, billType);
        }
    }
    
    public static BillType valueOf(int billType) {
        return (BillType) map.get(billType);
    }

    public int getValue() {
        return value;
    }

}
