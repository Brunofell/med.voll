package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.usuario.DadosAutenticacao;
import med.voll.api.domain.usuario.Usuario;
import med.voll.api.infra.security.DadosTokenJWT;
import med.voll.api.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthController {
    @Autowired // injeta um obj
    private AuthenticationManager manager;
    @Autowired
    private TokenService tokenService;

    @PostMapping // recebe algo
    public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutenticacao dados){
        var authToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        var authenticator = manager.authenticate(authToken);
        var tokenJWT = tokenService.gerarToken((Usuario) authenticator.getPrincipal());
        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT)); // devemos devolver o token aqui
        // o authenticator retorna o usuario autenticado, so usamos um casting pq ele espera um obj
    }

}
