package group10.GastroAPI.Models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DishReviewsTest {
   @Test
   public void testDefaultConstructor(){
      DishReviews dishRev = new DishReviews();

      assertTrue(dishRev != null);
   }

    @Test
    public void testConstructor(){
        DishReviews dishRev = new DishReviews(1L,"Griga Goldie", "Beste Suppe",
                "Die suppe war wirklich sehr lecker, ich komme wieder, kann nur empfehlen", 5);

        assertEquals("Griga Goldie", dishRev.getAuthor());
        assertEquals("Beste Suppe", dishRev.getTitle());
        assertEquals("Die suppe war wirklich sehr lecker, ich komme wieder, kann nur empfehlen",
                dishRev.getDescription());
        assertEquals(5, dishRev.getRating());
    }

    @Test
    public void testEditMethod(){
        DishReviews dishRev = new DishReviews(1L,"Griga Goldie", "Beste Suppe",
                "Die suppe war wirklich sehr lecker, ich komme wieder, kann nur empfehlen", 5);

        dishRev.setTitle("Schreckliche Suppe");
        dishRev.setRating(1);

        assertEquals("Schreckliche Suppe", dishRev.getTitle());
        assertEquals(1, dishRev.getRating());
        assertEquals("Griga Goldie", dishRev.getAuthor()); // author не должен меняться
    }


}
