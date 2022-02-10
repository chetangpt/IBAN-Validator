package com.pfc.iban.service;

import com.pfc.iban.vo.IbanValidatorResponse;

public interface IbanValidatorService {

    IbanValidatorResponse validate(String ibanId);
}
