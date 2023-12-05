package model;

import utils.LoadingBar;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class CardDAO implements LoadingBar {
    private final String URL = "jdbc:mysql://localhost:3306/yugioh_db_cards";
    private final String USER = "yugiohUser";
    private final String PASSWORD = "1234";
    public void insertCard(List<Card> cards) throws SQLException {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)){
            String insertQuery = "INSERT INTO cards (id, name, type, frameType, description, atk, def, level, race, attribute, " +
                    "archetype, linkval, scale, tcgDate, ocgDate, konamiId, totalViews, viewsWeek, imageUrl, hasEffect) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            int counter = 0;
            try (PreparedStatement preparedStatement = conn.prepareStatement(insertQuery)) {
                for (Card card : cards) {
                    preparedStatement.setInt(1, card.getId());
                    preparedStatement.setString(2, card.getName());
                    preparedStatement.setString(3, card.getType());
                    preparedStatement.setString(4, card.getFrameType());
                    preparedStatement.setString(5, card.getDescription());
                    preparedStatement.setInt(6, card.getAtk());
                    preparedStatement.setInt(7, card.getDef());
                    preparedStatement.setInt(8, card.getLevel());
                    preparedStatement.setString(9, card.getRace());
                    preparedStatement.setString(10, card.getAttribute());
                    preparedStatement.setString(11, card.getArchetype());
                    preparedStatement.setInt(12, card.getLinkval());
                    preparedStatement.setInt(13, card.getScale());
                    preparedStatement.setString(14, card.getTcgDate() != null ? String.valueOf(java.sql.Date.valueOf(card.getTcgDate())) : null);
                    preparedStatement.setString(15, card.getOcgDate() != null ? String.valueOf(java.sql.Date.valueOf(card.getOcgDate())) : null);
                    preparedStatement.setString(16, card.getKonamiId() != null ? String.valueOf(card.getKonamiId()) : null);
                    preparedStatement.setString(17, String.valueOf(card.getTotalViews()));
                    preparedStatement.setString(18, String.valueOf(card.getViewsWeek()));
                    preparedStatement.setString(19, card.getImageUrl());
                    preparedStatement.setInt(20, card.getHasEffect());
                    preparedStatement.executeUpdate();
                    counter++;
                    updateLoadingBar(counter, cards.size(), "Inserting cards into table.");
                }

            } catch (SQLException | InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("- cards inserted into DB successfully.");
        }
    }
    public void insertCardSets(List<CardSetInfo> cardSetInfoList) throws SQLException {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)){
            String insertQuery = "INSERT INTO cards_sets (cardId, setCode, setRarity, setPrice) " +
                    "VALUES (?, ?, ?, ?)";
            int counter = 0;
            try (PreparedStatement preparedStatement = conn.prepareStatement(insertQuery)) {
                for (CardSetInfo cardSet : cardSetInfoList) {
                    preparedStatement.setInt(1, cardSet.getCardId());
                    preparedStatement.setString(2, cardSet.getSetCode());
                    preparedStatement.setString(3, cardSet.getSetRarity());
                    preparedStatement.setDouble(4, cardSet.getSetPrice());
                    preparedStatement.executeUpdate();
                    counter++;
                    updateLoadingBar(counter, cardSetInfoList.size(), "Inserting cards_sets into table.");
                }
            } catch (SQLException | InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("- cards_sets inserted into DB successfully.");
        }
    }
    /*public void insertCardPrices(List<CardPriceInfo> cardPriceInfoList) throws SQLException {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)){
            String insertQuery = "INSERT INTO cards_sets (cardId, cardMarketPrice, tcgPlayerPrice) " +
                    "VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = conn.prepareStatement(insertQuery)) {
                for (CardPriceInfo cardPriceInfo : cardPriceInfoList) {
                    preparedStatement.setInt(1, cardPriceInfo.getCardId());
                    preparedStatement.setDouble(2, cardPriceInfo.getCardMarketPrice());
                    preparedStatement.setDouble(3, cardPriceInfo.getTcgPlayerPrice());
                    preparedStatement.executeUpdate();
                    counter++;
                    updateLoadingBar(counter, cardPriceInfoList.size(), "Inserting cards_prices into table - ");
                }
            } catch (SQLException | InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("cards_prices inserted into DB successfully.");
        }
    }*/
}
