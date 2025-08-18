package plataforma.exticao.seguranca;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class JwtService {

    private static final String CHAVE_SECRETA = "sua-chave-secreta-aqui";

    private final Algorithm algorithm = Algorithm.HMAC256(CHAVE_SECRETA);

    public String gerarToken(UserDetails userDetails) {
        long expirationTime = 1000 * 60 * 60 * 10; // 10 horas
        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
                .sign(algorithm);
    }

    public String extractUsername(String token) {
        DecodedJWT decodedJWT = JWT.require(algorithm).build().verify(token);
        return decodedJWT.getSubject();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        try {
            String username = extractUsername(token);
            DecodedJWT decodedJWT = JWT.require(algorithm).build().verify(token);
            return username.equals(userDetails.getUsername()) &&
                    decodedJWT.getExpiresAt().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
