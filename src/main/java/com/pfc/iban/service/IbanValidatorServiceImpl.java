package com.pfc.iban.service;

import com.pfc.iban.exception.BadRequestException;
import com.pfc.iban.vo.IbanValidatorResponse;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

@Service
public class IbanValidatorServiceImpl implements IbanValidatorService {

    public static Map<String, Integer> countryIBANNumberSizeMap;
    public static final int IBANNUMBER_MIN_SIZE = 15;
    public static final int IBANNUMBER_MAX_SIZE = 34;
    public static final BigInteger IBANNUMBER_MAGIC_NUMBER = new BigInteger("97");

    static {
        countryIBANNumberSizeMap = new HashMap<>();
        countryIBANNumberSizeMap.put("AT", 20);
        countryIBANNumberSizeMap.put("BE", 16);
        countryIBANNumberSizeMap.put("BG", 22);
        countryIBANNumberSizeMap.put("HR", 21);
        countryIBANNumberSizeMap.put("CY", 28);
        countryIBANNumberSizeMap.put("CZ", 24);
        countryIBANNumberSizeMap.put("DK", 18);
        countryIBANNumberSizeMap.put("EE", 20);
        countryIBANNumberSizeMap.put("FI", 18);
        countryIBANNumberSizeMap.put("FR", 27);

        countryIBANNumberSizeMap.put("DE", 22);
        countryIBANNumberSizeMap.put("EL", 27);
        countryIBANNumberSizeMap.put("HU", 28);
        countryIBANNumberSizeMap.put("IE", 22);
        countryIBANNumberSizeMap.put("IT", 27);
        countryIBANNumberSizeMap.put("LV", 21);
        countryIBANNumberSizeMap.put("LT", 20);
        countryIBANNumberSizeMap.put("LU", 20);
        countryIBANNumberSizeMap.put("SE", 24);
        countryIBANNumberSizeMap.put("NL", 18);

    }

    @Override
    public IbanValidatorResponse validate(String ibanId) {
        boolean isValid;
        // remove spaces and make them uppercase
        ibanId = ibanId.replaceAll("\\s", "");
        ibanId = ibanId.toUpperCase();

        IbanValidatorResponse ibanValidatorResponse = new IbanValidatorResponse();
        isValid = validateIban(ibanId);

        if(isValid){
            ibanValidatorResponse.setValid(true);
            ibanValidatorResponse.setMessage("IBAN Number is valid");
        }else{
            ibanValidatorResponse.setValid(false);
            ibanValidatorResponse.setMessage("IBAN Number is invalid");
        }

        return ibanValidatorResponse;

    }

    private boolean validateIban(String ibanId) {

        if (ibanId.length() < IBANNUMBER_MIN_SIZE || ibanId.length() > IBANNUMBER_MAX_SIZE) {
            return false;
        }

        if(countryIBANNumberSizeMap.get(ibanId.substring(0, 2)) == null){
            throw new BadRequestException("This country is not supported by the validator");
        }

        if (ibanId.length() != countryIBANNumberSizeMap.get(ibanId.substring(0, 2))) {
            return false;
        }

        // move first four letters to the end
        ibanId = ibanId.substring(4) + ibanId.substring(0, 4);
        StringBuilder numericAccountNumber = new StringBuilder();
        for (int i = 0; i < ibanId.length(); i++) {
            int numericValue = Character.getNumericValue(ibanId.charAt(i));
            if (numericValue < 0 || numericValue > 35) {
                return false;
            }
            numericAccountNumber.append(Character.getNumericValue(ibanId.charAt(i)));
        }

        BigInteger ibanNumber = new BigInteger(numericAccountNumber.toString());
        return ibanNumber.mod(IBANNUMBER_MAGIC_NUMBER).intValue() == 1;
    }

}
