package group10.GastroAPI.Models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Persistierbare Darstellung eines Gerichts mit Basisdaten, Filterkeywords
 * und einem Flag, ob es aktuell auf der Speisekarte steht. Eingaben werden über die
 * privaten Setter validiert, bevor sie gespeichert werden.
 */
@Entity
public class Dish {
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false)
    private int weight;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false, length = 150)
    private String category;

    @Column(nullable = false)
    private boolean isInMenu = false;

    @ElementCollection
    private List<String> filterKeywords = new ArrayList<String>();


    /**
     * protected Standardkonstruktor für JPA.
     */
    protected Dish(){}

    /**
     * Legt ein Gericht mit allen Pflichtfeldern an.
     *
     * @param name Name des Gerichts (max. 150 Zeichen)
     * @param weight Gewicht in Gramm, muss größer 0 sein
     * @param price Preis in Euro, muss größer 0 sein
     * @param category Kategoriebezeichnung (max. 150 Zeichen)
     */
    public Dish(String name, int weight, double price, String category){
        setName(name);
        setWeight(weight);
        setPrice(price);
        setCategory(category);
    }

    /**
     * Legt ein Gericht mit Pflichtfeldern und Menu-Status an.
     *
     * @param name Name des Gerichts (max. 150 Zeichen)
     * @param weight Gewicht in Gramm, muss größer 0 sein
     * @param price Preis in Euro, muss größer 0 sein
     * @param category Kategoriebezeichnung (max. 150 Zeichen)
     * @param isInMenu Flag, ob das Gericht aktuell angeboten wird
     */
    public Dish(String name, int weight, double price, String category, boolean isInMenu){
        this(name, weight, price, category);
        setIsInMenu(isInMenu);
    }

    /**
     * Legt ein Gericht mit Pflichtfeldern und optionalen Filterkeywords an.
     *
     * @param name Name des Gerichts (max. 150 Zeichen)
     * @param weight Gewicht in Gramm, muss grösser 0 sein
     * @param price Preis in Euro, muss grösser 0 sein
     * @param category Kategoriebezeichnung (max. 150 Zeichen)
     * @param filterKeywords optionale Liste zum Filtern und Suchen
     */
    public Dish(String name, int weight, double price, String category, List<String> filterKeywords){
        this(name, weight, price, category);
        setFilterKeywords(filterKeywords);
    }

    /**
     * Legt ein Gericht mit Pflichtfeldern, Filterkeywords und Menu-Status an.
     *
     * @param name Name des Gerichts (max. 150 Zeichen)
     * @param weight Gewicht in Gramm, muss grösser 0 sein
     * @param price Preis in Euro, muss grösser 0 sein
     * @param category Kategoriebezeichnung (max. 150 Zeichen)
     * @param filterKeywords optionale Liste zum Filtern und Suchen
     * @param isInMenu Flag, ob das Gericht aktuell angeboten wird
     */
    public Dish(String name, int weight, double price, String category, List<String> filterKeywords, boolean isInMenu){
        this(name, weight, price, category, filterKeywords);
        setIsInMenu(isInMenu);
    }

    /**
     * Liefert die technische ID aus der Datenbank.
     */
    public Long getId() { return id; }

    /**
     * Name des Gerichts.
     */
    public String getName() { return this.name; }
    public void setName(String name) { 
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Der Name darf nicht leer sein");

        if (name.length() > 150) 
            throw new IllegalArgumentException("Der Name darf nicht länger als 150 Zeichen sein");
        
        this.name = name;
    }

    /**
     * Gewicht in Gramm.
     */
    public int getWeight() { return this.weight; }
    public void setWeight(int weight) { 
        if(weight <= 0)
            throw new IllegalArgumentException("Das Gewicht darf nicht 0 oder kleiner 0 sein");

        this.weight = weight; 
    }

    /**
     * Preis in Euro.
     */
    public double getPrice() { return this.price; }
    public void setPrice(double price) { 
        if(price <= 0)
            throw new IllegalArgumentException("Der Preis darf nicht 0 oder kleiner 0 sein");

        this.price = price; 
    }

    /**
     * Kategoriebezeichnung.
     */
    public String getCategory() { return this.category; }
    public void setCategory(String category) { 
        if (category == null || category.isBlank())
            throw new IllegalArgumentException("Die Kategorie darf nicht leer sein");

        if (category.length() > 150) 
            throw new IllegalArgumentException("Die Kategorie darf nicht länger als 150 Zeichen sein");
        
        this.category = category; 
    }

    /**
     * Filterkeywords zur Suche und Gruppierung.
     */
    public List<String> getFilterKeywords() { return this.filterKeywords; }
    public void setFilterKeywords(List<String> filterKeywords) {
        if (filterKeywords == null)
            this.filterKeywords = new ArrayList<>();     
        else
            this.filterKeywords = new ArrayList<>(filterKeywords);
        
    }

    /**
     * Gibt an, ob das Gericht aktuell auf der Speisekarte steht.
     */
    public boolean getIsInMenu() { return this.isInMenu; }
    public void setIsInMenu(boolean isInMenu) {
        this.isInMenu = isInMenu; 
    }

    
    /**
     * Uebernimmt validierte Werte aus einem anderen Gericht.
     *
     * @param newValues Quelle für neue Werte, darf nicht null sein
     * @throws IllegalArgumentException wenn newValues null ist oder Validierungen fehlschlagen
     */
    public void edit(Dish newValues) {
        if (newValues == null) {
            throw new IllegalArgumentException("newValues darf nicht null sein");
        }
        setName(newValues.getName());
        setWeight(newValues.getWeight());
        setPrice(newValues.getPrice());
        setCategory(newValues.getCategory());
        setFilterKeywords(newValues.getFilterKeywords());
        setIsInMenu(newValues.getIsInMenu());
    }


}
