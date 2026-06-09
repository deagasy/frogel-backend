package tinywins_backend;

import java.time.LocalDate;

public class UpdateGoalRequest {
    private String title;
    private LocalDate deadline;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }
}
