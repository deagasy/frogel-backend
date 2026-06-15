package tinywins_backend;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GoalResponse {

    private Long id;
    private String title;
    private String description;
    private int progressPercent;
    private LocalDate deadline;
    private LocalDate lastUpdatedAt;

    private List<GoalPart> parts = new ArrayList<>();

    public GoalResponse(Long id, String title, int progressPercent, LocalDate deadline, LocalDate lastUpdatedAt) {
        this.id = id;
        this.title = title;
        this.progressPercent = progressPercent;
        this.deadline = deadline;
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getProgressPercent() {
        if (parts.isEmpty()) {
            return 0;
        }

        int totalProgress = 0;

        for (GoalPart part : parts) {
            totalProgress += part.getProgressPercent();
        }
        return totalProgress / parts.size();
    }

    public void setProgressPercent(int progressPercent) {
        this.progressPercent = progressPercent;
    }

    public List<GoalPart> getParts() {
        return parts;
    }

    public void addPart(GoalPart part) {
        parts.add(part);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public LocalDate getLastUpdatedAt() {
        return lastUpdatedAt;
    }
}
