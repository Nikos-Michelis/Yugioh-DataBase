package model;

public class CardSetInfo {
    private int cardId;
    private String setCode;
    private String setRarity;
    private double setPrice;

    public CardSetInfo(int cardId, String setCode, String setRarity, double setPrice) {
        this.cardId = cardId;
        this.setCode = setCode;
        this.setRarity = setRarity;
        this.setPrice = setPrice;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public String getSetCode() {
        return setCode;
    }

    public void setSetCode(String setCode) {
        this.setCode = setCode;
    }

    public String getSetRarity() {
        return setRarity;
    }

    public void setSetRarity(String setRarity) {
        this.setRarity = setRarity;
    }

    public double getSetPrice() {
        return setPrice;
    }

    public void setSetPrice(double setPrice) {
        this.setPrice = setPrice;
    }

    @Override
    public String toString() {
        return "CardSetInfo{" +
                "cardId=" + cardId +
                ", setCode='" + setCode + '\'' +
                ", setRarity='" + setRarity + '\'' +
                ", setPrice=" + setPrice +
                '}';
    }
}
