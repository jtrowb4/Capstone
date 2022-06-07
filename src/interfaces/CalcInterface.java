package interfaces;
/**
 * class CalcInterface.java
 * interfaces
 * functional interface
 * @author James Trowbridge
 */
@FunctionalInterface
public interface CalcInterface {

    /**
     * abstract method that takes in two integers
     * for Lambda operation
     */
    int timeCalc(int n1, int n2);

}
