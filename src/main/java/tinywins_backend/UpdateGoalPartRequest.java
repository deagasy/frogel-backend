package tinywins_backend;

import java.time.LocalDate;

public class UpdateGoalPartRequest {
    private Boolean completed;
    private String title;
    private LocalDate deadline;
    private GoalPartType type;
    private String unit;
    private Integer currentAmount;
    private Integer targetAmount;

    public Boolean getCompleted() { return completed; }
    public String getTitle() { return title; }
    public LocalDate getDeadline() { return deadline; }
    public GoalPartType getType() { return type; }
    public String getUnit() { return unit; }
    public Integer getCurrentAmount() { return currentAmount; }
    public Integer getTargetAmount() { return targetAmount; }
}
