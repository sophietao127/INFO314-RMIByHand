import java.io.*;
import java.net.*;

public class Client {
  public static Object makeRequest(RemoteEcho requestMethod) {
    try (Socket sock = new Socket("localhost", PORT);) {
        OutputStream sockOut = sock.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(sockOut);
        oos.writeObject(requestMethod);

        InputStream sockIn = sock.getInputStream();
        ObjectInputStream ois = new ObjectInputStream(sockIn);
        // sock.close();

        // send the response
        Object response = ois.readObject();
        if (response instanceof Exception) throw (Exception) response;
        return response;
    }
    catch (Exception e) {
        return e;
    }
  }
    /**
     * This method name and parameters must remain as-is
     */
    public static int add(int lhs, int rhs) {
        RemoteEcho add = new RemoteEcho("add", new Object[]{lhs, rhs});
        Object response = makeRequest(add);
        return (int)response;
    }
    /**
     * This method name and parameters must remain as-is
     */
    public static int divide(int num, int denom) {
        RemoteEcho divide = new RemoteEcho("divide", new Object[]{num, denom});
        Object response = makeRequest(divide);
        if (response instanceof ArithmeticException) throw (ArithmeticException) response;
        return (int) response;
    }
    /**
     * This method name and parameters must remain as-is
     */
    public static String echo(String message) {
        RemoteEcho echo = new RemoteEcho("echo", new Object[]{message});
        Object response = makeRequest(echo);
        return (String)response;
    }

    // Do not modify any code below this line
    // --------------------------------------
    String server = "localhost";
    public static final int PORT = 10314;

    public static void main(String... args) {
        // All of the code below this line must be uncommented
        // to be successfully graded.
        System.out.print("Testing... ");

        if (add(2, 4) == 6)
            System.out.print(".");
        else
            System.out.print("X");

        try {
            divide(1, 0);
            System.out.print("X");
        }
        catch (ArithmeticException x) {
            System.out.print(".");
        }

        if (echo("Hello").equals("You said Hello!"))
            System.out.print(".");
        else
            System.out.print("X");

        System.out.println(" Finished");
    }
}