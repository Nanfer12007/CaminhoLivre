package br.com.caminholivre.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.caminholivre.dto.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log =
        LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request) {

        return buildResponse(
            HttpStatus.NOT_FOUND,
            ex.getMessage(),
            request.getRequestURI(),
            null
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        Map<String, String> errors = new HashMap<>();

        for (FieldError error :
                ex.getBindingResult().getFieldErrors()) {

            errors.put(
                error.getField(),
                error.getDefaultMessage()
            );
        }

        return buildResponse(
            HttpStatus.BAD_REQUEST,
            "Erro de validação nos campos informados",
            request.getRequestURI(),
            errors
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleIntegrity(
            DataIntegrityViolationException ex,
            HttpServletRequest request) {

        log.warn("Violação de integridade em {}",
                 request.getRequestURI(), ex);

        return buildResponse(
            HttpStatus.CONFLICT,
            "Não foi possível realizar a operação devido a uma restrição do banco de dados",
            request.getRequestURI(),
            null
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleUnreadable(
            HttpMessageNotReadableException ex,
            HttpServletRequest request) {

        return buildResponse(
            HttpStatus.BAD_REQUEST,
            "JSON inválido ou valor de enumeração não reconhecido",
            request.getRequestURI(),
            null
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(
            Exception ex,
            HttpServletRequest request) {

        log.error(
            "Erro interno na requisição {}",
            request.getRequestURI(),
            ex
        );

        return buildResponse(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "Ocorreu um erro interno no servidor",
            request.getRequestURI(),
            null
        );
    }

    private ResponseEntity<ErrorResponse> buildResponse(
            HttpStatus status,
            String message,
            String path,
            Map<String, String> errors) {

        ErrorResponse response = new ErrorResponse(
            LocalDateTime.now(),
            status.value(),
            status.getReasonPhrase(),
            message,
            path,
            errors
        );

        return ResponseEntity.status(status).body(response);
    }
}
