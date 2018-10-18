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

    DataFrame get (String[] cols, boolean copy){
        String [] nowe_typy = new String[cols.length];
        for (int i=0; i<cols.length; ++i){
            for (int j=0; j<names.length; ++j){
                if (cols[i].equals(names[j])){
                    nowe_typy[i]=types[j];
                }
            }
        }

        DataFrame result = new DataFrame(cols, nowe_typy);

        for (int i=0; i<cols.length; ++i){
            for (int j=0; j<names.length; ++j){
                if (cols[i].equals(names[j])){
                    if (copy){
                        result.tab.get(j).addAll(tab.get(i));
                    } else {
                        result.tab.set(j, tab.get(i));
                    }
                }
            }
        }
        return result;
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