import core.agents.Alice;
import core.agents.Bob;
import ui.BobUI;

import java.math.BigInteger;
import java.util.ArrayList;


/**
 * @author Dimitri RODARIE on 08/02/2016.
 * @version 1.0
 */
public class MainBob {

    public static void main(String[] args) {
        BobUI bobUI = new BobUI(new Bob());
    }
}
