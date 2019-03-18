package exception;

/**
 * Created by minglang.xu on 2018/12/25.
 */
public enum ErrorCode {

  /**
   * 7位数字, 前三位大类型, 后四位小类型
   */
  SUCCESS(0, "Success."),

  FAILED(100_0001, "Failed."),
  INVALID_PARA(100_0002, "Failed."),
  NOT_FOUND(100_0003, "not found"),
  UNAUTHORIZED(100_0004, "unauthorized"),
  PARAM_ERROR(100_0005, "parameter error");

  private int code;
  private String msg;

  public int getCode() {
    return code;
  }

  public String getMsg() {
    return msg;
  }

  ErrorCode(int code, String msg) {
    this.code = code;
    this.msg = msg;
  }

}