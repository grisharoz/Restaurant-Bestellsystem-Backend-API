package group10.GastroAPI.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {
   record ApiError(int status, String error, String message){}

   @ExceptionHandler(IllegalArgumentException.class)
   @ResponseStatus(HttpStatus.BAD_REQUEST)
   public ApiError handleBadRequest(IllegalArgumentException exception){
      return new ApiError(400, "BAD_REQUEST", exception.getMessage());
   }

   @ExceptionHandler(NotFoundException.class)
   @ResponseStatus(HttpStatus.NOT_FOUND)
   public ApiError handleNotFound(NotFoundException exception){
      return new ApiError(404, "NOT_FOUND", exception.getMessage());
   }

}
