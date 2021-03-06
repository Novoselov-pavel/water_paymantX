package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.dao.DBConnect;
import model.DBConnectFactory;

public class ConnectController implements IController{
    @FXML
    private TextField hostTextField;

    @FXML
    private TextField portTextField;

    @FXML
    private TextField dbNameTextField;

    @FXML
    private TextField userNameTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Button cancelСonnectButtonAction;

    @FXML
    private Button tryСonnectButtonAction;

    private UIFactory factory;

    public static String getFXMLPath() {
        return "../ConnectionForm.fxml";
    }

    /*
     *Регулярное выражение проверяет корректность ввода поля port,
     *  если в строке только цифы то вернёт true
     */
    private boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }

    /**
     * неправильно смешивать названия функций, функция init - инициализирует форм,
     * эта функция инициализирует коннект,
     */
    public boolean connectDB() {
        DBConnectFactory dbConnectFactory = DBConnectFactory.getFactory();
        dbConnectFactory.setHostName(hostTextField.getText());

        String port = portTextField.getText();
        if (isNumeric(port)) {
            dbConnectFactory.setPort(Integer.parseInt(port));
        } else {
            portTextField.setText("");
        }
        dbConnectFactory.setDbName(dbNameTextField.getText());
        dbConnectFactory.setUserName(userNameTextField.getText());
        dbConnectFactory.setPassword(passwordTextField.getText());
        DBConnect connect = DBConnectFactory.getConnect();
        return connect != null;
    }

    /**
     * И да, нам все еще нужна ссылка на фабрику форм, как без нее мы будем открывать новую форму
     *
     * @param factory
     */
    public void init(UIFactory factory) {
        this.factory = factory;
    }

    private void goToMainForm() {
        try {
            factory.getForm(UIForms.CONNECTION_FORM).setVisible(false);
            factory.getForm(UIForms.MAIN_FORM).setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void tryСonnectButtonAction() {
        if (connectDB()) {
            ///Тут добавим переход на новую форму
            goToMainForm();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка подключения к базе данных");
            alert.setContentText("Неверные параметры подключения");
            alert.show();
        }

    }

    @FXML
    private void cancelСonnectButtonAction() {
        if (connectDB()) {
            goToMainForm();
        } else {
            factory.primaryStage.close();
        }
    }
}
