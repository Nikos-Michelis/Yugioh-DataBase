package utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import model.Card;
import model.CardPriceInfo;
import model.CardSetInfo;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FetchApiData {

    public FetchApiData(){}
    public List<Card> fetchCards(JsonObject cardNode) throws Exception {
     
        List<Card> cardList = new ArrayList<>();
        int id = cardNode.has("id") ? cardNode.get("id").getAsInt() : 0;
        String name = cardNode.has("name") ? cardNode.get("name").getAsString() : "";
        String type = cardNode.has("type") ? cardNode.get("type").getAsString() : "";
        String frameType = cardNode.has("frameType") ? cardNode.get("frameType").getAsString() : "";
        String description = cardNode.has("desc") ? cardNode.get("desc").getAsString() : "";
        int atk = cardNode.has("atk") ? cardNode.get("atk").getAsInt() : 0;
        int def = cardNode.has("def") ? cardNode.get("def").getAsInt() : 0;
        int level = cardNode.has("level") ? cardNode.get("level").getAsInt() : 0;
        String race = cardNode.has("race") ? cardNode.get("race").getAsString() : "";
        String archetype = cardNode.has("archetype") ? cardNode.get("archetype").getAsString() : "";
        String attribute = cardNode.has("attribute") ? cardNode.get("attribute").getAsString() : "";
        int linkval = cardNode.has("linkval") ? cardNode.get("linkval").getAsInt() : 0;
        int scale = cardNode.has("scale") ? cardNode.get("scale").getAsInt() : 0;

        JsonArray miscInfoArray = cardNode.getAsJsonArray("misc_info");
        String konamiId = null;
        LocalDate tcgDate = null;
        LocalDate ocgDate = null;
        int hasEffect = 0;
        int totalViews = 0;
        int viewsWeek = 0;
        if (miscInfoArray != null && !miscInfoArray.isEmpty()) {
            JsonObject miscInfoObject = miscInfoArray.get(0).getAsJsonObject();
            String tcgDateString = miscInfoObject.has("tcg_date") ? miscInfoObject.get("tcg_date").getAsString() : "";
            String ocgDateString = miscInfoObject.has("ocg_date") ? miscInfoObject.get("ocg_date").getAsString() : "";
            konamiId = miscInfoObject.has("konami_id") ? miscInfoObject.get("konami_id").getAsString() : "";
            hasEffect = miscInfoObject.has("has_effect") ? miscInfoObject.get("has_effect").getAsInt() : 0;
            totalViews = miscInfoObject.has("views") ? miscInfoObject.get("views").getAsInt() : 0;
            viewsWeek = miscInfoObject.has("viewsweek") ? miscInfoObject.get("viewsweek").getAsInt() : 0 ;
            tcgDate = !tcgDateString.isEmpty() ? LocalDate.parse(tcgDateString) : null;
            ocgDate = !ocgDateString.isEmpty() ? LocalDate.parse(ocgDateString) : null;
        } else {
            System.err.println("Warning: 'misc_info' array is null or empty ");
        }
        // download images
        String localPath = fetchCardImages(cardNode);

        Card card = new Card(id, name, type, frameType, description, atk, def, level, race, attribute, archetype,
                linkval, scale, tcgDate, ocgDate, konamiId, totalViews, viewsWeek, localPath, hasEffect);
        cardList.add(card);
        return cardList;
    }
    public String fetchCardImages(JsonObject cardNode) throws Exception {
        String localPath = null;
        JsonArray cardImagesArray = cardNode.getAsJsonArray("card_images");
        for (int j = 0; j < cardImagesArray.size(); j++) {
            JsonObject imageNode = cardImagesArray.get(j).getAsJsonObject();
            String imageUrl = imageNode.get("image_url").getAsString();
            localPath = downloadImages(imageUrl);
        }
        return localPath;
    }
    public  List<CardSetInfo> fetchCardSets(JsonObject cardNode) throws Exception {
        List<CardSetInfo> cardSetInfoList = new ArrayList<>();
        CardSetInfo cardSetInfo;
        int id = cardNode.has("id") ? cardNode.get("id").getAsInt() : 0;
        JsonArray cardSetsArray = cardNode.getAsJsonArray("card_sets");
        if (cardSetsArray != null) {
            for (int j = 0; j < cardSetsArray.size(); j++) {
                JsonObject setNode = cardSetsArray.get(j).getAsJsonObject();
                String setCode = setNode.has("set_code") ? setNode.get("set_code").getAsString() : "";
                String setRarity = setNode.has("set_rarity") ? setNode.get("set_rarity").getAsString() : "";
                double setPrice = setNode.has("set_price") ? setNode.get("set_price").getAsDouble() : 0.0;
                cardSetInfo = new CardSetInfo(id, setCode, setRarity, setPrice);
                cardSetInfoList.add(cardSetInfo);
            }
        }
        return cardSetInfoList;
    }
    public List<CardPriceInfo> fetchCardPrices(JsonObject cardNode) throws Exception {
        List<CardPriceInfo> cardPriceInfoList = new ArrayList<>();
        CardPriceInfo cardPriceInfo = null;
        int id = cardNode.has("id") ? cardNode.get("id").getAsInt() : 0;
        JsonArray cardPricesArray = cardNode.getAsJsonArray("card_prices");
        if (cardPricesArray != null) {
            for (int j = 0; j < cardPricesArray.size(); j++) {
                JsonObject setNode = cardPricesArray.get(j).getAsJsonObject();
                Double cardMarketPrice = setNode.has("cardmarket_price") ? setNode.get("cardmarket_price").getAsDouble() : 0.0;
                Double tcgPlayerPrice = setNode.has("tcgplayer_price") ? setNode.get("tcgplayer_price").getAsDouble() : 0.0;
                cardPriceInfo = new CardPriceInfo(id, cardMarketPrice, tcgPlayerPrice);
            }
            cardPriceInfoList.add(cardPriceInfo);
        }else{
            throw new Exception("Warning: 'card_prices' array is null or empty ");
        }
        return cardPriceInfoList;
    }
    private String downloadImages(String imageUrl) throws IOException {
        URL url = new URL(imageUrl);
        String fileName = Paths.get(url.getPath()).getFileName().toString();
        String localPath = "card-images/" + fileName; // directory to save the images
        // Check if the file already exists
        if (Files.exists(Paths.get(localPath))) {
            return localPath;
        }
        // get images from url source and download the images
        URLConnection connection = url.openConnection();
        try (InputStream inputStream = connection.getInputStream()) {
            try (FileOutputStream outputStream = new FileOutputStream(localPath)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }
            return localPath;
        }
    }
}
