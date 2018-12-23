package br.com.comexport;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import br.com.comexport.DTO.StatsDTO;
import br.com.comexport.domain.LancamentoContabil;
import br.com.comexport.repository.LancamentosContabeisRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class LancamentosContabeisUnitTests {

    @Autowired
    private LancamentosContabeisRepository lancamentosContabeisRepository;

    @Test
    public void insertLancamentoContabil() throws Exception {

        LancamentoContabil lancamento = new LancamentoContabil();
        lancamento.setContaContabil(418231L);
        lancamento.setData(LocalDate.now());
        lancamento.setValor(new BigDecimal("499.00"));

        lancamentosContabeisRepository.save(lancamento);

        //check for generated id
        assertNotNull(lancamento.getId());
    }

    @Test
    public void findLancamentoContabilByContaContabil() throws Exception {

        LancamentoContabil lancamento = new LancamentoContabil();
        lancamento.setContaContabil(2212312L);
        lancamento.setData(LocalDate.now());
        lancamento.setValor(new BigDecimal("499.00"));

        lancamentosContabeisRepository.save(lancamento);

        //check for generated id
        assertNotNull(lancamento.getId());

        Page<LancamentoContabil> findResults = lancamentosContabeisRepository.findByContaContabil(lancamento.getContaContabil(), Pageable.unpaged());

        //check for results
        assertEquals(1, findResults.getTotalElements());
    }

    @Test
    public void getStats() throws Exception {

        final BigDecimal minValue = new BigDecimal("100.00");
        final BigDecimal maxValue = new BigDecimal("150.50");
        final BigDecimal soma = maxValue.add(minValue);
        final Double avg = soma.doubleValue() / 2;

        //lacamento 1
        LancamentoContabil lancamento1 = new LancamentoContabil();
        lancamento1.setContaContabil(1111111L);
        lancamento1.setData(LocalDate.now());
        lancamento1.setValor(maxValue);

        lancamentosContabeisRepository.save(lancamento1);

        //lacamento 2
        LancamentoContabil lancamento2 = new LancamentoContabil();
        lancamento2.setContaContabil(2222222L);
        lancamento2.setData(LocalDate.now());
        lancamento2.setValor(minValue);

        lancamentosContabeisRepository.save(lancamento2);

        StatsDTO stats = lancamentosContabeisRepository.getStats();

        //check if we have a result
        assertNotNull(stats);

        //check if we have a result
        assertEquals(2, stats.getQtde());

        //check min
        assertEquals(minValue, stats.getMin());

        //check max
        assertEquals(maxValue, stats.getMax());

        //check soma
        assertEquals(soma, stats.getSoma());

        //check media
        assertEquals(avg, stats.getMedia());
    }

    @Test
    public void getStatsByContaContabil() throws Exception {

        LancamentoContabil lancamento = new LancamentoContabil();
        lancamento.setContaContabil(54645L);
        lancamento.setData(LocalDate.now());
        lancamento.setValor(new BigDecimal("99.99"));

        lancamentosContabeisRepository.save(lancamento);

        //check for generated id
        assertNotNull(lancamento.getId());

        StatsDTO stats = lancamentosContabeisRepository.getStatsByContaContabil(lancamento.getContaContabil());

        //check for results
        assertNotNull(stats);
    }

    @Test
    public void getLancamentoContabilByContaContabil() throws Exception {

        final Long contaContabil = 54645L;

        LancamentoContabil lancamento1 = new LancamentoContabil();
        lancamento1.setContaContabil(contaContabil);
        lancamento1.setData(LocalDate.now());
        lancamento1.setValor(new BigDecimal("99.10"));

        lancamentosContabeisRepository.save(lancamento1);

        LancamentoContabil lancamento2 = new LancamentoContabil();
        lancamento2.setContaContabil(contaContabil);
        lancamento2.setData(LocalDate.now());
        lancamento2.setValor(new BigDecimal("10.99"));

        lancamentosContabeisRepository.save(lancamento2);

        //check for generated id
        assertNotNull(lancamento1.getId());
        assertNotNull(lancamento2.getId());

        Page<LancamentoContabil> findResults = lancamentosContabeisRepository.findByContaContabil(contaContabil, Pageable.unpaged());

        //check for results
        assertNotNull(findResults);

        //check for count
        assertEquals(2, findResults.getTotalElements());
    }
}
