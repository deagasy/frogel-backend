package tinywins_backend;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class GoalsController {

    private GoalStorage goalStorage;

    public GoalsController(GoalStorage goalStorage) {
        this.goalStorage = goalStorage;
    }

    @GetMapping("/goals")
    public ArrayList<GoalResponse> getGoals() {
        return goalStorage.getGoals();
    }

    @PostMapping("/goals")
    public GoalResponse createGoal(@RequestBody CreateGoalRequest request) {
        return goalStorage.addGoal(request);
    }

    @GetMapping("/goals/{id}")
    public ResponseEntity<GoalResponse> getGoalById(@PathVariable Long id) {
        GoalResponse goal = goalStorage.findGoalById(id);
        if (goal == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(goal);
    }

    @DeleteMapping("/goals/{id}")
    public ResponseEntity<Void> deleteGoal(@PathVariable Long id) {
        boolean deleted = goalStorage.deleteById(id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }


    @PatchMapping("/goals/{id}/progress")
    public ResponseEntity<GoalResponse> updateProgress(
            @PathVariable Long id,
            @RequestBody UpdateGoalProgressRequest request) {
        int progressPercent = request.getProgressPercent();

        if (progressPercent < 0 || progressPercent > 100) {
            return ResponseEntity.badRequest().build();
        }

        GoalResponse updatedGoal = goalStorage.updateProgress(id, progressPercent);

        if (updatedGoal == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updatedGoal);
    }

    @PutMapping("/goals/{id}")
    public ResponseEntity<GoalResponse> updateGoal(
            @PathVariable Long id,
            @RequestBody UpdateGoalRequest request
    ) {
        if (request.getTitle() == null || request.getTitle().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        GoalResponse updatedGoal = goalStorage.updateGoal(id, request);

        if (updatedGoal == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updatedGoal);
    }

    @PatchMapping("/goals/{id}/deadline")
    public ResponseEntity<GoalResponse> updateGoalDeadline(
            @PathVariable Long id,
            @RequestBody UpdateGoalRequest request
    ) {
        if (request.getDeadline() == null) {
            return ResponseEntity.badRequest().build();
        }

        GoalResponse updatedGoal = goalStorage.updateDeadline(id, request.getDeadline());

        if (updatedGoal == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedGoal);
    }


    @PatchMapping("/goals/{goalId}/parts/{partIndex}/complete")
    public GoalResponse completePart(
            @PathVariable Long goalId,
            @PathVariable int partIndex) {
        return goalStorage.completePart(goalId, partIndex);
    }

    @PostMapping("/goals/{id}/parts")
    public ResponseEntity<GoalResponse> addPart(
            @PathVariable Long id,
            @RequestBody CreateGoalPartRequest request) {

        GoalResponse goal = goalStorage.addPart(id, request);

        if (goal == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(goal);
    }

    @PatchMapping("/goals/{goalId}/parts/{partIndex}")
    public ResponseEntity<GoalResponse> updatePartCompleted(
            @PathVariable Long goalId,
            @PathVariable int partIndex,
            @RequestBody UpdateGoalPartRequest request) {
        GoalResponse goal = goalStorage.updatePartCompleted(
                goalId,
                partIndex,
                request.isCompleted()
        );

        if (goal == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(goal);
    }

    @PatchMapping("/goals/{goalId}/parts/{partIndex}/amount")
    public ResponseEntity<GoalResponse> updatePartAmount(
            @PathVariable Long goalId,
            @PathVariable int partIndex,
            @RequestBody UpdateGoalPartAmountRequest request) {

        if (request.getAmountToAdd() == null || request.getAmountToAdd() <= 0) {
            return ResponseEntity.badRequest().build();
        }

        GoalResponse goal = goalStorage.updatePartAmount(
                goalId,
                partIndex,
                request.getAmountToAdd()
        );

        if (goal == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(goal);
    }


    @DeleteMapping("/goals/{goalId}/parts/{partIndex}")
    public ResponseEntity<GoalResponse> deletePart(
            @PathVariable Long goalId,
            @PathVariable int partIndex) {
        GoalResponse goal = goalStorage.deletePart(goalId, partIndex);

        if (goal == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(goal);
    }
}
