package by.fpmibsu.find_a_friend.data_access_layer;

public class DaoException extends Exception{
    public DaoException(){
    }
    public DaoException(String message){
        super(message);
    }
    public DaoException(String message, Throwable cause){
        super(message, cause);
    }
}
