package br.com.comexport.domain;

import br.com.comexport.helpers.IntDateJsonDeserializer;
import br.com.comexport.helpers.IntDateJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table
public class LancamentoContabil {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    @Column
    private long contaContabil;

    @NotNull
    @Column
    //@JsonFormat(pattern = "yyyyMMdd")
    @JsonSerialize(converter = IntDateJsonSerializer.class)
    @JsonDeserialize(converter = IntDateJsonDeserializer.class)
    private LocalDate data;

    @NotNull
    @Min(0)
    @Column
    private BigDecimal valor;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

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
