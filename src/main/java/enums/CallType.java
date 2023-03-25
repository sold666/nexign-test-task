package enums;

import org.apache.log4j.Logger;

public enum CallType {
    OUTGOING("01"),
    INCOMING("02");

    private final String code;
    private static final Logger logger = Logger.getLogger(CallType.class);

    CallType(String code) {
        this.code = code;
    }

    public static CallType getByCode(String code) {
        for (CallType type : CallType.values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        logger.error("There is no tariff with such an index " + code);
        throw new IllegalArgumentException();
    }

    public String getCode() {
        return code;
    }
}
