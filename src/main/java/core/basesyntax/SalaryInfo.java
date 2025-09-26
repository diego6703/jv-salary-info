package core.basesyntax;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SalaryInfo {
    private static final DateTimeFormatter dateFormat
            = DateTimeFormatter.ofPattern(Constants.PARSE_FORMAT);

    public String getSalaryInfo(String[] names, String[] data, String dateFrom, String dateTo) {
        StringBuilder sb = new StringBuilder();
        int numberOfEmployees = names.length;
        int[] employeesEarnings = new int[numberOfEmployees];
        String[] singleEmployeeData;
        String singleEmployeeName;
        LocalDate singleEmployeeWorkingDayDate;
        LocalDate startDate = LocalDate.parse(dateFrom, dateFormat);
        LocalDate endDate = LocalDate.parse(dateTo, dateFormat);
        int singleEmployeeHoursWorked;
        int singleEmployeeEarningsPerHour;
        int singleEmployeeEarnings;
        for (String employeeData : data) {
            singleEmployeeData = employeeData.split(Constants.DATA_SPLIT_SEPARATOR);
            singleEmployeeWorkingDayDate
                    = LocalDate.parse(singleEmployeeData[Constants.DATE_INDEX], dateFormat);
            if ((singleEmployeeWorkingDayDate.isAfter(startDate)
                    || singleEmployeeWorkingDayDate.isEqual(startDate))
                    && (singleEmployeeWorkingDayDate.isBefore(endDate)
                    || singleEmployeeWorkingDayDate.isEqual(endDate))) {
                singleEmployeeName = singleEmployeeData[Constants.NAME_INDEX];
                singleEmployeeHoursWorked
                        = Integer.parseInt(singleEmployeeData[Constants.HOURS_INDEX]);
                singleEmployeeEarningsPerHour
                        = Integer.parseInt(singleEmployeeData[Constants.RATE_INDEX]);
                singleEmployeeEarnings = singleEmployeeHoursWorked * singleEmployeeEarningsPerHour;
                employeesEarnings[findEmployeeIndex(names, singleEmployeeName, numberOfEmployees)]
                        += singleEmployeeEarnings;
            }
        }
        sb.append(Constants.REPORT_BEGINNING)
                .append(dateFrom)
                .append(Constants.DATA_LIST_SEPARATOR).append(dateTo);
        for (int i = 0; i < numberOfEmployees; i++) {
            sb.append(System.lineSeparator())
                    .append(names[i])
                    .append(Constants.DATA_LIST_SEPARATOR)
                    .append(employeesEarnings[i]);
        }
        return sb.toString();
    }

    private int findEmployeeIndex(String[] employeeNames,
                                  String employeeName, int numberOfEmployees) {
        for (int i = 0; i < numberOfEmployees; i++) {
            if (employeeName.equals(employeeNames[i])) {
                return i;
            }
        }
        return 0;
    }
}
