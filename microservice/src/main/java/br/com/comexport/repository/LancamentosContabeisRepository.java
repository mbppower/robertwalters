package br.com.comexport.repository;

import br.com.comexport.domain.LancamentoContabil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LancamentosContabeisRepository extends JpaRepository<LancamentoContabil, Long>, LancamentosContabeisRepositoryCustom {

    @Query("FROM LancamentoContabil l WHERE l.contaContabil = :contaContabil")
    public Page<LancamentoContabil> findByContaContabil(@Param("contaContabil") Long contaContabil, Pageable page);

}
