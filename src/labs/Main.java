package labs;

import java.util.ArrayList;
import java.util.Objects;

public class Main {

    public static void main(String[] args) {
        DataFrame ck = new  DataFrame(new String[]{"kol1","kol2","kol3"},
                new String[]{"int","double","MyCustomType"});

        ArrayList<ArrayList> test = new ArrayList();
        test.add(ck.get("kol1"));

        System.out.println("Using for loop");
        System.out.println("--------------");
        for (int i = 0; i < test.size(); i++) {
            System.out.println(test.get(i));
        }
    }

}
