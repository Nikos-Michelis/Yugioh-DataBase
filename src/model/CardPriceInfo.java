package model;

public class CardPriceInfo {
    private int cardId;
    private Double cardMarketPrice;
    private Double tcgPlayerPrice;

    public CardPriceInfo(int cardId, Double cardMarketPrice, Double tcgPlayerPrice) {
        this.cardId = cardId;
        this.cardMarketPrice = cardMarketPrice;
        this.tcgPlayerPrice = tcgPlayerPrice;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public Double getCardMarketPrice() {
        return cardMarketPrice;
    }

    public void setCardMarketPrice(Double cardMarketPrice) {
        this.cardMarketPrice = cardMarketPrice;
    }

    public Double getTcgPlayerPrice() {
        return tcgPlayerPrice;
    }

    public void setTcgPlayerPrice(Double tcgPlayerPrice) {
        this.tcgPlayerPrice = tcgPlayerPrice;
    }

    @Override
    public String toString() {
        return "CardPriceInfo{" +
                "cardId=" + cardId +
                ", cardMarketPrice=" + cardMarketPrice +
                ", tcgPlayerPrice=" + tcgPlayerPrice +
                '}';
    }
}
