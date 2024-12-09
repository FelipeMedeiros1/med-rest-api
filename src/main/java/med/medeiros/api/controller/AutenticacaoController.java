package med.medeiros.api.controller;

import jakarta.validation.Valid;
import med.medeiros.api.usuario.DadosAutenticacao;
import med.medeiros.api.usuario.Usuario;
import med.medeiros.api.infra.seguranca.DadosTokenJWT;
import med.medeiros.api.infra.seguranca.TokenService;
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
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager gerenciador;
    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutenticacao dados) {
        try {

            var autenticacaToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
            var autenticador = gerenciador.authenticate(autenticacaToken);
            var tokenJWT = tokenService.gerarToken((Usuario) autenticador.getPrincipal());

            return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));

        }catch (Exception exception){
            return ResponseEntity.badRequest().body(exception.getMessage());
        }

    }
}
