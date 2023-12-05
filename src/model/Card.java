package model;

import java.time.LocalDate;
import java.util.Arrays;

public class Card {
    private int id;
    private String name;
    private String type;
    private String frameType;
    private String description;
    private int atk;
    private int def;
    private int level;
    private String race;
    private String attribute;
    private String archetype;
    private int linkval;
    private int scale;
    private LocalDate tcgDate;
    private LocalDate ocgDate;
    private String konamiId;
    private int totalViews;
    private int viewsWeek;
    private int hasEffect;
    private String imageUrl;
    public Card() {}

    public Card(int id, String name, String type, String frameType, String description, int atk, int def, int level, String race, String attribute, String archetype,
                int linkval, int scale, LocalDate tcgDate, LocalDate ocgDate, String konamiId, int totalViews, int viewsWeek, String imageUrl, int hasEffect) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.frameType = frameType;
        this.description = description;
        this.atk = atk;
        this.def = def;
        this.level = level;
        this.attribute = attribute;
        this.archetype = archetype;
        this.linkval = linkval;
        this.scale = scale;
        this.race = race;
        this.tcgDate = tcgDate;
        this.ocgDate = ocgDate;
        this.konamiId = konamiId;
        this.totalViews = totalViews;
        this.viewsWeek = viewsWeek;
        this.imageUrl = imageUrl;
        this.hasEffect = hasEffect;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getArchetype() {
        return archetype;
    }

    public void setArchetype(String archetype) {
        this.archetype = archetype;
    }

    public int getLinkval() {
        return linkval;
    }

    public void setLinkval(int linkval) {
        this.linkval = linkval;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public LocalDate getTcgDate() {
        return tcgDate;
    }

    public void setTcgDate(LocalDate tcgDate) {
        this.tcgDate = tcgDate;
    }

    public LocalDate getOcgDate() {
        return ocgDate;
    }

    public void setOcgDate(LocalDate ocgDate) {
        this.ocgDate = ocgDate;
    }

    public String getKonamiId() {
        return konamiId;
    }

    public void setKonamiId(String konamiId) {
        this.konamiId = konamiId;
    }

    public int getHasEffect() {
        return hasEffect;
    }

    public void setHasEffect(int hasEffect) {
        this.hasEffect = hasEffect;
    }

    public String getFrameType() {
        return frameType;
    }

    public void setFrameType(String frameType) {
        this.frameType = frameType;
    }

    public int getTotalViews() {
        return totalViews;
    }

    public void setTotalViews(int totalViews) {
        this.totalViews = totalViews;
    }

    public int getViewsWeek() {
        return viewsWeek;
    }

    public void setViewsWeek(int viewsWeek) {
        this.viewsWeek = viewsWeek;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", frameType='" + frameType + '\'' +
                ", description='" + description + '\'' +
                ", atk=" + atk +
                ", def=" + def +
                ", level=" + level +
                ", race='" + race + '\'' +
                ", attribute='" + attribute + '\'' +
                ", archetype='" + archetype + '\'' +
                ", linkval=" + linkval +
                ", scale=" + scale +
                ", tcgDate=" + tcgDate +
                ", ocgDate=" + ocgDate +
                ", konamiId='" + konamiId + '\'' +
                ", totalViews=" + totalViews +
                ", viewsWeek=" + viewsWeek +
                ", hasEffect=" + hasEffect +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
