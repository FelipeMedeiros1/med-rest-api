package med.medeiros.api.domain.ecessao;

public class ValidacaoExcecao extends RuntimeException {
    public ValidacaoExcecao(String mensagem) {
        super(mensagem);
    }
}
