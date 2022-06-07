package interfaces;
/**
 * class StringCreate.java
 * interfaces
 * functional interface
 * @author James Trowbridge
 */
@FunctionalInterface
public interface StringCreate {

    /**
     * abstract method that takes in two Strings
     * for Lambda operation
     */
    String Combine(String str1, String str2);
}
