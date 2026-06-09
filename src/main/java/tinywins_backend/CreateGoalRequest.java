package tinywins_backend;

import java.time.LocalDate;

public class CreateGoalRequest {
    private String title;
    private LocalDate deadline;

    public String getTitle() {
        return title;
    }

    public LocalDate getDeadline() {
        return deadline;
    }
}
