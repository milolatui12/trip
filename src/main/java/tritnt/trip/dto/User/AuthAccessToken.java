package tritnt.trip.dto.User;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthAccessToken {
    private String accessToken;
//    private String refreshToken;
}
