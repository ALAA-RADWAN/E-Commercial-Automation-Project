package Utilities;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class DataUtil {
    private static final String Test_Data_Path = "src/test/resources/TestData/";
    //TODO: Reading Data from Json File
    public static String getJsonData(String filename, String field) {
        try {
            FileReader reader = new FileReader(Test_Data_Path + filename + ".json");
            JsonElement jsonElement = JsonParser.parseReader(reader);
            return jsonElement.getAsJsonObject().get(field).getAsString();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    //TODO: Reading Data from properties File
    public static String getPropertyValue(String filename, String key) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(Test_Data_Path+ filename + ".properties"));
        return properties.getProperty(key);
    }
}
