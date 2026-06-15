package tinywins_backend;

import java.time.LocalDate;

public class CreateGoalRequest {
    private String title;
    private String description;
    private LocalDate deadline;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDeadline() {
        return deadline;
    }
}
