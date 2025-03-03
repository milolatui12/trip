package tritnt.trip.dto.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthAccessToken {
    private String accessToken;
    private String refreshToken;

    public AuthAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
