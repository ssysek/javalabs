package labs;

import java.util.ArrayList;

public class DataFrame {

    ArrayList<ArrayList> tab = new ArrayList<ArrayList>();
    String[] names;
    String[] types;

    DataFrame(String[] names, String[] types){
        this.names=names;
        this.types=types;
    }
    int size(){
        return tab.get(0).size();
    }
    ArrayList get(String colanme){
        for (int i=0; i<names.length; ++i){
            if (colanme.equals(names[i])) {
                return tab.get(i);
            }
        }
        return new ArrayList();
    }

    DataFrame iloc (int i){
        DataFrame result = new DataFrame(names, types);
        for (int j=0; j<names.length; ++j){

            result.tab.get(j).add(tab.get(j).get(i));

        }
        return result;
    }
    DataFrame iloc (int from, int to){
        DataFrame result = new DataFrame(names, types);
        for (int j=0; j<names.length; ++j){
            result.tab.get(j).addAll(tab.get(j).subList(from, to));
        }
        return result;
    }
}