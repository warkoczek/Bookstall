package pl.warkoczewski.Bookstall;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomGlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleException(MethodArgumentNotValidException ex){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        HttpStatus status = HttpStatus.BAD_REQUEST;
        body.put("status", status);
        List<String> errors = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + " - " + error.getDefaultMessage())
                .collect(Collectors.toList());
        body.put("errors", errors);
        return new ResponseEntity<>(body, status);
    }

}
