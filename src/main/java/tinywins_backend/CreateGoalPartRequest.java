package tinywins_backend;

import java.time.LocalDate;

public class CreateGoalPartRequest {
    private String title;
    private LocalDate deadline;

    private GoalPartType type;

    private String unit;
    private Integer currentAmount;
    private Integer targetAmount;

    public String getTitle(){
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDeadline (){
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public GoalPartType getType() {
        return type;
    }

    public void setType(GoalPartType type) {
        this.type = type;
    }

    public String getUnit (){
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(Integer currentAmount) {
        this.currentAmount = currentAmount;
    }

    public Integer getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(Integer targetAmount) {
        this.targetAmount = targetAmount;
    }
}
