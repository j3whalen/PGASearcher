package sample;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.fxml.Initializable;
import javafx.beans.value.ChangeListener;



import java.beans.EventHandler;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;


public class Controller {
    @FXML
    private TextField searchbox;
    @FXML
    private TextArea Screen;

    public void Click(ActionEvent event) throws SQLException, ClassNotFoundException {
        ExecuteSQL();
    }

    public  void ExecuteSQL() throws SQLException, ClassNotFoundException {
       Class.forName( "org.sqlite.JDBC" );
        Screen.clear();
        Screen.setPrefRowCount(10);
        Screen.setPrefColumnCount(20);
        Screen.setWrapText(true);
        Screen.setStyle("-fx-font-family: monospace");
        Screen.appendText("First\t\t"+"Last\t\t"+"AVG\t\t"+"ACC\t\t"+"Rank\t\t"+"Perc\t\t"+"1Putt\t\t"+"Saves\t\t"+"SavePerc\t\t\n");
        Screen.appendText("______________________________________________________________________________________________________________________________________\n");
        Connection connection = DriverManager.getConnection("jdbc:sqlite:C:/Users/Josh/Documents/Comp_Sci/Spring 2017/Intro To Databases/pga.db");
        String sql = "SELECT FName as First, LName as last, round(AvgDrvDistance,2) as AverageDrive, round(DrvAcc,2) as DriverAccuracy, GIRRank, round(GIRPerc,2) as GIRPerc, OnePutts, Saves, round(SavePerc,2) as SavePerc FROM Player join Statistics using(PID)"
                + " Where FName like(\"" + searchbox.getText().toString()+
                "%\")";
//        or LName like (\"R%\")";
        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet res = stmt.executeQuery();
        while(res.next())
        {
            Screen.appendText( res.getString("First")+"\t\t"+res.getString("last")+"\t\t"+ res.getString("AverageDrive") +"\t\t"+ res.getString("DriverAccuracy") +  "\t\t"+ res.getString("GIRRank") +"\t\t"+ res.getString("GIRPerc") +"\t\t"+ res.getString("OnePutts") +"\t\t"+ res.getString("Saves") +"\t\t"+ res.getString("SavePerc") +"\n");
        }

    }
}
