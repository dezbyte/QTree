
public class Fibonacci {

    public int fibonacci(int n, int len)
    {
        if(len > 0) {
            System.out.println(n);
            this.fibonacci(this.fibonacci(1 + n, len - 1), len - 1);
        }
        return n;
    }

    public static void main(String[] args)
    {
        new Fibonacci().fibonacci(0, 10);
    }

}
