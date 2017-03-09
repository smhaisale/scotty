package scotty.util;

public class SystemUtils {

    public static void log(Object obj, String message) {
        System.out.println(obj.getClass() + ":\t" + message);
    }

}
