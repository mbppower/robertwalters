package br.com.comexport.DTO;

import java.util.UUID;

public class LancamentoContabilSuccessDTO {
    private UUID id;

    private String message;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
