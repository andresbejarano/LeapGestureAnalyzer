package leapgestureanalyzer;

/**
 *
 * @author AndresMauricio
 */
public class Point {
    
    public float x;
    public float y;
    public float z;
    
    /**
     * 
     */
    public Point() {
        x = y = z = 0;
    }

    /**
     * 
     * @param x
     * @param y
     * @param z 
     */
    public Point(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    /**
     * 
     * @param x
     * @param y
     * @param z 
     */
    public Point(String x, String y, String z) {
        this.x = Float.parseFloat(x);
        this.y = Float.parseFloat(y);
        this.z = Float.parseFloat(z);
    }
    
}
