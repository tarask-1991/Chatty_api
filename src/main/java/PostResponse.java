import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {
    private String id;
    private String postId;
    private String userId;
    private String title;
    private String content;
    private String createdDate;
    private String description;
    private String body;
    private String imageUrl;
    private String createdAt;
    private String updatedAt;
    private String draft;

    private UserResponse user;
}
