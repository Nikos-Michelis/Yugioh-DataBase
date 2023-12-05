import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import model.*;
import utils.CsvWriter;
import utils.FetchApiData;
import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static void writeToFile() throws IOException {
        String filePath = "data/info.txt";
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME) + "\n");
        }
    }
    public static void main(String[] args) throws Exception {
        FetchApiData fetchApiData = new FetchApiData();
        CsvWriter csvWriter = new CsvWriter();
        CardDAO dao = new CardDAO();
        try {
            // Yugioh API Endpoint
       	    String apiUrl= "https://db.ygoprodeck.com/api/v7/cardinfo.php?misc=yes";  

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(apiUrl)).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                System.out.println("Status: " + response.statusCode());
                // Parse JSON response using Gson
                Gson gson = new Gson();
                JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);
                JsonArray cardsArray = jsonObject.getAsJsonArray("data");
                List<Card> cardList = new ArrayList<>();
                List<CardSetInfo> cardSetInfoList = new ArrayList<>();
                List<CardPriceInfo> cardPriceInfoList = new ArrayList<>();
                // Loop through each card in the JSON array
                for (int i = 0; i < cardsArray.size(); i++) {
                    JsonObject cardNode = cardsArray.get(i).getAsJsonObject();
                    // cards
                    cardList.addAll(fetchApiData.fetchCards(cardNode));
                    // card_sets
                    cardSetInfoList.addAll(fetchApiData.fetchCardSets(cardNode));
                    // card_prices
                    cardPriceInfoList.addAll(fetchApiData.fetchCardPrices(cardNode));
                }
                // Insert data into database(Comment out all of this if you have not created the database.)
                dao.insertCard(cardList);
                dao.insertCardSets(cardSetInfoList);
                //dao.insertCardPrices(cardPriceInfoList);
                // Insert data into CSV files
                csvWriter.writeDataToCSV(cardSetInfoList,"data/card_sets.csv");
                csvWriter.writeDataToCSV(cardPriceInfoList,"data/card_price.csv");
                csvWriter.writeDataToCSV(cardList,"data/cards.csv");
                writeToFile();
            } else {
                throw new Exception("Error: Unable to fetch card information. HTTP Status Code: " + response.statusCode());
            }
        } catch (IOException e) {
            System.err.println("IO Error while sending or receiving data: " + e.getMessage());
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            System.err.println("Error parsing JSON: " + e.getMessage());
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}






