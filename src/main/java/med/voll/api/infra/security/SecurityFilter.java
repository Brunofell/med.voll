package med.voll.api.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
// aqui estamos capturando o token, e bloqueando requisições que precisam do token
@Component // componente generico, so ra carregar a classe mesmo
public class SecurityFilter extends OncePerRequestFilter { // o spring garante que essa classe vai ser executada apenas uma vez por requisição
    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository repository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var tokenJWT = recuperarToken(request); // recupera o token do cabecalho

        if (tokenJWT != null) { // se tem cabecalho, faz a verificação do token
            var subject = tokenService.getSubject(tokenJWT);
            var usuario = repository.findByLogin(subject); // recuperamos o obj usuario pelo repository e subject(email)

            // forçando autenticação abaixo
            var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities()); // é com fosse fosse um dto do spring que representa um user logado no sistema
            SecurityContextHolder.getContext().setAuthentication(authentication); // agr o sprign considera que estamos logados
            System.out.println("Logado na requisição.");
        }

        // se não tem, segue o fluxo, vai bater na valid nop spring e checa se a pessoa ta logada.
        filterChain.doFilter(request, response); // continua o fluxo da requisição, se não tiver ele para a requisição e não retorna nd. mas da cod 200 ok
    }

    private String recuperarToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader != null) { // aqui bloqueava pq ao fazer login não precisava de header, dair timudamos pra dentro do if.
            return authHeader.replace("Bearer ", "").trim(); // tira o prefixo
        }
        return null;
    }
}