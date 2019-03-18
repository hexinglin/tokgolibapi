package exception;


/**
 * Created by xinglin.he on 2018/12/25.
 */
public class TOKGOException extends Exception {
  private int code;
  private String msg;

  public TOKGOException(int code, String msg) {
    super(msg);
    this.code = code;
    this.msg = msg;
  }

  public TOKGOException(ErrorCode errorCode) {
    super(errorCode.getMsg());
    this.code = errorCode.getCode();
    this.msg = errorCode.getMsg();
  }

  public TOKGOException(ErrorCode errorCode, String errorMessage) {
    super(errorMessage);
    this.code = errorCode.getCode();
    this.msg = errorCode.getMsg();
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public int getCode() {
    return this.code;
  }

  public String getMsg() {
    return msg;
  }

}