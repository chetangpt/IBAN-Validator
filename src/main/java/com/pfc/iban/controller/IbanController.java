package com.pfc.iban.controller;

import com.pfc.iban.service.IbanValidatorService;
import com.pfc.iban.vo.IbanValidatorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/iban/")
public class IbanController {

     private static final Logger log = LoggerFactory.getLogger(IbanController.class);

    @Autowired
    IbanValidatorService ibanValidatorService;

    @GetMapping(produces = "application/json", value = "/{ibanId}/validate")
    public ResponseEntity<IbanValidatorResponse> validateIbanId(@PathVariable String ibanId) {
        IbanValidatorResponse ibanValidatorResponse = ibanValidatorService.validate(ibanId);
        log.debug("response: ibanValidatorResponse(): {}", ibanValidatorResponse);
        return ResponseEntity.ok().body(ibanValidatorResponse);
    }


}
