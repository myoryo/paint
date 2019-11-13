package field;

public class SizeException extends Throwable {
    private String text;
    public SizeException(String string){
        text=string;
    }
    public String getMessage(){
        return text;
    }
}
