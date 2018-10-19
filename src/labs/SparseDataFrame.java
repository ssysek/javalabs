package labs;

import java.util.ArrayList;

public class SparseDataFrame extends DataFrame {
    public String hide;
    ArrayList<ArrayList<COOValue>> sparse = new ArrayList<ArrayList<COOValue>>();


    public SparseDataFrame(String[] cols, String[] rows, String hide){

        super(cols, rows);
        this.hide=hide;
    }

    public SparseDataFrame(DataFrame frame, String hide){
        super(frame.names,frame.types);
        this.hide=hide;

        for(int i=0;i<frame.tab.size();i++){
            for(int k=0;k<frame.tab.get(i).size();k++){
                if(frame.tab.get(i).get(k)!=hide){
                    COOValue cooval=new COOValue(k,frame.tab.get(i).get(k));
                    sparse.get(i).add(cooval);
                }
            }
        }

    }
    int ArraySize(ArrayList<ArrayList<COOValue>> list){
        int size=0;
        for(int i=0;i<list.size();i++){
            for(int j=0;j<list.get(i).size();i++){
                if(list.get(i).get(j).COOIndex>size){
                    size=list.get(i).get(j).COOIndex;
                }
            }
        }
        return size;
    }


    public DataFrame todense(SparseDataFrame sframe){
        DataFrame result=new DataFrame(sframe.names,sframe.types);
        String tounhide=sframe.hide;
        ArrayList<ArrayList<Object>> resultvals=new ArrayList();

        int highest=ArraySize(sframe.sparse);

        for(int i=0;i<sframe.names.length;i++){
            for(int j=0;j<highest;j++){
                resultvals.get(i).add(tounhide);
            }
        }

        for(int i = 0; i<sframe.sparse.size(); i++){
            for(int j = 0; j<sframe.sparse.get(i).size(); j++){
                COOValue thiscooval=sframe.sparse.get(i).get(j);
                resultvals.get(i).set(thiscooval.COOIndex,thiscooval.COOVal);

            }
        }
        result.values=resultvals;
        return result;

    }
}