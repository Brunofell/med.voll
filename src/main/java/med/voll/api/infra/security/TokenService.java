package med.voll.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import med.voll.api.domain.usuario.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService {
    @Value("${api.security.token.secret}") // injeta no app.prop
    private String secret;
    public String gerarToken(Usuario usuario){
        try {
            var algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("API Voll.med")
                    .withSubject(usuario.getLogin())      ////////
                    .withClaim("id", usuario.getId()) // passamos a info para guardAR MAIS INFOS NO TOKEN, NO CASO PASSAMOS ID
                    .withExpiresAt(dataExpiracao())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException("ERRO AO GERAR TOKEN JWT", exception);
        }
    }

    private Instant dataExpiracao() { // definimos data de expiração pra duas horas
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
