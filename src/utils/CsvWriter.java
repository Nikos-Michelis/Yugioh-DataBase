package utils;

import com.opencsv.CSVWriter;
import model.Card;
import model.CardPriceInfo;
import model.CardSetInfo;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class CsvWriter implements LoadingBar {
    private final String[] CSV_HEADER = {"id", "name", "type", "frameType", "description", "atk", "def", "level",
            "race", "attribute", "archetype", "linkval", "scale", "tcgDate", "ocgDate", "konamiId", "views", "viewsweek", "imageUrl", "hasEffect"};
    private final String[] CSV_SETS = {"cardId", "setCode", "setRarity", "set_price"};
    private final String[] CSV_Price = {"cardId", "cardmarket_price", "tcgplayer_price"};

    public void writeDataToCSV(List<?> dataList, String csvFilePath) {
        try (CSVWriter writer = new CSVWriter(
                new OutputStreamWriter(new FileOutputStream(csvFilePath), StandardCharsets.UTF_8),
                CSVWriter.DEFAULT_SEPARATOR, CSVWriter.DEFAULT_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END)) {

            if (!dataList.isEmpty()) {
                Object data = dataList.get(0);
                String[] csvHeader = getCSVHeader(data);
                writer.writeNext(csvHeader);
            }

            int dataSize = dataList.size();
            int progressBarStep = Math.max(dataSize / 100, 1); // Dynamic progress bar step
            for (int i = 0; i < dataSize; i++) {
                writer.writeNext(getStringArrayFromData(dataList.get(i)));
                if (i % progressBarStep == 0 || i == dataSize - 1) {
                    updateLoadingBar(i + 1, dataSize, "Data written to " + csvFilePath);
                    Thread.sleep(200);
                }
            }
            System.out.println("- Data written to " + csvFilePath + " successfully.");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    private String[] getCSVHeader(Object data) {
        if (data instanceof CardSetInfo) {
            return CSV_SETS;
        } else if (data instanceof CardPriceInfo) {
            return CSV_Price;
        } else if (data instanceof Card) {
            return CSV_HEADER;
        }
        return new String[0];
    }
    private String[] getStringArrayFromData(Object data) {
        if (data instanceof CardSetInfo) {
            CardSetInfo cardSetInfo = (CardSetInfo) data;
            return new String[]{
                    String.valueOf(cardSetInfo.getCardId()), cardSetInfo.getSetCode(),
                    cardSetInfo.getSetRarity(), String.valueOf(cardSetInfo.getSetPrice())
            };
        } else if (data instanceof CardPriceInfo) {
            CardPriceInfo cardPriceInfo = (CardPriceInfo) data;
            return new String[]{
                    String.valueOf(cardPriceInfo.getCardId()), String.valueOf(cardPriceInfo.getCardMarketPrice()),
                    String.valueOf(cardPriceInfo.getTcgPlayerPrice())
            };
        } else if (data instanceof Card) {
            Card card = (Card) data;
            return new String[]{
                    String.valueOf(card.getId()), card.getName(), card.getType(), card.getFrameType(),
                    card.getDescription(), String.valueOf(card.getAtk()), String.valueOf(card.getDef()),
                    String.valueOf(card.getLevel()), card.getRace(), card.getAttribute(), card.getArchetype(),
                    String.valueOf(card.getLinkval()), String.valueOf(card.getScale()), String.valueOf(card.getTcgDate()),
                    String.valueOf(card.getOcgDate()), card.getKonamiId(), String.valueOf(card.getTotalViews()),
                    String.valueOf(card.getViewsWeek()), card.getImageUrl(), String.valueOf(card.getHasEffect())
            };
        }
        return new String[0];
    }
}
