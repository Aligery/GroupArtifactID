package com.company;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class Controller {
    @FXML
    private TextArea Rslt;

    @FXML
    private Text CurrentCourse;
    @FXML
    private Button Recalculate;

    @FXML
    private DatePicker DatePick;
    @FXML
    private TextField Summ;
    public void RecalculateOnAction(ActionEvent actionEvent)
    {

        //коды ошибок
        String Error1 = new String("\"Число не может быть меньше 0 или не введена дата\"");
        String Error2 = new String("Введите число долларов");
        String Error3 = new String("Введите дату");

        System.out.println("button pressed");
//        System.out.println(DatePick.getValue().getYear());
//        System.out.println(DatePick.getValue().getDayOfMonth());
//        System.out.println(DatePick.getValue().getMonthValue());
        Summ.getText();
        Rslt.setText(Summ.getText());
        try { //проверяем правильность данных.
            Double.valueOf(Summ.getText());
            Rslt.setText("Это число: " + Double.valueOf(Summ.getText()));
            if (Double.valueOf(Summ.getText())>=0)
            {
                ValueOfDollar TodayCost = new ValueOfDollar();
                Double ValueToday = TodayCost.getCurrentValue();
                CurrentCourse.setText(ValueToday.toString());
                Double ValueEarly = TodayCost.getEarlyValue (DatePick.getValue().getYear(), DatePick.getValue().getMonthValue(), DatePick.getValue().getDayOfMonth());
                Double FinishResult = 0.995*ValueToday - ValueEarly;
                if (ValueEarly==0)
                {
                    Rslt.setText("Ошибка общения с сервером, кури логи");
                } else {
                    Rslt.setText("Proofit with spread: " + FinishResult*Double.valueOf(Summ.getText()));
               }

            }
            else //
            {
                throw new Exception();
            }
            //если все ок, то только после этого записываем значение которое будем обрабатывать и открываем соединение с API
        } catch(NumberFormatException e){
            Rslt.setText(Error2);
        } catch (Exception e1) {
            e1.printStackTrace();
            Rslt.setText(Error1);

        }
    }
}
