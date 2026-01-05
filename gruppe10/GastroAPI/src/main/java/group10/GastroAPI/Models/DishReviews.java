package group10.GastroAPI.Models;

import jakarta.persistence.*;

/**
 * Hinterlassen der Rezensionen für einzehlne Speisen aus Menü
 */
@Entity
public class DishReviews {

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;

   @Column(nullable = false, length = 5)
   private Long dishId;

   @Column(nullable = false, length = 100)
   private String author;

   @Column(nullable = false, length = 200)
   private String title;

   @Column(nullable = false, length = 10000)
   private String description;

   @Column(nullable = false)
   private double rating;

   /**
    * Leerer Konstruktor für JPA
    */
   public DishReviews(){}

   public DishReviews(Long dishId,String author, String title, String description, double rating) {
       this.setDishId(dishId);
      this.setAuthor(author);
      this.setTitle(title);
      this.setDescription(description);
      this.setRating(rating);

   }

   public long getId(){
      return id;
   }

   public long getDishId(){
      return dishId;
   }
   public void setDishId(Long dishId){
      if(dishId <= 0){
         throw new IllegalArgumentException("Dish Id darf nicht 0 oder kleiner sein");
      }

      this.dishId = dishId;
   }

   public String getAuthor(){
      return author;
   }
   public void setAuthor(String author){
      if(author == null || author.trim().isEmpty()){
         throw new IllegalArgumentException("Author darf nicht null oder leer sein");
      }

      this.author = author;
   }

   public String getTitle(){
      return title;
   }
   public void setTitle(String title){
      if(title == null || title.trim().isEmpty()){
         throw new IllegalArgumentException("Title darf nicht null oder leer sein");
      }
      this.title = title;
   }

   public String getDescription(){
      return description;
   }
   public void setDescription(String description){
      if(description == null || description.trim().isEmpty()){
         throw new IllegalArgumentException("Description darf nicht null oder leer sein");
      }
      this.description = description;
   }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Bewertung muss zwischen 1 und 5 liegen");
        }
        this.rating = rating;
    }

    public void edit(DishReviews dishRev) {
        if (dishRev == null) {
            throw new IllegalArgumentException("DishReviews cannot be null");
        }
        setDishId(dishRev.getDishId());
        setAuthor(dishRev.getAuthor());
        setTitle(dishRev.getTitle());
        setDescription(dishRev.getDescription());
        setRating(dishRev.getRating());
    }
   
}

