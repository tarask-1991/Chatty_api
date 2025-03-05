import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PasswordUpdateRequest {
    private String currentPassword; //старый пароль
    private String newPassword; // новый пароль
    private String confirmPassword;
}