import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class SILab2Test extends TestCase {

    private List<Time> createList (Time... elements){
        return new ArrayList<>(Arrays.asList(elements));
    }

    @Test
    void everyBranchTest(){
        RuntimeException exception;

        assertEquals(0, SILab2.function(Collections.emptyList()).size());

        int sum = SILab2.function(createList(new Time(22,45,35),
                new Time(24,0,0))).stream().reduce(0, Integer::sum);
        assertEquals(168335,sum);

        exception = assertThrows(RuntimeException.class, () -> SILab2.function(createList(new Time(-2,45,35))));
        assertTrue(exception.getMessage().contains("The hours are smaller than the minimum"));

        exception = assertThrows(RuntimeException.class, () -> SILab2.function(createList(new Time(25,45,35))));
        assertTrue(exception.getMessage().contains("The hours are grater than the maximum"));

        exception = assertThrows(RuntimeException.class, () -> SILab2.function(createList(new Time(22,65,35))));
        assertTrue(exception.getMessage().contains("The minutes are not valid!"));

        exception = assertThrows(RuntimeException.class, () -> SILab2.function(createList(new Time(22,45,65))));
        assertTrue(exception.getMessage().contains("The seconds are not valid"));

        exception = assertThrows(RuntimeException.class, () -> SILab2.function(createList(new Time(24,65,65))));
        assertTrue(exception.getMessage().contains("The time is greater than the maximum"));
    }

    @Test
    void multipleConditionsTest(){
        RuntimeException exception;
        for(int i=1; i<=4; i++){
            switch (i){
                case 1: exception = assertThrows(RuntimeException.class, () -> SILab2.function(createList(new Time(-2,45,35))));
                        assertTrue(exception.getMessage().contains("The hours are"));
                        exception = assertThrows(RuntimeException.class, () -> SILab2.function(createList(new Time(25,45,35))));
                        assertTrue(exception.getMessage().contains("The hours are"));
                        int sum = SILab2.function(createList(new Time(22,45,35))).get(0);
                        assertEquals(81935, sum);


                case 2: exception = assertThrows(RuntimeException.class, () -> SILab2.function(createList(new Time(22,-5,35))));
                        assertTrue(exception.getMessage().contains("The minutes are not valid!"));
                        exception = assertThrows(RuntimeException.class, () -> SILab2.function(createList(new Time(22,65,35))));
                        assertTrue(exception.getMessage().contains("The minutes are not valid!"));
                        sum = SILab2.function(createList(new Time(22,45,35))).get(0);
                        assertEquals(81935, sum);

                case 3: exception = assertThrows(RuntimeException.class, () -> SILab2.function(createList(new Time(22,45,-5))));
                        assertTrue(exception.getMessage().contains("The seconds are not valid"));
                        exception = assertThrows(RuntimeException.class, () -> SILab2.function(createList(new Time(22,45,65))));
                        assertTrue(exception.getMessage().contains("The seconds are not valid"));
                        sum = SILab2.function(createList(new Time(22,45,35))).get(0);
                        assertEquals(81935, sum);

                case 4: exception = assertThrows(RuntimeException.class, () -> SILab2.function(createList(new Time(24,45,0))));
                        assertTrue(exception.getMessage().contains("The time is greater than the maximum"));
                        exception = assertThrows(RuntimeException.class, () -> SILab2.function(createList(new Time(24,0,45))));
                        assertTrue(exception.getMessage().contains("The time is greater than the maximum"));
                        sum = SILab2.function(createList(new Time(24,0,0))).get(0);
                        assertEquals(86400, sum);
            }
        }
    }
}