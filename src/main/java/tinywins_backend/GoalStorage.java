package tinywins_backend;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;

@Component
@Transactional
public class GoalStorage {

    private final GoalRepository goalRepository;

    public GoalStorage(GoalRepository goalRepository) {
        this.goalRepository = goalRepository;
    }

    @PostConstruct
    public void seedDemoGoalsIfDatabaseIsEmpty() {
        if (goalRepository.count() > 0) {
            return;
        }

        GoalEntity diploma = new GoalEntity(
                "Написать диплом",
                LocalDate.of(2027, 4, 1)
        );

        GoalPartEntity diplomaPlan = new GoalPartEntity(
                "Сделать план диплома",
                null,
                GoalPartType.NORMAL,
                null,
                0,
                0
        );
        diplomaPlan.setCompleted(true);

        diploma.addPart(diplomaPlan);

        diploma.addPart(new GoalPartEntity(
                "Написать актуальность",
                null,
                GoalPartType.NORMAL,
                null,
                0,
                0
        ));

        diploma.addPart(new GoalPartEntity(
                "Собрать источники",
                null,
                GoalPartType.NORMAL,
                null,
                0,
                0
        ));

        goalRepository.save(diploma);

        goalRepository.save(new GoalEntity(
                "Выучить немецкий",
                LocalDate.of(2027, 5, 1)
        ));

        goalRepository.save(new GoalEntity(
                "Подготовиться к интервью",
                LocalDate.of(2026, 5, 15)
        ));
    }

    public ArrayList<GoalResponse> getGoals() {
        ArrayList<GoalResponse> responses = new ArrayList<>();

        for (GoalEntity goal : goalRepository.findAllByOrderByIdAsc()) {
            responses.add(toResponse(goal));
        }

        return responses;
    }

    public GoalResponse addGoal(CreateGoalRequest request) {
        GoalEntity newGoal = new GoalEntity(
                request.getTitle(),
                request.getDeadline()
        );

        GoalEntity savedGoal = goalRepository.save(newGoal);

        return toResponse(savedGoal);
    }

    public GoalResponse findGoalById(Long id) {
        GoalEntity goal = findEntityById(id);

        if (goal == null) {
            return null;
        }

        return toResponse(goal);
    }

    public boolean deleteById(Long id) {
        GoalEntity goal = findEntityById(id);

        if (goal == null) {
            return false;
        }

        goalRepository.delete(goal);
        return true;
    }

    public GoalResponse updateProgress(Long id, int progressPercent) {
        if (progressPercent < 0 || progressPercent > 100) {
            return null;
        }

        GoalEntity goal = findEntityById(id);

        if (goal == null) {
            return null;
        }

        return toResponse(goal);
    }

    public GoalResponse updateGoal(Long id, UpdateGoalRequest request) {
        GoalEntity goal = findEntityById(id);

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

        GoalEntity savedGoal = goalRepository.save(goal);

        return toResponse(savedGoal);
    }

    public GoalResponse updateDeadline(Long id, LocalDate deadline) {
        GoalEntity goal = findEntityById(id);

        if (goal == null) {
            return null;
        }

        goal.setDeadline(deadline);

        GoalEntity savedGoal = goalRepository.save(goal);

        return toResponse(savedGoal);
    }

    public GoalResponse completePart(Long goalId, int partIndex) {
        GoalEntity goal = findEntityById(goalId);

        if (goal == null) {
            return null;
        }

        if (!isValidPartIndex(goal, partIndex)) {
            return null;
        }

        GoalPartEntity part = goal.getParts().get(partIndex);
        part.setCompleted(true);

        GoalEntity savedGoal = goalRepository.save(goal);

        return toResponse(savedGoal);
    }

    public GoalResponse addPart(Long goalId, CreateGoalPartRequest request) {
        GoalEntity goal = findEntityById(goalId);

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

        GoalPartEntity newPart;

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

            newPart = new GoalPartEntity(
                    request.getTitle(),
                    request.getDeadline(),
                    GoalPartType.MEASURABLE,
                    request.getUnit(),
                    currentAmount,
                    request.getTargetAmount()
            );
        } else {
            newPart = new GoalPartEntity(
                    request.getTitle(),
                    request.getDeadline(),
                    GoalPartType.NORMAL,
                    null,
                    0,
                    0
            );
        }

        goal.addPart(newPart);

        GoalEntity savedGoal = goalRepository.save(goal);

        return toResponse(savedGoal);
    }

    public GoalResponse updatePartCompleted(
            Long goalId,
            int partIndex,
            boolean completed) {

        GoalEntity goal = findEntityById(goalId);

        if (goal == null) {
            return null;
        }

        if (!isValidPartIndex(goal, partIndex)) {
            return null;
        }

        GoalPartEntity part = goal.getParts().get(partIndex);
        part.setCompleted(completed);

        GoalEntity savedGoal = goalRepository.save(goal);

        return toResponse(savedGoal);
    }

    public GoalResponse updatePartAmount(
            Long goalId,
            int partIndex,
            int amountToAdd) {

        GoalEntity goal = findEntityById(goalId);

        if (goal == null) {
            return null;
        }

        if (!isValidPartIndex(goal, partIndex)) {
            return null;
        }

        GoalPartEntity part = goal.getParts().get(partIndex);

        if (part.getType() != GoalPartType.MEASURABLE) {
            return null;
        }

        if (amountToAdd <= 0) {
            return null;
        }

        int newCurrentAmount = part.getCurrentAmount() + amountToAdd;

        part.setCurrentAmount(newCurrentAmount);

        GoalEntity savedGoal = goalRepository.save(goal);

        return toResponse(savedGoal);
    }

    public GoalResponse deletePart(Long goalId, int partIndex) {
        GoalEntity goal = findEntityById(goalId);

        if (goal == null) {
            return null;
        }

        if (!isValidPartIndex(goal, partIndex)) {
            return null;
        }

        GoalPartEntity removedPart = goal.getParts().remove(partIndex);
        removedPart.setGoal(null);

        reindexParts(goal);

        GoalEntity savedGoal = goalRepository.save(goal);

        return toResponse(savedGoal);
    }

    private GoalEntity findEntityById(Long id) {
        return goalRepository.findById(id).orElse(null);
    }

    private boolean isValidPartIndex(GoalEntity goal, int partIndex) {
        return partIndex >= 0 && partIndex < goal.getParts().size();
    }

    private void reindexParts(GoalEntity goal) {
        for (int index = 0; index < goal.getParts().size(); index++) {
            goal.getParts().get(index).setPositionIndex(index);
        }
    }

    private GoalResponse toResponse(GoalEntity entity) {
        GoalResponse response = new GoalResponse(
                entity.getId(),
                entity.getTitle(),
                0,
                entity.getDeadline()
        );

        for (GoalPartEntity partEntity : entity.getParts()) {
            response.addPart(toGoalPart(partEntity));
        }

        return response;
    }

    private GoalPart toGoalPart(GoalPartEntity entity) {
        GoalPart part = new GoalPart(
                entity.getTitle(),
                entity.getDeadline(),
                entity.getType(),
                entity.getUnit(),
                entity.getCurrentAmount(),
                entity.getTargetAmount()
        );

        part.setCompleted(entity.isCompleted());

        return part;
    }
}