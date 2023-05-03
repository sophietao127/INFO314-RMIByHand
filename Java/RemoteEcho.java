import java.io.Serializable;

public class RemoteEcho implements Serializable {
  private String methodName;
  private Object[] args;

  public RemoteEcho(String methodName, Object[] args) {
    this.methodName = methodName;
    this.args = args;
  }

  // get method
  public String getMethodName() {
    return methodName;
  }

  public Object[] getArgs() {
    return this.args;
  }
}
