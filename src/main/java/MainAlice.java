import core.agents.Alice;
import core.agents.Bob;
import ui.AliceUI;

import java.math.BigInteger;
import java.util.ArrayList;


public class MainAlice {

    public static void main(String[] args) {
        AliceUI aliceUI = new AliceUI(new Alice(new ArrayList<BigInteger>(), BigInteger.ONE));
    }

}
