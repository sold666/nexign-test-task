import enums.CallType;
import enums.Tariff;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CDR {
    private CallType callType;
    private String phoneNumber;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private long duration;
    private Tariff tariff;

    public CDR(CallType callType, String phoneNumber, LocalDateTime startTime, LocalDateTime endTime, Tariff tariff) {
        this.callType = callType;
        this.phoneNumber = phoneNumber;
        this.startTime = startTime;
        this.endTime = endTime;
        this.tariff = tariff;
        this.duration = Duration.between(startTime, endTime).toMinutes();
    }

    public double getCost() {
        return tariff.getCost(duration);
    }

    public CallType getCallType() {
        return callType;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getStartTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return startTime.format(formatter);
    }

    public String getEndTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return endTime.format(formatter);
    }

    public long getDuration() {
        return duration;
    }

    public String getDurationAsString() {
        Duration duration = Duration.between(startTime, endTime);
        long seconds = duration.getSeconds();
        long absSeconds = Math.abs(seconds);
        String formattedTime = String.format("%02d:%02d:%02d", absSeconds / 3600, (absSeconds % 3600) / 60, absSeconds % 60);
        return seconds < 0 ? "-" + formattedTime : formattedTime;
    }

    public Tariff getTariff() {
        return tariff;
    }
}
