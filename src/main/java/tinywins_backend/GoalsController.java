package tinywins_backend;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
public class GoalsController {

    private final GoalStorage goalStorage;
    private final AuthService authService;

    public GoalsController(GoalStorage goalStorage, AuthService authService) {
        this.goalStorage = goalStorage;
        this.authService = authService;
    }

    @GetMapping("/goals")
    public ResponseEntity<?> getGoals(
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        UserEntity owner = authService.resolveUser(authHeader);
        if (owner == null) return unauthorized();

        ArrayList<GoalResponse> goals = goalStorage.getGoals(owner);
        return ResponseEntity.ok(goals);
    }

    @PostMapping("/goals")
    public ResponseEntity<?> createGoal(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @RequestBody CreateGoalRequest request) {
        UserEntity owner = authService.resolveUser(authHeader);
        if (owner == null) return unauthorized();

        GoalResponse goal = goalStorage.addGoal(request, owner);
        return ResponseEntity.ok(goal);
    }

    @GetMapping("/goals/{id}")
    public ResponseEntity<?> getGoalById(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @PathVariable Long id) {
        UserEntity owner = authService.resolveUser(authHeader);
        if (owner == null) return unauthorized();

        GoalResponse goal = goalStorage.findGoalById(id, owner);
        if (goal == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(goal);
    }

    @DeleteMapping("/goals/{id}")
    public ResponseEntity<?> deleteGoal(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @PathVariable Long id) {
        UserEntity owner = authService.resolveUser(authHeader);
        if (owner == null) return unauthorized();

        boolean deleted = goalStorage.deleteById(id, owner);
        if (!deleted) return ResponseEntity.notFound().build();

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/goals/{id}/progress")
    public ResponseEntity<?> updateProgress(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @PathVariable Long id,
            @RequestBody UpdateGoalProgressRequest request) {
        UserEntity owner = authService.resolveUser(authHeader);
        if (owner == null) return unauthorized();

        int progressPercent = request.getProgressPercent();
        if (progressPercent < 0 || progressPercent > 100) return ResponseEntity.badRequest().build();

        GoalResponse updatedGoal = goalStorage.updateProgress(id, progressPercent, owner);
        if (updatedGoal == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(updatedGoal);
    }

    @PutMapping("/goals/{id}")
    public ResponseEntity<?> updateGoal(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @PathVariable Long id,
            @RequestBody UpdateGoalRequest request) {
        UserEntity owner = authService.resolveUser(authHeader);
        if (owner == null) return unauthorized();

        if (request.getTitle() == null || request.getTitle().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        GoalResponse updatedGoal = goalStorage.updateGoal(id, request, owner);
        if (updatedGoal == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(updatedGoal);
    }

    @PatchMapping("/goals/{id}/deadline")
    public ResponseEntity<?> updateGoalDeadline(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @PathVariable Long id,
            @RequestBody UpdateGoalRequest request) {
        UserEntity owner = authService.resolveUser(authHeader);
        if (owner == null) return unauthorized();

        if (request.getDeadline() == null) return ResponseEntity.badRequest().build();

        GoalResponse updatedGoal = goalStorage.updateDeadline(id, request.getDeadline(), owner);
        if (updatedGoal == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(updatedGoal);
    }

    @PatchMapping("/goals/{goalId}/parts/{partIndex}/complete")
    public ResponseEntity<?> completePart(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @PathVariable Long goalId,
            @PathVariable int partIndex) {
        UserEntity owner = authService.resolveUser(authHeader);
        if (owner == null) return unauthorized();

        GoalResponse goal = goalStorage.completePart(goalId, partIndex, owner);
        if (goal == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(goal);
    }

    @PostMapping("/goals/{id}/parts")
    public ResponseEntity<?> addPart(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @PathVariable Long id,
            @RequestBody CreateGoalPartRequest request) {
        UserEntity owner = authService.resolveUser(authHeader);
        if (owner == null) return unauthorized();

        GoalResponse goal = goalStorage.addPart(id, request, owner);
        if (goal == null) return ResponseEntity.badRequest().build();

        return ResponseEntity.ok(goal);
    }

    @PatchMapping("/goals/{goalId}/parts/{partIndex}")
    public ResponseEntity<?> updatePart(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @PathVariable Long goalId,
            @PathVariable int partIndex,
            @RequestBody UpdateGoalPartRequest request) {
        UserEntity owner = authService.resolveUser(authHeader);
        if (owner == null) return unauthorized();

        try {
            GoalResponse goal = goalStorage.updatePart(goalId, partIndex, request, owner);
            if (goal == null) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(goal);
        } catch (PartValidationException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getErrorCode()));
        }
    }

    @PatchMapping("/goals/{goalId}/parts/{partIndex}/amount")
    public ResponseEntity<?> updatePartAmount(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @PathVariable Long goalId,
            @PathVariable int partIndex,
            @RequestBody UpdateGoalPartAmountRequest request) {
        UserEntity owner = authService.resolveUser(authHeader);
        if (owner == null) return unauthorized();

        if (request.getAmountToAdd() == null || request.getAmountToAdd() <= 0) {
            return ResponseEntity.badRequest().build();
        }

        GoalResponse goal = goalStorage.updatePartAmount(goalId, partIndex, request.getAmountToAdd(), owner);
        if (goal == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(goal);
    }

    @DeleteMapping("/goals/{goalId}/parts/{partIndex}")
    public ResponseEntity<?> deletePart(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @PathVariable Long goalId,
            @PathVariable int partIndex) {
        UserEntity owner = authService.resolveUser(authHeader);
        if (owner == null) return unauthorized();

        GoalResponse goal = goalStorage.deletePart(goalId, partIndex, owner);
        if (goal == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(goal);
    }

    private ResponseEntity<Map<String, String>> unauthorized() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "token_required"));
    }
}
