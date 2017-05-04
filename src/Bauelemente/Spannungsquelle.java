package Bauelemente;

/**
 * Created by Merve on 24.04.2017.
 */
public class Spannungsquelle extends Bauelement {

    public Spannungsquelle(int x, int y, int Orientation)
    {
        super(x,y,Orientation);

        System.out.println("Class Spann: "+posX+","+posY+","+Orientation);

    }
}
