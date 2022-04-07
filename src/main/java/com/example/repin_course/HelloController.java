package com.example.repin_course;

import com.example.repin_course.Model.QRcodeGenerateTask;
import com.example.repin_course.Model.QRcodeReadTaskFile;
import com.example.repin_course.Model.QRcodeReadTaskImage;
import com.google.zxing.EncodeHintType;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

public class HelloController implements Initializable {
    private FileChooser fileChooser = new FileChooser();
    private Stage stage;
   @FXML
    TextField urlInputField;
   @FXML
    ImageView qrCodeView;
   public void readQRBtnOn(ActionEvent e) throws ExecutionException, InterruptedException {
       Map<EncodeHintType,ErrorCorrectionLevel> hashMap =
               new HashMap<EncodeHintType,ErrorCorrectionLevel>();
       hashMap.put(EncodeHintType.ERROR_CORRECTION,ErrorCorrectionLevel.L);
       Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
       alert.setTitle("Открыть");
       alert.setHeaderText("Выберите файл для открытия");
       alert.setContentText("Открыть отображаемый QR код");
       ButtonType buttonType = alert.showAndWait().get();
       if (buttonType == ButtonType.OK){
           try {
               QRcodeReadTaskImage task = new QRcodeReadTaskImage(qrCodeView.getImage(),"UTF-8",hashMap);
               new Thread(task).start();
               Desktop.getDesktop().browse(new URI(task.get()));
           }catch (IOException ex) {
               Alert alertIOex = new Alert(Alert.AlertType.ERROR);
               alertIOex.setTitle("ОШИБКА");
               alertIOex.setHeaderText("Не удалось открыть браузер");
               alertIOex.setContentText("Попробуйте снова");
               if (alertIOex.showAndWait().get() == ButtonType.OK) alertIOex.close();
           } catch (URISyntaxException ex) {
               Alert alertUriEx = new Alert(Alert.AlertType.ERROR);
               alertUriEx.setTitle("ОШИБКА");
               alertUriEx.setHeaderText("QR код не содержит ссылку");
               alertUriEx.setContentText("Попробуйте снова");
               if (alertUriEx.showAndWait().get() == ButtonType.OK) alertUriEx.close();
           }catch (NullPointerException ex){
               Alert alertNullEx = new Alert(Alert.AlertType.ERROR);
               alertNullEx.setTitle("ОШИБКА");
               alertNullEx.setHeaderText("Никакое изображение заранее не введено");
               alertNullEx.setContentText("Попробуйте снова");
               if (alertNullEx.showAndWait().get() == ButtonType.OK) alertNullEx.close();
           }
       }
       else if (buttonType == ButtonType.CANCEL){
           try {
               String QRfilePath = fileChooser.showOpenDialog(stage).getAbsolutePath();
               QRcodeReadTaskFile task = new QRcodeReadTaskFile(QRfilePath,"UTF-8",hashMap);
               new Thread(task).start();
               String uri = task.get();
               Desktop.getDesktop().browse(new URI(uri));
               QRcodeGenerateTask task1 = new QRcodeGenerateTask(uri,"UTF-8",hashMap,200,200);
               new Thread(task1).start();
               qrCodeView.setImage(task1.get());
           } catch (IOException ex) {
               Alert alertIOex = new Alert(Alert.AlertType.ERROR);
               alertIOex.setTitle("ОШИБКА");
               alertIOex.setHeaderText("Не удалось открыть браузер");
               alertIOex.setContentText("Попробуйте снова");
               if (alertIOex.showAndWait().get() == ButtonType.OK) alertIOex.close();
           } catch (URISyntaxException ex) {
               Alert alertUriEx = new Alert(Alert.AlertType.ERROR);
               alertUriEx.setTitle("ОШИБКА");
               alertUriEx.setHeaderText("QR код не содержит ссылку");
               alertUriEx.setContentText("Попробуйте снова");
               if (alertUriEx.showAndWait().get() == ButtonType.OK) alertUriEx.close();
           }catch (NullPointerException ex){
               Alert alertNullEx = new Alert(Alert.AlertType.ERROR);
               alertNullEx.setTitle("ОШИБКА");
               alertNullEx.setHeaderText("Ничего не введено");
               alertNullEx.setContentText("Попробуйте снова");
               if (alertNullEx.showAndWait().get() == ButtonType.OK) alertNullEx.close();
           }
       }
   }
   public void generateQRBtnOn(ActionEvent e) throws ExecutionException, InterruptedException {
       String inputText = urlInputField.getText();
       try {
           URL check_URL = new URL(inputText);
           Map<EncodeHintType,ErrorCorrectionLevel> hashMap =
                   new HashMap<EncodeHintType,ErrorCorrectionLevel>();
           hashMap.put(EncodeHintType.ERROR_CORRECTION,
                   ErrorCorrectionLevel.L);
           QRcodeGenerateTask task = new QRcodeGenerateTask(
                   inputText,
                   "UTF-8",
                   hashMap,
                   200,200);
           new Thread(task).start();
           qrCodeView.setImage(task.get());
       } catch (MalformedURLException ex) {
           ex.printStackTrace();
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("ОШИБКА");
           alert.setHeaderText("Неверный формат ввода");
           alert.setContentText("Введите ссылку");
           if (alert.showAndWait().get() == ButtonType.OK) alert.close();
       }
   }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        urlInputField.setText("Введите ссылку");
        fileChooser.setTitle("Выберите QR-код");
        fileChooser.setInitialDirectory(new File("C:\\Users\\user\\Downloads"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Files","*.*"),
                new FileChooser.ExtensionFilter("JPG","*.jpg"),
                new FileChooser.ExtensionFilter("PNG","*.png"),
                new FileChooser.ExtensionFilter("SVG","*.svg"));
    }
}