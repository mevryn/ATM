import com.atm.ATM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ConsoleATMControllerTest {

    private ATM atm;

    private long MAX_MONEY_CAPACITY=500000;

    @BeforeEach
    public void init(){
        atm = new ATM(MAX_MONEY_CAPACITY);
    }

    @Test
    public void atmShouldAcceptCreditCard(){

    }
}
