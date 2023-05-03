import java.net.*;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Server {
  public static void handleRequest(Socket sock) {
    try {
        InputStream request = sock.getInputStream();
        ObjectInputStream ois = new ObjectInputStream(request);
        RemoteEcho requestMethod = (RemoteEcho) ois.readObject();

        String methodName = requestMethod.getMethodName();
        Object[] args = requestMethod.getArgs();

        // find the right method
        Method[] availableMethods = new Server().getClass().getMethods();
        Method callMethod = null;
        for (Method method : availableMethods) {
            if (method.getName().equals(methodName)) {
                callMethod = method;
                break;
            }
        }

        // call method
        Object result = null;
        if (callMethod == null) {
            result = new Exception("Couldn't find this method: " + methodName);
        } else {
            // result = callMethod.invoke(null, args);
            try {
                result = callMethod.invoke(null, args);
            } catch (InvocationTargetException e) {
                result = e.getCause();
            }
        }

        // Send out the response
        OutputStream response = sock.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(response);
        oos.writeObject(result);
    } catch (Exception e) {
        e.printStackTrace();
    }
  }

    // open a server socket
    public static void main(String[] args) throws Exception{
        ServerSocket server = new ServerSocket(10314);
        System.out.println("Listening for connection on port 10314 ...");
        Socket socket = null;

        while((socket = server.accept()) != null) {
            final Socket threadSocket = socket;
            new Thread( () -> handleRequest(threadSocket)).start();
        }

        System.out.println("closed");
        server.close();
    }

    // Do not modify any code below tihs line
    // --------------------------------------
    public static String echo(String message) {
        return "You said " + message + "!";
    }
    public static int add(int lhs, int rhs) {
        return lhs + rhs;
    }
    public static int divide(int num, int denom) {
        if (denom == 0)
            throw new ArithmeticException();

        return num / denom;
    }
}