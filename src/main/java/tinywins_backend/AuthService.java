package tinywins_backend;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthUserResponse register(RegisterRequest request) {
        String normalizedEmail = request.getEmail().trim().toLowerCase();

        if (userRepository.findByEmail(normalizedEmail).isPresent()) {
            throw new DuplicateEmailException();
        }

        String hash = passwordEncoder.encode(request.getPassword());
        UserEntity user = new UserEntity(
                normalizedEmail,
                hash,
                request.getDisplayName().trim(),
                LocalDate.now()
        );

        UserEntity saved = userRepository.save(user);

        return new AuthUserResponse(
                saved.getId(),
                saved.getEmail(),
                saved.getDisplayName(),
                saved.getCreatedAt()
        );
    }
}
