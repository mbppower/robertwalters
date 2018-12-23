package br.com.comexport.repository;

import br.com.comexport.DTO.StatsDTO;
import br.com.comexport.domain.LancamentoContabil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface LancamentosContabeisRepositoryCustom  {

    public StatsDTO getStats();

    public StatsDTO getStatsByContaContabil(Long contaContabil);

    public Optional<LancamentoContabil> findById(UUID id);

}
