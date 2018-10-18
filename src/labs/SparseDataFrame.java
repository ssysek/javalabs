/*package labs;

import java.util.ArrayList;

public class SparseDataFrame extends DataFrame {
    public String hide;
    ArrayList<ArrayList<COOValue>> sparse = new ArrayList<ArrayList<COOValue>>();


    public SparseDataFrame(String[] cols, String[] rows, String hide){
        super(cols, rows);
        this.hide=hide;
    }

    public SparseDataFrame(DataFrame df, String hide){
        super(df.names, df.types);
        for(int i =0; i < df.tab.size();i++){
            for(int j=0;j < df.tab.size();j++){
                if(df.tab.get(i).get(j)!=hide){
                    //COOValue cooval= new COOValue(j,df.tab.get(i).get(j));
                    //sparse.get(i).add(cooval);
                }
            }
        }
    }
    public DataFrame ToDense(SparseDataFrame sdf){
        DataFrame DenseDataFrame = new DataFrame(sdf.names,sdf.types);
        String fill = sdf.hide;

        for(int i=0; i< sdf.size();i++){
        }
    }

}*/