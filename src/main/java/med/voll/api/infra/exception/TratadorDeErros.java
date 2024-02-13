package med.voll.api.infra.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratadorDeErros {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratar404(){
        return ResponseEntity.notFound().build();
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratar400(MethodArgumentNotValidException e){
        var errors = e.getFieldErrors();

        return ResponseEntity.badRequest().body(errors.stream().map(DadosErroValidacao::new).toList());
    }

    private record DadosErroValidacao(String campo, String mensagem){
    // record/DTO interno pra uso interno
        public DadosErroValidacao(FieldError erro){
            this(erro.getField(), erro.getDefaultMessage());
        }
    }
}
