package com.bank.account.controllers;

import com.bank.account.dto.AccountDetailsDTO;
import com.bank.account.exceptions.AccountDetailsNotCreatedException;
import com.bank.account.services.AccountDetailsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountDetailsRestControllerTest {
    @Mock
    AccountDetailsService accountDetailsService;

    @Mock
    ModelMapper modelMapper;

    @InjectMocks
    @Spy
    AccountDetailsRestController accountDetailsRestController;


    @Test
    void test() {
        AccountDetailsDTO dto = getDto();
        BindingResult bind = getBind();

        StringBuilder builder = new StringBuilder();

        Iterator<FieldError> iterator = getMock();
        doReturn(true).when(bind).hasErrors();
        doReturn(builder).when(accountDetailsRestController).getErrorMsg();
        doReturn(true,false).when(iterator).hasNext();
//        when(iterator.hasNext()).thenReturn(true).thenReturn(false);
//        FieldError fieldError = new FieldError("objectName", "0", "defaultMessage");
        FieldError fieldError = mock(FieldError.class);
        doReturn(fieldError).when(iterator).next();
        List<FieldError> errors = getErrors();
        doReturn(iterator).when(errors).iterator();
        doReturn(errors).when(bind).getFieldErrors();
        String errorField = "error field";
        for (FieldError error : errors) {
            doThrow(new RuntimeException()).when(error).getField();
            assertThrows(RuntimeException.class, () ->
            builder.append(error.getField())
                    .append(" - ").append(error.getDefaultMessage())
                    .append(";").append(" by Oleg"));
        }
        System.out.println(builder);

//        accountDetailsRestController.create(dto, bind);
        assertThrows(AccountDetailsNotCreatedException.class,() ->accountDetailsRestController.create(dto, bind));
    }




    private AccountDetailsDTO getDto() {
        AccountDetailsDTO accountDetailsDTO = new AccountDetailsDTO();
        accountDetailsDTO.setAccountNumber(1);
        accountDetailsDTO.setBankDetailsId(1);
        accountDetailsDTO.setMoney(BigDecimal.valueOf(1));
        accountDetailsDTO.setProfileId(1);
        accountDetailsDTO.setNegativeBalance(false);
        accountDetailsDTO.setPassportId(1);
        return accountDetailsDTO;
    }

    private BindingResult getBind() {
        return Mockito.mock(BindingResult.class);
    }

//    private List<FieldError> getErrors() {
//        List<FieldError> errors = new ArrayList<>();
//        errors.add(new FieldError("objectName", "0", "defaultMessage"));
//        errors.add(new FieldError("objectName2", "1", "defaultMessage2"));
//        errors.add(new FieldError("objectName3", "2", "defaultMessage3"));
//        return errors;
//    }
    @SuppressWarnings("unchecked")
    private List<FieldError> getErrors() {
        return mock(List.class);
    }

    @SuppressWarnings("unchecked")
    private Iterator<FieldError> getMock() {
        return mock(Iterator.class);
    }
}