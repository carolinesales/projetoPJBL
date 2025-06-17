// exceção personalizada para o sistema de condomínio
// é utilizada para indicar erros específicos relacionados ao cadastro e reservas
// de áreas comuns, moradia e outras operações do sistema
package src;
public class CondominioException extends Exception {  
    public CondominioException(String message) { 
        super(message);
    }

    public CondominioException(String message, Throwable cause) {
        super(message, cause);
    }
}
