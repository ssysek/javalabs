package labs;
import java.io.*;
import java.util.*;

public class DataFrame
{

    ArrayList<Column> columns;
    public DataFrame()
    {
        columns = new ArrayList<>();
    }

    public DataFrame(String[] colnames, Class<? extends Value>[] coltypes)
    {
        columns = new ArrayList<>();
        for(int i=0; i<colnames.length;i++)
            columns.add(new Column(colnames[i],coltypes[i]));
    }

    public DataFrame(String filename, Class<? extends Value>[] coltypes)
    {
        this(filename, coltypes, true);
    }

    public DataFrame(String filename, Class<? extends Value>[] coltypes, boolean header)
    {
        columns = new ArrayList<>();
        BufferedReader br = null;
        try
        {
            FileInputStream fstream = new FileInputStream(filename);
            br = new BufferedReader(new InputStreamReader(fstream));
            String line, name;
            String[] colnames, row;

            if((line = br.readLine()) != null && header)
            {
                colnames = line.split(",");
                for(int i=0; i<colnames.length;i++)
                    columns.add(new Column(colnames[i],coltypes[i]));
            }
            else if(!header)
            {
                Scanner keyboard = new Scanner(System.in);
                for(int i=0; i<coltypes.length;i++)
                {
                    System.out.println("Enter the name of column number" + (i+1));
                    name = keyboard.nextLine();
                    columns.add(new Column(name,coltypes[i]));
                }
            }

            while((line = br.readLine()) != null)
            {
                row = line.split(",");
                for(int i=0; i<row.length;i++)
                {
                    Class clazz = coltypes[i];
                    if( IntegerValue.class == clazz)
                    {
                        columns.get(i).col.add(new IntegerValue(Integer.parseInt( row[i] )));
                        continue;
                    }
                    if( FloatValue.class == clazz)
                    {
                        columns.get(i).col.add(new FloatValue(Float.parseFloat( row[i] )));
                        continue;
                    }
                    if( DoubleValue.class == clazz)
                    {
                        columns.get(i).col.add(new DoubleValue(Double.parseDouble( row[i] )));
                        continue;
                    }
                    if( StringValue.class == clazz)
                    {
                        columns.get(i).col.add(new StringValue(row[i]));
                        continue;
                    }
                    if( DateTimeValue.class == clazz)
                    {
                        DateTimeValue dtv = new DateTimeValue(new Date());
                        columns.get(i).col.add(dtv.create(row[i]));
                    }

                }
            }
        } catch(FileNotFoundException e) {
            System.err.println("Caught FileNotFoundException: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch(IOException e) {
            System.err.println("Caught IOException: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (null != br)
            {
                try{ br.close(); }
                catch (IOException e) {System.err.println("Caught IOException while closing reader: " + e.getMessage());e.printStackTrace();}
            }
        }
    }

    public int Size()
    {
        return columns.get(0).col.size();
    }

    public Column Get(String colname)
    {
        for(Column c:columns)
            if(c.columnName.equals(colname))
                return c;
        throw (new IllegalArgumentException("No such column"));
    }

    /*public DataFrame Get(String[] cols, boolean deepCopy)
    {
        DataFrame partialFrame = null;
        for(int i=0;i<cols.length;i++)
        {
            for(int j=0;j<columns.size();j++)
            {
                if(cols[i].equals(columns.get(j).columnName))
                {
                    if(!deepCopy)
                    {
                        partialFrame.columns.add(columns.get(j).clone());
                    }
                    else
                    {
                        partialFrame.columns.add(new Column(columns.get(j).columnName,columns.get(j).columnType));
                        partialFrame.columns.get(partialFrame.columns.size()-1).col = columns.get(j).col.clone();
                    }
                }
            }
        }
        return partialFrame;
    }*/

    public DataFrame Iloc(int i)
    {
        String[] colnames = new String[columns.size()];
        Class<? extends Value>[] coltypes = (Class<? extends Value>[]) new Class<?>[columns.size()];
        for(int x=0; x<columns.size(); x++)
        {
            colnames[x] = columns.get(x).columnName;
            coltypes[x] = columns.get(x).columnType;
        }
        DataFrame rowOfIndex = new DataFrame(colnames, coltypes);
        for(int c=0; c<rowOfIndex.columns.size(); c++)
        {
            rowOfIndex.columns.get(c).col.add(columns.get(c).col.get(i));
        }
        return rowOfIndex;
    }

    public DataFrame Iloc(int from, int to)
    {
        String[] colnames = new String[columns.size()];
        Class<? extends Value>[] coltypes = (Class<? extends Value>[]) new Class<?>[columns.size()];
        for(int x=0; x<columns.size(); x++)
        {
            colnames[x] = columns.get(x).columnName;
            coltypes[x] = columns.get(x).columnType;
        }
        DataFrame rowsOfIndex = new DataFrame(colnames, coltypes);
        for(int i=from; i<to+1; i++)
        {
            for(int c=0; c<columns.size(); c++)
            {
                rowsOfIndex.columns.get(c).col.add(columns.get(c).col.get(i));
            }
        }
        return rowsOfIndex;
    }

    public void addRow(DataFrame newRow)
    {
        if(newRow.columns.size()!=columns.size())
            throw new RuntimeException("Added row has different size than the data frame.");
        for(int i=0;i<columns.size();i++)
        {
            columns.get(i).col.add(newRow.columns.get(i).col.get(0));
        }
    }

    boolean CustomContainsKey(HashMap<Value[], DataFrame> container, Value[] key)
    {
        int equalityRate;
        for (HashMap.Entry<Value[], DataFrame> entry : container.entrySet())
        {
            equalityRate=0;
            for(int i=0;i<key.length;i++)
            {
                if(entry.getKey()[i].eq(key[i]))
                    equalityRate++;
            }
            if(equalityRate==key.length)
                return true;
        }
        return false;
    }

    public void DisplayRow(int index)
    {
        DataFrame tmp = this.Iloc(index);
        for(int i=0;i<tmp.columns.size();i++)
            System.out.print(tmp.columns.get(i).col.get(0).toString()+' ');
        System.out.println();
    }

    public void Display() {
        for (Column c : columns)
            System.out.print(c.columnName + ' ');
        System.out.println();
        for (int i = 0; i < this.Size(); i++)
            this.DisplayRow(i);
    }

    public DfList groupby(String name) {
        String[] names = new String[columns.size()];
        ArrayList<Class<? extends Value>> types = new ArrayList<>(0);
        int counter = 0;
        for (Column i : columns) {
            names[counter] = i.columnName;
            types.add(i.columnType);
            counter++;
        }
        LinkedList<DataFrame> ldf = new LinkedList<>();
        counter = 0;
        for (Column i : columns) {
            if (i.columnName.equals(name)) {
                int whereToStart = 1;
                int whereToStart2 = 0;
                if (i.col.size() > 1) {
                    while (whereToStart < i.col.size()) {
                        DataFrame df = new DataFrame(names, types);
                        Value[] v = new Value[columns.size()];
                        int c = 0;
                        for (Column j : columns) {
                            v[c] = j.col.get(whereToStart2);
                            c++;
                        }
                        try {
                            df.addRow(v);
                        } catch (CustomExpection e) {
                            e.print();
                        }
                        while (i.col.get(whereToStart).equals(df.columns.get(counter).col.get(df.columns.get(counter).col.size() - 1))) {
                            v = new Value[columns.size()];
                            c = 0;
                            for (Column j : columns) {
                                v[c] = j.col.get(whereToStart);
                                c++;
                            }
                            try {
                                df.addRow(v);
                            } catch (CustomExpection e) {
                                e.print();
                            }
                            whereToStart++;
                            if (whereToStart == i.col.size()) break;
                        }
                        ldf.add(df);
                        whereToStart2 = whereToStart;
                        whereToStart++;
                    }
                    if(!i.col.get(i.col.size()-1).equals(ldf.get(ldf.size()-1).columns.get(counter).col.get(0))) {
                        DataFrame df = new DataFrame(names, types);
                        Value[] v = new Value[columns.size()];
                        int c = 0;
                        for (Column j : columns) {
                            v[c] = j.col.get(i.col.size()-1);
                            c++;
                        }
                        try {
                            df.addRow(v);
                        } catch (CustomExpection e) {
                            e.print();
                        }
                        ldf.add(df);
                    }
                } else {
                    DataFrame df = new DataFrame(names, types);
                    Value[] v = new Value[columns.size()];
                    int c = 0;
                    for (Column j : columns) {
                        v[c] = j.col.get(0);
                        c++;
                    }
                    try {
                        df.addRow(v);
                    } catch (CustomExpection e) {
                        e.print();
                    }
                    ldf.add(df);
                }
            }
            counter++;
        }
        DfList dfList = new DfList(ldf, new String[] {name});
        return dfList;
    }
    public DfList groupby(String[] names) {
        ArrayList<LinkedList<DataFrame>> arrayList = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            arrayList.add(null);
            if (i == 0) {
                arrayList.add(this.groupby(names[0]).ldf);
            } else {
                for (DataFrame df : arrayList.get(arrayList.size()-2)) {
                    arrayList.add(df.groupby(names[i]).ldf);
                }
            }
        }
        LinkedList<DataFrame> finalLL = new LinkedList<>();
        for (int i = arrayList.size() - 1 ; i > 0; i--) {
            if (arrayList.get(i) == null) {
                for (int j = i + 1; j < arrayList.size(); j++) {
                    for (DataFrame ll : arrayList.get(j)) {
                        finalLL.add(ll);
                    }
                }
            }
        }
        DfList dfList = new DfList(finalLL, names);
        return dfList;
    }}