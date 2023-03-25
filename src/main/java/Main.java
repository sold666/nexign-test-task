import enums.CallType;
import enums.Tariff;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    private static final String FILE_NAME = "cdr.txt";
    private static final Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        PropertyConfigurator.configure("log4j.properties");
        Map<String, List<CDR>> callRecords = readCDR();
        for (String phoneNumber : callRecords.keySet()) {
            ReportGenerator.generateReport(phoneNumber, callRecords.get(phoneNumber));
        }
    }

    private static Map<String, List<CDR>> readCDR() {
        Map<String, List<CDR>> callRecords = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(Main.FILE_NAME))) {
            List<String> lines = reader.lines().collect(Collectors.toList());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            for (String line : lines) {
                String[] values = line.split(",\\s*");
                CallType callType = CallType.getByCode(values[0]);
                String phoneNumber = values[1];
                LocalDateTime startTime = LocalDateTime.parse(values[2], formatter);
                LocalDateTime endTime = LocalDateTime.parse(values[3], formatter);
                Tariff tariff = Tariff.getByIndex(values[4]);
                CDR cdr = new CDR(callType, phoneNumber, startTime, endTime, tariff);
                List<CDR> phoneCallRecords = callRecords.getOrDefault(phoneNumber, new ArrayList<>());
                phoneCallRecords.add(cdr);
                callRecords.put(phoneNumber, phoneCallRecords);
            }
        } catch (IOException e) {
            logger.error("Can't read file. Check the file name.");
        }
        return callRecords;
    }
}
