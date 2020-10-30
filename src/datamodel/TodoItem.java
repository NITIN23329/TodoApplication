package datamodel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TodoItem {
    private final String shortDescription;
    private final String detailedDescription;
    private final LocalDate deadline;

    public TodoItem(String shortDescription, String detailedDescription, LocalDate deadline) {
        this.shortDescription = shortDescription;
        this.detailedDescription = detailedDescription;
        this.deadline = deadline;
    }


    public String getDetailedDescription() {
        return detailedDescription+".";
    }

    public String getDeadline() {
        // changing YYYY-MM-DD to  April 23, 2020 type
        DateTimeFormatter df =  DateTimeFormatter.ofPattern("MMMM d, yyyy");
        return df.format(deadline);
    }

    @Override
    public String toString() {
        return shortDescription;
    }
}
