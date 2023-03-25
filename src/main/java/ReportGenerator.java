import enums.Tariff;
import org.apache.log4j.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class ReportGenerator {

    private static final Logger logger = Logger.getLogger(ReportGenerator.class);

    static void generateReport(String phoneNumber, List<CDR> callRecords) {
        File directoryForReports = new File("reports");
        if (!directoryForReports.exists()) {
            directoryForReports.mkdir();
        }

        String fileName = "reports/" + phoneNumber + ".txt";

        File file = new File(fileName);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            logger.error("Can't create file");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file.getAbsoluteFile()))) {
            writer.write("Tariff index: " + callRecords.get(0).getTariff().getIndex() + "\n");
            writer.write("---------------------------------------------------------------------------\n");
            writer.write("Report for phone number " + phoneNumber + ":\n");
            writer.write("---------------------------------------------------------------------------\n");
            writer.write("| Call Type |   Start Time        |     End Time        | Duration | Cost |\n");
            writer.write("---------------------------------------------------------------------------\n");
            for (CDR record : callRecords) {
                writer.write("|     " + record.getCallType().getCode() + "    | ");
                writer.write(record.getStartTime() + " | ");
                writer.write(record.getEndTime() + " | ");
                writer.write(record.getDurationAsString() + " | ");
                writer.write(String.format(Locale.US, "%.2f", record.getCost()) + " |\n");
            }
            writer.write("---------------------------------------------------------------------------\n");
            writer.write(String.format("|                                     Total Cost: | %1$14.2f rubles |\n", getTotalCostForPhoneNumber(phoneNumber, callRecords)));
            writer.write("---------------------------------------------------------------------------\n");
        } catch (IOException exception) {
            logger.error("Can't write the report to a file");
        }
    }

    private static double getTotalCostForPhoneNumber(String phoneNumber, List<CDR> callRecords) {
        Tariff tariff = callRecords.get(0).getTariff();
        double totalCost = callRecords.stream()
                .filter(record -> record.getPhoneNumber().equals(phoneNumber))
                .mapToDouble(CDR::getCost)
                .sum();
        if (tariff.getIndex().equals("06") && totalCost <= tariff.getIncludedMinutes() * tariff.getPerMinuteCost()) {
            return 100.0;
        }
        return totalCost;
    }
}
