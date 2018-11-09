package labs;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.io.*;


public class DataFrame {
    ArrayList<ArrayList> dataFrame;
    String[] names;
    Class<? extends Value>[] types;


    public DataFrame(String[] names, Class<? extends Value>[] types){
        dataFrame = new ArrayList<>();
        int size = names.length;
        this.names = names;
        this.types = types;
        initiate(types);
    }

    DataFrame(String[] names, ArrayList<ArrayList> data){
        this.names = names;
        this.dataFrame = data;
    }

    DataFrame(DataFrame df){
        names = df.names;
        types = df.types;
        dataFrame = df.dataFrame;
    }

    DataFrame(String fileName, Class<? extends Value>[] types, boolean isHeader){
        try ( BufferedReader br = Files.newBufferedReader(Paths.get(fileName))){
            dataFrame = new ArrayList<>();
            String line = br.readLine();
            if(line.split(",").length!=types.length){
                System.out.print("Amount of given types can't differ from number of columns in file");
            }
            else{
                names = new String[types.length];
                if(isHeader && line!=null){
                    names = line.split(",");
                    line = br.readLine();
                }
                initiate(types);
                this.types = types;

                while(line!=null){
                    String[] attributes = line.split(",");
                    add(attributes);
                    line = br.readLine();
                }
            }
        } catch (Exception exception){
            exception.printStackTrace();
        }


    }

    int Size(){
        return dataFrame.get(0).size();
    }

    ArrayList get(String colname){
        int i;
        for (i=0; i<names.length; i++){
            if (names[i].equals(colname)){
                return dataFrame.get(i);
            }
        }
        return null;
    }

    DataFrame get(String[] cols, boolean copy){
        ArrayList list = new ArrayList();
        for (String col : cols) {
            for(int x =0; x<names.length; x++){
                if(col.equals(names[x])){
                    list.add(copy?dataFrame.get(x).clone():dataFrame.get(x));
                }
            }
        }
        return new DataFrame(cols, list);
    }

    ArrayList getRow(int indexOfRow){
        ArrayList row = new ArrayList();
        for(int columnIterator=0; columnIterator<names.length; columnIterator++){
            row.add(dataFrame.get(columnIterator).get(indexOfRow));
        }
        return row;
    }

    DataFrame iloc(int i){
        ArrayList list = new ArrayList();
        for (int k=0; k<dataFrame.size(); k++){
            if(dataFrame.get(k).size()>i){
                list.add(dataFrame.get(k).get(i));
            }
            else{
                return null;
            }
        }
        return new DataFrame(names, list);
    }

    DataFrame iloc(int from, int to){
        ArrayList list = new ArrayList();
        ArrayList df = new ArrayList();
        for (int i=0; i<dataFrame.size(); i++){
            for (int k=from; k<to; k++){
                if(dataFrame.get(i).size()>k){
                    list.add(dataFrame.get(i).get(k));
                }
                else{
                    return null;
                }
            }
            df.add(list);
        }
        return new DataFrame(names, df);
    }

    void initiate(Class<? extends Value>[] v){
        if(v.length == names.length){
                for(int columnIterator=0; columnIterator<names.length; columnIterator++) {
                    if (Value.class.isAssignableFrom(v[columnIterator])) {
                        if (v[columnIterator] == IntHolder.class) {
                            dataFrame.add(new ArrayList<IntHolder>());
                        }
                        if (v[columnIterator] == DoubleHolder.class) {
                            dataFrame.add(new ArrayList<DoubleHolder>());
                        }
                        if (v[columnIterator] == FloatHolder.class) {
                            dataFrame.add(new ArrayList<FloatHolder>());
                        }
                        if (v[columnIterator] == StringHolder.class) {
                            dataFrame.add(new ArrayList<StringHolder>());
                        }
                        if (v[columnIterator] == DateTimeHolder.class) {
                            dataFrame.add(new ArrayList<DateTimeHolder>());
                        }

                    }
                    else{
                        System.out.print("Wrong type");
                    }
                }
        }
    }

    void add(String [] content){
        ArrayList <Value> values = new ArrayList<Value>();
        try{
            for(int columnIterator=0; columnIterator<names.length; columnIterator++){
                values.add(Value.builder(types[columnIterator]).build(content[columnIterator]));
            }
            addRow(values);
        }
        catch(NumberFormatException e){
            System.out.print("Values in file of different types than given types");
        }

    }

    void addRow(ArrayList row){
        for(int columnIterator=0; columnIterator<names.length; columnIterator++){
            dataFrame.get(columnIterator).add(row.get(columnIterator));
        }
    }



    }


