package tinywins_backend;

import java.time.LocalDate;

public class AuthUserResponse {

    private Long id;
    private String email;
    private String displayName;
    private LocalDate createdAt;

    public AuthUserResponse(Long id, String email, String displayName, LocalDate createdAt) {
        this.id = id;
        this.email = email;
        this.displayName = displayName;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getDisplayName() { return displayName; }
    public LocalDate getCreatedAt() { return createdAt; }
}
