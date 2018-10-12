package labs;

public class Punkt2D{

    protected double x,y;

    Punkt2D(double x, double y){
        this.x=x;
        this.y=y;
    }

    public double GetX() {
        return x;
    }

    public double GetY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
}