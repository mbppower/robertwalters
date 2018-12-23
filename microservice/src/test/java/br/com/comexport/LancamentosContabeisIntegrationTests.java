package br.com.comexport;

import br.com.comexport.DTO.LancamentoContabilDTO;
import br.com.comexport.DTO.LancamentoContabilErrorDTO;
import br.com.comexport.DTO.LancamentoContabilSuccessDTO;
import br.com.comexport.DTO.StatsDTO;
import br.com.comexport.domain.LancamentoContabil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class LancamentosContabeisIntegrationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private ResponseEntity<LancamentoContabilSuccessDTO> insertContaContabil(LancamentoContabilDTO payload) {

        return restTemplate
                .postForEntity("http://localhost:" + this.port + "/lancamentos-contabeis/", payload, LancamentoContabilSuccessDTO.class);
    }

    @Test
    public void insertValidLancamentoContabil() throws Exception {

        LancamentoContabilDTO payload = new LancamentoContabilDTO();
        payload.setContaContabil(418231L);
        payload.setData(LocalDate.now());
        payload.setValor(new BigDecimal("499.00"));

        ResponseEntity<LancamentoContabilSuccessDTO> entity = insertContaContabil(payload);

        //check request completed
        assertEquals(HttpStatus.CREATED, entity.getStatusCode());

        //check for generated id
        assertNotNull(entity.getBody().getId());
    }

    @Test
    public void insertInvalidLancamentoContabil() throws Exception {

        //invalid payload
        LancamentoContabilDTO payload = new LancamentoContabilDTO();

        ResponseEntity<LancamentoContabilErrorDTO> entity = restTemplate
                .postForEntity("http://localhost:" + this.port + "/lancamentos-contabeis/", payload, LancamentoContabilErrorDTO.class);

        //check request
        assertEquals(HttpStatus.BAD_REQUEST, entity.getStatusCode());

        //check for error message
        assertNotNull(entity.getBody().getMessage());

        //check for returned errors
        assertNotNull(entity.getBody().getErrors());
    }

    @Test
    public void findLancamentoContabilByContaContabil() throws Exception {

        //insert
        LancamentoContabilDTO payload = new LancamentoContabilDTO();
        payload.setContaContabil(12312L);
        payload.setData(LocalDate.now());
        payload.setValor(new BigDecimal("1299.00"));

        ResponseEntity<LancamentoContabilSuccessDTO> inserted = insertContaContabil(payload);

        //check request completed
        assertEquals(HttpStatus.CREATED, inserted.getStatusCode());

        ResponseEntity<String> entity = restTemplate
                .getForEntity("http://localhost:" + this.port + "/lancamentos-contabeis/?contaContabil=" + payload.getContaContabil(), String.class);

        assertEquals(HttpStatus.OK, entity.getStatusCode());
    }

    @Test
    public void findLancamentoContabilById() throws Exception {

        //insert
        LancamentoContabilDTO payload = new LancamentoContabilDTO();
        payload.setContaContabil(198L);
        payload.setData(LocalDate.now());
        payload.setValor(new BigDecimal("36695.00"));

        ResponseEntity<LancamentoContabilSuccessDTO> inserted = insertContaContabil(payload);

        //check request completed
        assertEquals(HttpStatus.CREATED, inserted.getStatusCode());

        ResponseEntity<LancamentoContabil> entity = restTemplate
                .getForEntity("http://localhost:" + this.port + "/lancamentos-contabeis/" + inserted.getBody().getId(), LancamentoContabil.class);

        assertEquals(HttpStatus.OK, entity.getStatusCode());
    }

    @Test
    public void getStats() throws Exception {

        ResponseEntity<StatsDTO> entity = restTemplate
                .getForEntity("http://localhost:" + this.port + "/lancamentos-contabeis/_stats", StatsDTO.class);

        assertEquals(HttpStatus.OK, entity.getStatusCode());
    }

    @Test
    public void getStatsByContaContabil() throws Exception {

        //insert
        LancamentoContabilDTO payload = new LancamentoContabilDTO();
        payload.setContaContabil(16554L);
        payload.setData(LocalDate.now());
        payload.setValor(new BigDecimal("199.00"));

        ResponseEntity<LancamentoContabilSuccessDTO> inserted = insertContaContabil(payload);

        //check request completed
        assertEquals(HttpStatus.CREATED, inserted.getStatusCode());

        ResponseEntity<StatsDTO> entity = restTemplate
                .getForEntity("http://localhost:" + this.port + "/lancamentos-contabeis/_stats?contaContabil=" + payload.getContaContabil(), StatsDTO.class);

        assertEquals(HttpStatus.OK, entity.getStatusCode());
    }

}
