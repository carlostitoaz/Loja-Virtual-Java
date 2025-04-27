package br.edu.iff.ccc.bsi.foreverfashion.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IdNaoEncontrado.class)
    public ResponseEntity<String> tratarIdNaoEncontrado(IdNaoEncontrado e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(JaCadastrado.class)
    public ResponseEntity<String> tratarJaCadastrado(JaCadastrado e){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
}
