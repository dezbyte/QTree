import org.dezzPro.app.engine.geometry.Vector2D;

public class TestVector {

    public static void main(String[] args)
    {
        Vector2D vector2D = new Vector2D(3, 4);

        System.out.println(vector2D.distance(new Vector2D(4, 5)));
        System.out.println(vector2D.length());
//        System.out.println(vector2D.normalize().length());

        Vector2D vectorG = new Vector2D(1, 3);
        Vector2D vectorG2 = new Vector2D(1, 3);
        Vector2D vectorD = new Vector2D(1, 1);
        Vector2D vectorH = new Vector2D(3, 2);

//        Vector2D vectorV = vectorH.subtract(vectorG).normalize();

        System.out.println(vectorG2.normalize().subtract(vectorD.normalize()).normalize());

//        System.out.println(Math.acos(vectorD.normalize().dot(vectorV)) * (180 / Math.PI));
//        System.out.println(new Vector2D(4, 2).subtract(new Vector2D(1, 4)));

    }

}
