package pizzashop.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mockito;
import pizzashop.model.MenuDataModel;
import pizzashop.model.Payment;
import pizzashop.repository.MenuRepository;
import pizzashop.repository.PaymentRepository;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class PizzaServiceIntegrationTest {
    private static MenuRepository menuRepository;

    private static PizzaService pizzaService;

    private static File tempFile;

    @BeforeAll
    static void setUp(@TempDir Path tempDir) throws IOException {
        tempFile = tempDir.resolve("menuItems.txt").toFile();
        tempFile.createNewFile();
        menuRepository = new MenuRepository(tempFile.getAbsolutePath());
        pizzaService = new PizzaService(menuRepository, null);
    }

    private void write(MenuDataModel menuDataModel) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(tempFile));
            bw.write(menuDataModel.getMenuItem() + "," + menuDataModel.getPrice());
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void invalidTest() {
        MenuDataModel invalidMenuItem = mock(MenuDataModel.class);
        Mockito.when(invalidMenuItem.getMenuItem()).thenReturn("1");
        Mockito.when(invalidMenuItem.getPrice()).thenReturn(12.2);
        write(invalidMenuItem);
        assertEquals(pizzaService.getMenuData().size(), 0);
    }

    @Test
    void validTest() {
        MenuDataModel invalidMenuItem = mock(MenuDataModel.class);
        Mockito.when(invalidMenuItem.getMenuItem()).thenReturn("a");
        Mockito.when(invalidMenuItem.getPrice()).thenReturn(12.2);
        write(invalidMenuItem);
        assertEquals(pizzaService.getMenuData().size(), 1);
    }
}