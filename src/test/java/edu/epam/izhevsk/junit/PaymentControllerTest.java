package edu.epam.izhevsk.junit;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.mockito.AdditionalMatchers.gt;
import static org.mockito.Mockito.*;

public class PaymentControllerTest {
    @InjectMocks
    private PaymentController paymentController;
    @Mock
    private AccountService mockAccountService;
    @Mock
    private DepositService mockDepositService;

    @BeforeEach
    @Test
    void initTestPaymentControllerMock() throws InsufficientFundsException {
        MockitoAnnotations.initMocks(this);
        when(mockAccountService.isUserAuthenticated(100L)).thenReturn(true);
        when(mockDepositService.deposit(gt(100L), anyLong())).thenThrow(InsufficientFundsException.class);

    }

    @Test
    void throwInsufficientFundsExceptionWhenAmountIsLarge() {
        assertThrows(InsufficientFundsException.class, () -> paymentController.deposit(150L, 100L));
    }

    @Test
    void throwSecurityExceptionWhenUserIdIsNot100L() {
        assertThrows(SecurityException.class, () -> paymentController.deposit(50L, 10L));
    }

    @Test
    void getSuccessProcess() {
        assertDoesNotThrow(() -> paymentController.deposit(50L, 100L));
    }
}
