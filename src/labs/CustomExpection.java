package labs;

public class CustomExpection extends RuntimeException
{
    CustomExpection(String message)
    {
        super(message);
    }
    public void print() {

            System.out.println("Cannot perform this operation on: " );



    }
}
