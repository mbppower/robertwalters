package br.com.comexport.endpoints;

import br.com.comexport.DTO.*;
import br.com.comexport.domain.LancamentoContabil;
import br.com.comexport.repository.LancamentosContabeisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/lancamentos-contabeis")
public class LancamentosContabeisEndpoint {

    protected Logger logger = Logger.getLogger(LancamentosContabeisEndpoint.class.getName());

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    private LancamentosContabeisRepository lancamentosContabeisRepository;

    @RequestMapping("")
    public Page<LancamentoContabil> lancamentosContabeis(@RequestParam(required = false, name = "contaContabil") Long contaContabil, Pageable pageable) {
        if (contaContabil != null) {
            return lancamentosContabeisRepository.findByContaContabil(contaContabil, pageable);
        } else {
            return lancamentosContabeisRepository.findAll(pageable);
        }
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Object> postLancamentosContabeis(@Valid @RequestBody LancamentoContabilDTO lancamentoContabilDTO, BindingResult bindingResult) {

        //check for validation errors
        if (bindingResult.hasErrors()) {
            LancamentoContabilErrorDTO body = new LancamentoContabilErrorDTO();
            body.setMessage(applicationContext.getMessage("lancamentosContabeis.error", null, Locale.getDefault()));

            List<ValidationErrorDTO> errors = new ArrayList<>();

            for (FieldError e : bindingResult.getFieldErrors()) {
                ValidationErrorDTO error = new ValidationErrorDTO();
                error.setField(e.getField());
                error.setMessage(e.getDefaultMessage());
                errors.add(error);
            }

            body.setErrors(errors);

            //if we got errors we send a bad request containing the invalid fields
            return ResponseEntity.badRequest().body(body);
        } else {
            try {
                //create entity for insertion
                LancamentoContabil lancamento = new LancamentoContabil();
                lancamento.setContaContabil(lancamentoContabilDTO.getContaContabil());
                lancamento.setData(lancamentoContabilDTO.getData());
                lancamento.setValor(lancamentoContabilDTO.getValor());

                //persist entity
                lancamento = lancamentosContabeisRepository.save(lancamento);

                //set the success response
                LancamentoContabilSuccessDTO body = new LancamentoContabilSuccessDTO();
                body.setId(lancamento.getId());

                return ResponseEntity.status(HttpStatus.CREATED).body(body);
            } catch (Exception e) {
                LancamentoContabilErrorDTO body = new LancamentoContabilErrorDTO();
                body.setMessage(applicationContext.getMessage("common.unexpectedError", null, Locale.getDefault()));

                logger.log(Level.SEVERE, "Error inserting LancamentoContabil", e);

                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
            }
        }
    }

    @RequestMapping("/{id}")
    public ResponseEntity<LancamentoContabil> getLancamentoContabil(@PathVariable UUID id) {
        Optional<LancamentoContabil> lancamento = lancamentosContabeisRepository.findById(id);
        if (lancamento.isPresent()) {
            return ResponseEntity.ok(lancamento.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/_stats")
    public StatsDTO lancamentosContabeis(@RequestParam(required = false, name = "contaContabil") Long contaContabil) {
        if (contaContabil != null) {
            return lancamentosContabeisRepository.getStatsByContaContabil(contaContabil);
        } else {
            return lancamentosContabeisRepository.getStats();
        }
    }
}
