package tinywins_backend;

import java.time.LocalDate;

public class GoalPart {
    private String title;
    private boolean completed;

    private LocalDate deadline;

    private GoalPartType type;

    private String unit;
    private int currentAmount;
    private int targetAmount;

    public GoalPart(String title) {
        this.title = title;
        this.completed = false;
        this.type = GoalPartType.NORMAL;
        this.currentAmount = 0;
        this.targetAmount = 0;
    }

    public GoalPart(
            String title,
            LocalDate deadline,
            GoalPartType type,
            String unit,
            int currentAmount,
            int targetAmount
    ) {
        this.title = title;
        this.deadline = deadline;
        this.type = type == null ? GoalPartType.NORMAL : type;
        this.unit = unit;
        this.currentAmount = currentAmount;
        this.targetAmount = targetAmount;

        updateCompletedFromAmount();
    }

    public String getTitle() {
        return title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void complete(){
        completed = true;
    }

    public void setCompleted(boolean completed){
        this.completed = completed;
    }

    public LocalDate getDeadline (){
        return deadline;
    }

    public GoalPartType getType (){
        return type;
    }

    public String getUnit() {
        return unit;
    }

    public int getCurrentAmount() {
        return currentAmount;
    }

    public int getTargetAmount() {
        return targetAmount;
    }

    public void setCurrentAmount(int currentAmount){
        this.currentAmount = currentAmount;
        updateCompletedFromAmount();
    }

    public void setTargetAmount(int targetAmount) {
        this.targetAmount = targetAmount;
        updateCompletedFromAmount();
    }

    private void updateCompletedFromAmount (){
        if (type == GoalPartType.MEASURABLE && targetAmount > 0){
            completed = currentAmount >=targetAmount;
        } else {
            completed = false;
        }
    }

    public int getProgressPercent() {
        if (type == GoalPartType.MEASURABLE){
            if (targetAmount <= 0) {
                return 0;
            }

            int progress = currentAmount * 100 / targetAmount;

            if (progress > 100) {
                return 100;
            }
            return progress;
        }

        if (completed) {
            return 100;
        }

        return 0;
    }
}
