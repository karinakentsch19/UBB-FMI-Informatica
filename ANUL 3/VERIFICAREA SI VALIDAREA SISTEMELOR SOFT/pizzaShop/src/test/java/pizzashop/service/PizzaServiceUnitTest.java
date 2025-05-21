package pizzashop.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import pizzashop.model.MenuDataModel;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;
import pizzashop.repository.MenuRepository;
import pizzashop.repository.PaymentRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PizzaServiceUnitTest {
    @Mock
    PaymentRepository paymentRepository;

    @Mock
    MenuRepository menuRepository;

    @InjectMocks
    PizzaService pizzaService;

    @Test
    void getMenuDataTest() {
        MenuDataModel menuDataModel = new MenuDataModel("a", 1, 2.3);
        Mockito.when(menuRepository.getMenu()).thenReturn(Arrays.asList(menuDataModel));
        assertEquals(1, pizzaService.getMenuData().size());
        assertTrue(pizzaService.getMenuData().contains(menuDataModel));
    }

    @Test
    void paymentTest() {
        Payment payment = new Payment(1, PaymentType.CASH, 12.2);
        Mockito.when(paymentRepository.getAll()).thenReturn(Arrays.asList(payment));
        Mockito.doNothing().when(paymentRepository).add(Mockito.any(Payment.class));
        pizzaService.addPayment(payment.getTableNumber(), payment.getType(), payment.getAmount());
        assertEquals(pizzaService.getPayments().size(), 1);
        assertTrue(pizzaService.getPayments().contains(payment));
        assertEquals(pizzaService.getTotalAmount(PaymentType.CASH), payment.getAmount());
        assertEquals(pizzaService.getTotalAmount(PaymentType.CARD), 0);
    }
}
