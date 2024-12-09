package med.medeiros.api.infra.seguranca;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.medeiros.api.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class FiltroSeguranca extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;
    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse resp, FilterChain fc) throws ServletException, IOException {
        var tokenJwt = recuperarToken(req);
        if (tokenJwt != null) {
            var tokenCabecalho = tokenService.getToken(tokenJwt);
            var usuario = usuarioRepository.findByLogin(tokenCabecalho);
            var autenticacao = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(autenticacao);

        }
        fc.doFilter(req, resp);
    }

    private String recuperarToken(HttpServletRequest rq) {
        var autorizacaoCabecalho = rq.getHeader("Authorization");
        if (autorizacaoCabecalho != null) {
            return autorizacaoCabecalho.replace("Bearer ", "").trim();
        }
        return null;
    }
}
