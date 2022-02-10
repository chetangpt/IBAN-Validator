package com.pfc.iban.service;

import com.pfc.iban.exception.BadRequestException;
import com.pfc.iban.vo.IbanValidatorResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class IbanValidatorServiceTests {

    @Autowired
    IbanValidatorService ibanValidatorService;

    IbanValidatorResponse ibanValidatorResponse;
    String ibanId;

    @Test
    @DisplayName("Success test for valid iban id")
    public void validateIbanIDSuccessTest(){
        ibanId = "SE7280000810340009783242";
        ibanValidatorResponse = ibanValidatorService.validate(ibanId);
        assertThat(ibanValidatorResponse.isValid(),is(true));
    }

    @Test
    @DisplayName("Success test for a valid iban Id separated by spaces")
    public void validateIbanIdWithTrailingSpacesSuccessTest(){
        ibanId = "SE72 8000 0810 3400 0978 3242";
        ibanValidatorResponse = ibanValidatorService.validate(ibanId);
        assertThat(ibanValidatorResponse.isValid(),is(true));
    }

    @Test
    @DisplayName("Failure Test for a invalid ibanID with too many digits")
    public void validateIbanIdWithMoreLengthFailureTest(){
        ibanId = "RO09BCYP00000012345678900992RET465899";
        ibanValidatorResponse = ibanValidatorService.validate(ibanId);
        assertThat(ibanValidatorResponse.isValid(),is(false));
    }

    @Test
    @DisplayName("Exception thrown for a country not supported")
    public void validateIbanIDForCountryNotSupportedTest(){
        ibanId = "RO09BCYP0000001234567890";
       assertThrows(BadRequestException.class, () -> {ibanValidatorService.validate(ibanId);});
    }

    @Test
    @DisplayName("Invalid IBAN Id with mod by 97 is not 1")
    public void validateIbanIDForInaccurateAccountNumberFailureTest(){
        ibanId = "SE7280000810340009783248";
        ibanValidatorResponse = ibanValidatorService.validate(ibanId);
        assertThat(ibanValidatorResponse.isValid(),is(false));
    }

}
