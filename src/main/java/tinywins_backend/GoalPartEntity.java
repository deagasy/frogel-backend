package tinywins_backend;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "goal_parts")
public class GoalPartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private boolean completed;

    private LocalDate deadline;

    @Enumerated(EnumType.STRING)
    private GoalPartType type = GoalPartType.NORMAL;

    private String unit;

    private int currentAmount;

    private int targetAmount;

    private int positionIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    private GoalEntity goal;

    public GoalPartEntity() {
    }

    public GoalPartEntity(
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

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public GoalPartType getType() {
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

    public int getPositionIndex() {
        return positionIndex;
    }

    public GoalEntity getGoal() {
        return goal;
    }

    public void setGoal(GoalEntity goal) {
        this.goal = goal;
    }

    public void setPositionIndex(int positionIndex) {
        this.positionIndex = positionIndex;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void setCurrentAmount(int currentAmount) {
        this.currentAmount = currentAmount;
        updateCompletedFromAmount();
    }

    public void setTargetAmount(int targetAmount) {
        this.targetAmount = targetAmount;
        updateCompletedFromAmount();
    }

    private void updateCompletedFromAmount() {
        if (type == GoalPartType.MEASURABLE && targetAmount > 0) {
            completed = currentAmount >= targetAmount;
        }
    }

    public int getProgressPercent() {
        if (type == GoalPartType.MEASURABLE) {
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