package core.basesyntax;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class SalaryInfo {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.PARSE_FORMAT);

    public String getSalaryInfo(String[] names, String[] data, String dateFrom, String dateTo) {
        StringBuilder sb = new StringBuilder();
        int numberOfEmployees = names.length;
        int[] employeesEarnings = new int[numberOfEmployees];
        String[] singleEmployeeData;
        String singleEmployeeName;
        long singleEmployeeWorkingDayDate;
        long startDate;
        long endDate;
        int singleEmployeeHoursWorked;
        int singleEmployeeEarningsPerHour;
        int singleEmployeeEarnings;
        try {
            startDate = dateFormat.parse(dateFrom).getTime();
            endDate = dateFormat.parse(dateTo).getTime();
        } catch (ParseException e) {
            throw new RuntimeException(Constants.WRONG_DATE_FORMAT_MESSAGE);
        }
        for (String employeeData : data) {
            singleEmployeeData = employeeData.split(Constants.DATA_SPLIT_SEPARATOR);
            try {
                singleEmployeeWorkingDayDate = dateFormat.parse(singleEmployeeData[0]).getTime();
            } catch (ParseException e) {
                throw new RuntimeException(Constants.WRONG_DATE_FORMAT_MESSAGE);
            }
            if (singleEmployeeWorkingDayDate >= startDate && singleEmployeeWorkingDayDate <= endDate) {
                singleEmployeeName = singleEmployeeData[1];
                singleEmployeeHoursWorked = Integer.parseInt(singleEmployeeData[2]);
                singleEmployeeEarningsPerHour = Integer.parseInt(singleEmployeeData[3]);
                singleEmployeeEarnings = singleEmployeeHoursWorked * singleEmployeeEarningsPerHour;
                for (int i = 0; i < numberOfEmployees; i++) {
                    if (singleEmployeeName.equals(names[i])) {
                        employeesEarnings[i] += singleEmployeeEarnings;
                    }
                }
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


}
