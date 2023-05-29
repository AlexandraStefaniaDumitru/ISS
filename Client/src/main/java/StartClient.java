import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.ServiceInterface;
import view.AvailableBooksView;
import view.LoginView;
import view.ManageBooksView;

public class StartClient extends Application {

    @Override
    public void start(Stage primaryStage)  {
        try {
            ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:spring-client.xml");
            ServiceInterface server = (ServiceInterface) factory.getBean("LibraryService");
            System.out.println("Connected to server");

            AvailableBooksView availableBooksView = new AvailableBooksView(server);
            ManageBooksView manageBooksView = new ManageBooksView(server);
            LoginView loginView = new LoginView(primaryStage, server, availableBooksView.getAvailableBooksController(),
                    manageBooksView.getManageBooksController());
        } catch (Exception exception) {
            System.err.println("Client initialization exception: " + exception);
            exception.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}