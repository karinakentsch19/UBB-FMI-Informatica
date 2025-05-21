package pizzashop.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mockito;
import pizzashop.model.MenuDataModel;
import pizzashop.repository.MenuRepository;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class PizzaServiceFullIntegrationTest {
    private static MenuRepository menuRepository;

    private static PizzaService pizzaService;

    private static File tempFile;

    private static MenuDataModel validMenuItem;
    private static MenuDataModel invalidMenuItem;
    @BeforeAll
    static void setUp(@TempDir Path tempDir) throws IOException {
        tempFile = tempDir.resolve("menuItems.txt").toFile();
        tempFile.createNewFile();
        menuRepository = new MenuRepository(tempFile.getAbsolutePath());
        pizzaService = new PizzaService(menuRepository, null);
        invalidMenuItem = new MenuDataModel("1", 1, 12.2);
        validMenuItem = new MenuDataModel("a", 1, 12.2);
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
        write(invalidMenuItem);
        assertEquals(pizzaService.getMenuData().size(), 0);
    }

    @Test
    void validTest() {
        write(validMenuItem);
        assertEquals(pizzaService.getMenuData().size(), 1);
    }
}