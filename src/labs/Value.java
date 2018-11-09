package labs;

import java.lang.reflect.InvocationTargetException;

public abstract class Value implements Cloneable,Comparable<Value>{
    public abstract String toString();
    public abstract Value add(Value value);
    public abstract Value sub(Value value);
    public abstract Value mul(Value value);
    public abstract Value div(Value value);
    public abstract Value pow(Value value);
    public abstract boolean eq(Value value);
    public abstract boolean lte(Value value);
    public abstract boolean gte(Value value);
    public abstract boolean neq(Value value);
    public abstract boolean equals(Object other);
    public abstract int hashCode();
    public abstract Value create(String s);
    public abstract Object getValue();

    public static ValueBuilder builder(Class<? extends Value> type){
        return new ValueBuilder(type);
    }

    @Override
    public abstract int compareTo(Value o);

    public static class ValueBuilder{
        Class<? extends Value> classOfValue;

        public ValueBuilder(Class<? extends Value> classToBuild){
            classOfValue = classToBuild;
        }

        public Value build(String content) {
            try {
                return (Value) classOfValue.getMethod("create", String.class).invoke(classOfValue.newInstance(), content);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
