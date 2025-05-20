package ro.iss.biblioteca;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ro.iss.biblioteca.Business.IService;
import ro.iss.biblioteca.Business.Service;
import ro.iss.biblioteca.Controller.LoginWindow;
import ro.iss.biblioteca.Repository.CarteRepository;
import ro.iss.biblioteca.Repository.CosRepository;
import ro.iss.biblioteca.Repository.ImprumutRepository;
import ro.iss.biblioteca.Repository.UtilizatorRepository;
import ro.iss.biblioteca.Utils.JdbcUtils;
import ro.iss.biblioteca.Validation.ValidatorUtilizator;

import javax.xml.stream.XMLEventWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Properties properties = new Properties();
        try {
            properties.load(new FileReader("bd.config"));

            JdbcUtils jdbcUtils = new JdbcUtils(properties);
            ValidatorUtilizator validatorUtilizator = new ValidatorUtilizator();
            UtilizatorRepository utilizatorRepository = new UtilizatorRepository(jdbcUtils, validatorUtilizator);
            CarteRepository carteRepository = new CarteRepository(jdbcUtils);
            CosRepository cosRepository = new CosRepository(jdbcUtils);
            ImprumutRepository imprumutRepository = new ImprumutRepository(jdbcUtils);
            Service service = new Service(utilizatorRepository, carteRepository, cosRepository, imprumutRepository);

            int numberOfWindows = 2;
            while (numberOfWindows != 0) {
                Stage stage1 = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Views/loginwindow.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                stage1.setScene(scene);
                stage1.setTitle("Biblioteca");

                LoginWindow loginWindow = fxmlLoader.getController();
                loginWindow.LoginWindow(stage1, service);

                stage1.show();
                numberOfWindows--;
            }
        } catch (IOException e) {
            System.out.println("Cannot find bd.config" + e);
        }

    }

    public static void main(String[] args) {
        launch();
    }
}