package br.com.comexport.DTO;

import java.util.List;

public class LancamentoContabilErrorDTO {
    private String message;
    private List<ValidationErrorDTO> errors;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ValidationErrorDTO> getErrors() {
        return errors;
    }

    public void setErrors(List<ValidationErrorDTO> errors) {
        this.errors = errors;
    }
}
