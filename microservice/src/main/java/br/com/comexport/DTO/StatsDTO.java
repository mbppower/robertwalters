package br.com.comexport.DTO;

import java.math.BigDecimal;

public class StatsDTO {
    private BigDecimal soma;
    private BigDecimal min;
    private BigDecimal max;
    private Double media;
    private long qtde;

    public StatsDTO(BigDecimal soma, BigDecimal min, BigDecimal max, Double media, long qtde) {
        this.soma = soma;
        this.min = min;
        this.max = max;
        this.media = media;
        this.qtde = qtde;
    }

    public BigDecimal getSoma() {
        return soma;
    }

    public void setSoma(BigDecimal soma) {
        this.soma = soma;
    }

    public BigDecimal getMin() {
        return min;
    }

    public void setMin(BigDecimal min) {
        this.min = min;
    }

    public BigDecimal getMax() {
        return max;
    }

    public void setMax(BigDecimal max) {
        this.max = max;
    }

    public Double getMedia() {
        return media;
    }

    public void setMedia(Double media) {
        this.media = media;
    }

    public long getQtde() {
        return qtde;
    }

    public void setQtde(long qtde) {
        this.qtde = qtde;
    }
}
