package takeoutassistant.util;

public class DbException extends Exception {
    public DbException(java.lang.Throwable ex){
        super("���ݿ��������"+ex.getMessage());
    }
}
