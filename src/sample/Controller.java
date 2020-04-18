package sample;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.json.JSONObject;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField city;

    @FXML
    private Button getData;

    @FXML
    private Text info;

    @FXML
    private Text temp;

    @FXML
    private Text temp_max;

    @FXML
    private Text temp_min;

    @FXML
    private Text pressure;

    @FXML
    void initialize() {
        getData.setOnAction(event -> {
            String getUserCity = city.getText().trim();

            if (!getUserCity.equals("")) {
                String output = getUrlContent("http://api.openweathermap.org/data/2.5/weather?q=" + getUserCity + "&appid=5ea403f799b413db56a43f9fb556b7d6&units=metric");

                if (!output.isEmpty()) {
                    JSONObject jsonObj = new JSONObject(output);

                    info.setText("Weather in " + getUserCity + ": ");
                    temp.setText("Temperature: " + jsonObj.getJSONObject("main").getDouble("temp"));
                    temp_max.setText("Max: " + jsonObj.getJSONObject("main").getDouble("temp_max"));
                    temp_min.setText("Min: " + jsonObj.getJSONObject("main").getDouble("temp_min"));
                    pressure.setText("Pressure: " + jsonObj.getJSONObject("main").getDouble("pressure"));
                }
            }
        });
    }

    private String getUrlContent(String urlAddress) {
        StringBuffer content = new StringBuffer();

        try {
            URL url = new URL(urlAddress);

            URLConnection urlConnection = url.openConnection();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;

            while((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }

            bufferedReader.close();
        }
        catch (Exception e) {
            JFrame frame = new JFrame("Wrong input");

            JOptionPane.showMessageDialog(frame,"The city " + "\"" + city.getText().trim() + "\"" + " was not found!");

            city.setText("");
            city.setPromptText("Enter the City");
            info.setText("Information");
            temp.setText("Temperature: ");
            temp_max.setText("Max: ");
            temp_min.setText("Min: ");
            pressure.setText("Pressure: ");
        }

        return content.toString();
    }
}
