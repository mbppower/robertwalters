package br.com.comexport.repository;

import br.com.comexport.DTO.StatsDTO;
import br.com.comexport.domain.LancamentoContabil;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public class LancamentosContabeisRepositoryImpl implements LancamentosContabeisRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    public LancamentosContabeisRepositoryImpl() {
    }

    public LancamentosContabeisRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Finds a LancamentoContabil by id
     *
     * @param id
     * @return Optional
     */
    public Optional<LancamentoContabil> findById(UUID id) {
        try {
            LancamentoContabil lancamento = entityManager
                    .createQuery("FROM LancamentoContabil l WHERE l.id = :id", LancamentoContabil.class)
                    .setParameter("id", id)
                    .getSingleResult();
            return Optional.of(lancamento);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    /**
     * Get general statistics
     *
     * @return StatsDTO
     */
    public StatsDTO getStats() {

        Object[] result = entityManager
                .createQuery("SELECT " +
                        "COALESCE(SUM(l.valor), 0) AS soma, " +
                        "COALESCE(MIN(l.valor), 0) AS min, " +
                        "COALESCE(MAX(l.valor), 0) AS max, " +
                        "COALESCE(AVG(l.valor), 0) AS media, " +
                        "COUNT(*) AS qtde " +
                        "FROM LancamentoContabil l", Object[].class)
                .getSingleResult();

        BigDecimal soma = (BigDecimal) result[0];
        BigDecimal min = (BigDecimal) result[1];
        BigDecimal max = (BigDecimal) result[2];
        Double media = (Double) result[3];
        Long qtde = ((Long) result[4]);

        return new StatsDTO(soma, min, max, media, qtde);
    }

    /**
     * Get statistics for the provided account
     *
     * @param contaContabil
     * @return StatsDTO
     */
    public StatsDTO getStatsByContaContabil(Long contaContabil) {

        Object[] result = entityManager
                .createQuery("SELECT " +
                        "COALESCE(SUM(l.valor), 0) AS soma, " +
                        "COALESCE(MIN(l.valor), 0) AS min, " +
                        "COALESCE(MAX(l.valor), 0) AS max, " +
                        "COALESCE(AVG(l.valor), 0) AS media, " +
                        "COUNT(*) AS qtde " +
                        "FROM LancamentoContabil l " +
                        "WHERE l.contaContabil = :contaContabil", Object[].class)
                .setParameter("contaContabil", contaContabil)
                .getSingleResult();

        BigDecimal soma = (BigDecimal) result[0];
        BigDecimal min = (BigDecimal) result[1];
        BigDecimal max = (BigDecimal) result[2];
        Double media = (Double) result[3];
        Long qtde = ((Long) result[4]);

        return new StatsDTO(soma, min, max, media, qtde);
    }
}
