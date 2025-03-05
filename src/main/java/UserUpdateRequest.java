import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {
    private String avatarUrl;
    private String name;
    private String surname;
    private String birthDate;
    private String phone;
    private String gender;
    private String backgroundUrl;
    private boolean blocked;
}