import core.agents.Alice;
import core.agents.Bob;
import ui.AliceUI;

import java.math.BigInteger;
import java.util.ArrayList;


public class MainAlice {

    public static void main(String[] args) {
        ArrayList<BigInteger> arrayList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            arrayList.add(BigInteger.valueOf(i + 1));
        }
        AliceUI aliceUI = new AliceUI(new Alice(arrayList, BigInteger.ONE));
    }

}
