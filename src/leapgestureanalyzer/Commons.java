package leapgestureanalyzer;

/**
 *
 * @author AndresMauricio
 */
public class Commons {
    
    /**
     * From: https://github.com/processing/processing/blob/master/core/src/processing/core/PApplet.java
     * @param value
     * @param start1
     * @param stop1
     * @param start2
     * @param stop2
     * @return 
     */
    public static float map(float value, float start1, float stop1, float start2, float stop2) 
    {
        return start2 + (stop2 - start2) * ((value - start1) / (stop1 - start1));
    }
    
}
