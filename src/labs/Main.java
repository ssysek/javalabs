package labs;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        // write your code here
    }
    DataFrame ck = new  DataFrame(new String[]{"kol1","kol2","kol3"},
            new String[]{"int","double","MyCustomType"});

    ArrayList<ArrayList> test = new ArrayList<ArrayList>();
    test= ck.get("kol1");
}
