package tinywins_backend;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.time.LocalDate;

@Component
public class GoalStorage {
    private ArrayList<GoalResponse> goals = new ArrayList<>();

    public GoalStorage() {

        GoalResponse diploma = new GoalResponse(
                1L,
                "Написать диплом",
                0,
                LocalDate.of(2027, 4, 1));

        diploma.addPart(new GoalPart("Сделать план диплома"));
        diploma.addPart(new GoalPart("Написать актуальность"));
        diploma.addPart(new GoalPart("Собрать источники"));

        diploma.getParts().get(0).complete();

        goals.add(diploma);

        goals.add(new GoalResponse(2L,
                "Выучить немецкий",
                25,
                LocalDate.of(2027, 5, 1)));
        goals.add(new GoalResponse(3L,
                "Подготовиться к интервью",
                86,
                LocalDate.of(2026, 5, 15)));
    }

    public ArrayList<GoalResponse> getGoals() {
        return goals;
    }

    private Long nextId = 4L;

    public GoalResponse addGoal(CreateGoalRequest request) {
        GoalResponse newGoal = new GoalResponse(
                nextId,
                request.getTitle(),
                0,
                request.getDeadline()
        );
        goals.add(newGoal);
        nextId++;

        return newGoal;
    }

    public GoalResponse findGoalById(Long id) {
        for (GoalResponse goal : goals) {
            if (goal.getId().equals(id)) {
                return goal;
            }
        }
        return null;
    }

    public boolean deleteById(Long id) {
        GoalResponse goal = findGoalById(id);
        if (goal == null) {
            return false;
        }
        goals.remove(goal);
        return true;
    }

    public GoalResponse updateProgress(Long id, int progressPercent) {
        if (progressPercent < 0 || progressPercent > 100) {
            return null;
        }
        GoalResponse goal = findGoalById(id);

        if (goal == null) {
            return null;
        }

        goal.setProgressPercent(progressPercent);
        return goal;
    }

    public GoalResponse updateGoal(Long id, UpdateGoalRequest request) {
        GoalResponse goal = findGoalById(id);

        if (goal == null) {
            return null;
        }

        if (request.getTitle() != null &&
                !request.getTitle().trim().isEmpty()) {

            goal.setTitle(request.getTitle());
        }

        if (request.getDeadline() != null) {
            goal.setDeadline(request.getDeadline());
        }
        return goal;
    }

    public GoalResponse updateDeadline(Long id, LocalDate deadline) {
        GoalResponse goal = findGoalById(id);

        if (goal == null) {
            return null;
        }

        goal.setDeadline(deadline);
        return goal;
    }

    public GoalResponse completePart(Long goalId, int partIndex) {
        GoalResponse goal = findGoalById(goalId);

        if (goal == null) {
            return null;
        }
        if (partIndex < 0 || partIndex >= goal.getParts().size()) {
            return null;
        }

        GoalPart part = goal.getParts().get(partIndex);
        part.setCompleted(true);

        return goal;
    }

    public GoalResponse addPart(Long goalId, CreateGoalPartRequest request) {
        GoalResponse goal = findGoalById(goalId);

        if (goal == null) {
            return null;
        }

        if (request.getTitle() == null || request.getTitle().trim().isEmpty()) {
            return null;
        }

        if (request.getDeadline() != null &&
                goal.getDeadline() != null &&
                request.getDeadline().isAfter(goal.getDeadline())) {
            return null;
        }

        GoalPartType type = request.getType();

        if (type == null) {
            type = GoalPartType.NORMAL;
        }

        GoalPart newPart;

        if (type == GoalPartType.MEASURABLE) {
            if (request.getUnit() == null || request.getUnit().trim().isEmpty()) {
                return null;
            }

            if (request.getTargetAmount() == null || request.getTargetAmount() <= 0) {
                return null;
            }

            int currentAmount = 0;

            if (request.getCurrentAmount() != null) {
                currentAmount = request.getCurrentAmount();
            }

            if (currentAmount < 0) {
                return null;
            }

            newPart = new GoalPart(
                    request.getTitle(),
                    request.getDeadline(),
                    GoalPartType.MEASURABLE,
                    request.getUnit(),
                    currentAmount,
                    request.getTargetAmount()
            );
        } else {
            newPart = new GoalPart(
                    request.getTitle(),
                    request.getDeadline(),
                    GoalPartType.NORMAL,
                    null,
                    0,
                    0
            );
        }
        goal.addPart(newPart);

        return goal;
    }

    public GoalResponse updatePartCompleted(
            Long goalId,
            int partIndex,
            boolean completed) {

        GoalResponse goal = findGoalById(goalId);

        if (goal == null) {
            return null;
        }

        if (partIndex < 0 || partIndex >= goal.getParts().size()) {
            return null;
        }

        GoalPart part = goal.getParts().get(partIndex);
        part.setCompleted(completed);

        return goal;
    }

    public GoalResponse updatePartAmount(
            Long goalId,
            int partIndex,
            int amountToAdd) {

        GoalResponse goal = findGoalById(goalId);

        if (goal == null) {
            return null;
        }

        if (partIndex < 0 || partIndex >= goal.getParts().size()) {
            return null;
        }

        GoalPart part = goal.getParts().get(partIndex);

        if (part.getType() != GoalPartType.MEASURABLE) {
            return null;
        }

        if (amountToAdd <= 0) {
            return null;
        }

        int newCurrentAmount = part.getCurrentAmount() + amountToAdd;

        part.setCurrentAmount(newCurrentAmount);

        return goal;
    }

    public GoalResponse deletePart(Long goalId, int partIndex) {
        GoalResponse goal = findGoalById(goalId);

        if (goal == null) {
            return null;
        }

        if (partIndex < 0 || partIndex >= goal.getParts().size()) {
            return null;
        }

        goal.getParts().remove(partIndex);

        return goal;
    }
}
