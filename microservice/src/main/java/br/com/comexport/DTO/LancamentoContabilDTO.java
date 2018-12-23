package br.com.comexport.DTO;

import br.com.comexport.helpers.IntDateJsonDeserializer;
import br.com.comexport.helpers.IntDateJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public class LancamentoContabilDTO {
    @NotNull
    private long contaContabil;

    @NotNull
    //@JsonFormat(pattern = "yyyyMMdd")
    @JsonSerialize(converter = IntDateJsonSerializer.class)
    @JsonDeserialize(converter = IntDateJsonDeserializer.class)
    private LocalDate data;

    @NotNull
    @Min(0)
    private BigDecimal valor;

    public long getContaContabil() {
        return contaContabil;
    }

    public void setContaContabil(long contaContabil) {
        this.contaContabil = contaContabil;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}
