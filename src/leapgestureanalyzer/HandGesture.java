package leapgestureanalyzer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author AndresMauricio
 */
public final class HandGesture 
{    
    //  The main class of the application
    private LeapGestureAnalyzer mParent;
    
    //  The name of the file of the gesture
    private String mGestureName;
    
    //  The different types of gesture
    public static final int TYPE_NONE = 0;
    public static final int TYPE_PINCH = 1;
    public static final int TYPE_CLICK = 2;
    public static final int TYPE_STOP = 3;
    public static final int TYPE_WAVE = 4;
    
    //  The names of the different types of gestures
    public static final String TYPE_NONE_NAME = "None";
    public static final String TYPE_PINCH_NAME = "Pinch";
    public static final String TYPE_CLICK_NAME = "Click";
    public static final String TYPE_STOP_NAME = "Stop";
    public static final String TYPE_WAVE_NAME = "Wave";
    
    //	The timestamp of the frames
    public ArrayList<Float> mTimestamp;

    //	The information of the hand
    public ArrayList<Point> mPalmPosition;
    public ArrayList<Point> mPalmNormal;
    public ArrayList<Point> mPalmVelocity;
    public ArrayList<Point> mHandDirection;
    public ArrayList<Float> mPinchStrength;
    public ArrayList<Float> mGrabStrength;

    //	The information of the arm
    public ArrayList<Point> mWristPosition;
    public ArrayList<Point> mElbowPosition;
    public ArrayList<Point> mArmDirection;

    //	The information of the Thumb finger
    public ArrayList<Point> mThumbDirection;
    public ArrayList<Point> mThumbTipPosition;
    public ArrayList<Point> mThumbMetacarpalPrevJoint;
    public ArrayList<Point> mThumbProximalPrevJoint;
    public ArrayList<Point> mThumbIntermediatePrevJoint;
    public ArrayList<Point> mThumbDistalPrevJoint;

    //	The information of the Index finger
    public ArrayList<Point> mIndexDirection;
    public ArrayList<Point> mIndexTipPosition;
    public ArrayList<Point> mIndexMetacarpalPrevJoint;
    public ArrayList<Point> mIndexProximalPrevJoint;
    public ArrayList<Point> mIndexIntermediatePrevJoint;
    public ArrayList<Point> mIndexDistalPrevJoint;

    //	The information of the Middle finger
    public ArrayList<Point> mMiddleDirection;
    public ArrayList<Point> mMiddleTipPosition;
    public ArrayList<Point> mMiddleMetacarpalPrevJoint;
    public ArrayList<Point> mMiddleProximalPrevJoint;
    public ArrayList<Point> mMiddleIntermediatePrevJoint;
    public ArrayList<Point> mMiddleDistalPrevJoint;

    //	The information of the Ring finger
    public ArrayList<Point> mRingDirection;
    public ArrayList<Point> mRingTipPosition;
    public ArrayList<Point> mRingMetacarpalPrevJoint;
    public ArrayList<Point> mRingProximalPrevJoint;
    public ArrayList<Point> mRingIntermediatePrevJoint;
    public ArrayList<Point> mRingDistalPrevJoint;

    //	The information of the Pinky finger
    public ArrayList<Point> mPinkyDirection;
    public ArrayList<Point> mPinkyTipPosition;
    public ArrayList<Point> mPinkyMetacarpalPrevJoint;
    public ArrayList<Point> mPinkyProximalPrevJoint;
    public ArrayList<Point> mPinkyIntermediatePrevJoint;
    public ArrayList<Point> mPinkyDistalPrevJoint;
    
    //  Indicates if the file was loaded successfully
    private boolean mIsFileLoaded;
    
    //  The file with the content of the hand gesture
    public File mFile;
    
    //  Indicates the type of gesture
    public int mGestureType;

    /**
     * 
     * @param parent
     * @param file 
     */
    public HandGesture(LeapGestureAnalyzer parent, File file) 
    {
        //  Save the reference to the main class
        mParent = parent;
        
        mGestureName = file.getName();
        
        initArrays();
        
        //  Indicates that the file hasn't been loaded
        mIsFileLoaded = false;
        
        //  Save the reference to the file
        mFile = file;
        
        //  Read the file
        try (BufferedReader reader = new BufferedReader(new FileReader(file)))
        {
            //  Read the file line by line
            String line;
            int lineCount = 0;
            while((line = reader.readLine()) != null) 
            {
                String[] rawValues = line.split(";");
                
                //  If the raw value array is not of 114 positions
                if(rawValues.length != 114) 
                {
                    mParent.write("Not enough data on line " + lineCount);
                }
                
                if(lineCount == 0) 
                {
                    lineCount += 1;
                    continue;
                }
                
                //  Save the information of the timestamp
                mTimestamp.add(new Float(rawValues[0]));
                
                //  Save the information of the hand
                mPalmPosition.add(new Point(rawValues[1], rawValues[2], rawValues[3]));
                mPalmNormal.add(new Point(rawValues[4], rawValues[5], rawValues[6]));
                mPalmVelocity.add(new Point(rawValues[7], rawValues[8], rawValues[9]));
                mHandDirection.add(new Point(rawValues[10], rawValues[11], rawValues[12]));
                mPinchStrength.add(new Float(rawValues[13]));
                mGrabStrength.add(new Float(rawValues[14]));

                //  The information of the arm
                mWristPosition.add(new Point(rawValues[15], rawValues[16], rawValues[17]));
                mElbowPosition.add(new Point(rawValues[18], rawValues[19], rawValues[20]));
                mArmDirection.add(new Point(rawValues[21], rawValues[22], rawValues[23]));

                //  The information of the Thumb finger
                mThumbDirection.add(new Point(rawValues[24], rawValues[25], rawValues[26]));
                mThumbTipPosition.add(new Point(rawValues[27], rawValues[28], rawValues[29]));
                mThumbMetacarpalPrevJoint.add(new Point(rawValues[30], rawValues[31], rawValues[32]));
                mThumbProximalPrevJoint.add(new Point(rawValues[33], rawValues[34], rawValues[35]));
                mThumbIntermediatePrevJoint.add(new Point(rawValues[36], rawValues[37], rawValues[38]));
                mThumbDistalPrevJoint.add(new Point(rawValues[39], rawValues[40], rawValues[41]));

                //  The information of the Index finger
                mIndexDirection.add(new Point(rawValues[42], rawValues[43], rawValues[44]));
                mIndexTipPosition.add(new Point(rawValues[45], rawValues[46], rawValues[47]));
                mIndexMetacarpalPrevJoint.add(new Point(rawValues[48], rawValues[49], rawValues[50]));
                mIndexProximalPrevJoint.add(new Point(rawValues[51], rawValues[52], rawValues[53]));
                mIndexIntermediatePrevJoint.add(new Point(rawValues[54], rawValues[55], rawValues[56]));
                mIndexDistalPrevJoint.add(new Point(rawValues[57], rawValues[58], rawValues[59]));

                //  The information of the Middle finger
                mMiddleDirection.add(new Point(rawValues[60], rawValues[61], rawValues[62]));
                mMiddleTipPosition.add(new Point(rawValues[63], rawValues[64], rawValues[65]));
                mMiddleMetacarpalPrevJoint.add(new Point(rawValues[66], rawValues[67], rawValues[68]));
                mMiddleProximalPrevJoint.add(new Point(rawValues[69], rawValues[70], rawValues[71]));
                mMiddleIntermediatePrevJoint.add(new Point(rawValues[72], rawValues[73], rawValues[74]));
                mMiddleDistalPrevJoint.add(new Point(rawValues[75], rawValues[76], rawValues[77]));

                //  The information of the Ring finger
                mRingDirection.add(new Point(rawValues[78], rawValues[79], rawValues[80]));
                mRingTipPosition.add(new Point(rawValues[81], rawValues[82], rawValues[83]));
                mRingMetacarpalPrevJoint.add(new Point(rawValues[84], rawValues[85], rawValues[86]));
                mRingProximalPrevJoint.add(new Point(rawValues[87], rawValues[88], rawValues[89]));
                mRingIntermediatePrevJoint.add(new Point(rawValues[90], rawValues[91], rawValues[92]));
                mRingDistalPrevJoint.add(new Point(rawValues[93], rawValues[94], rawValues[95]));

                //  The information of the Pinky finger
                mPinkyDirection.add(new Point(rawValues[96], rawValues[97], rawValues[98]));
                mPinkyTipPosition.add(new Point(rawValues[99], rawValues[100], rawValues[101]));
                mPinkyMetacarpalPrevJoint.add(new Point(rawValues[102], rawValues[103], rawValues[104]));
                mPinkyProximalPrevJoint.add(new Point(rawValues[105], rawValues[106], rawValues[107]));
                mPinkyIntermediatePrevJoint.add(new Point(rawValues[108], rawValues[109], rawValues[110]));
                mPinkyDistalPrevJoint.add(new Point(rawValues[111], rawValues[112], rawValues[113]));
                
                lineCount += 1;
            }
            
            //  Indicates that the file was successfully loaded
            mIsFileLoaded = true;
            
            //  Set the type of gesture based on the name of the file
            mGestureType = TYPE_NONE;
            String[] nameSegments = file.getName().split(" ");
            switch (nameSegments[0]) {
                case "pinch":
                    mGestureType = TYPE_PINCH;
                    break;
                case "click":
                    mGestureType = TYPE_CLICK;
                    break;
                case "stop":
                    mGestureType = TYPE_STOP;
                    break;
                case "wave":
                    mGestureType = TYPE_WAVE;
                    break;
                default:
                    mGestureType = TYPE_NONE;
            }
            
            mParent.write(mGestureName + " is of type " + getGestureTypeName(mGestureType));
            mParent.write("Hand gesture from " + file.getName() + " loaded successfully");
        }
        catch(FileNotFoundException e) 
        {
            mParent.write("File not found: " + e.getMessage());
        }
        catch(IOException e) 
        {
            mParent.write("Error while reading file: " + e.getMessage());
        }
    }
    
    /**
     * Adjust the data to the specified number of frames 
     * @param array
     * @param N
     */
    public void adjustArray(ArrayList<Point> array, int N) 
    {
        //  Generate the new array
        ArrayList<Point> newArray = new ArrayList<>(N);
        
        //  Copy the data from the old array to the new array
        int n = array.size();
        for(int i = 0; i < n; i += 1) 
        {
            int pos;
            pos = (int)Commons.map(i, 0, n, 0, N);
            newArray.add(pos, array.get(i));
        }
        
        //  Fill null spaces
        for(int i = 0; i < N - 1; i += 1) 
        {
            //  If the current index is null
            if(newArray.get(i) == null) 
            {
                //  Find the next not null index
                int j = i + 1;
                while(newArray.get(j) == null && j < N - 1) 
                {
                    j += 1;
                }
                
                //  If no next not null index then exit the for loop
                if(j == N - 1) 
                {
                    break;
                }
                
                Point value = new Point();
                value.x = (newArray.get(j).x - newArray.get(i - 1).x) / (j - (i - 1));
                value.y = (newArray.get(j).y - newArray.get(i - 1).y) / (j - (i - 1));
                value.z = (newArray.get(j).z - newArray.get(i - 1).z) / (j - (i - 1));
                
                //  Fill the null positions
                for(int k = i; k < j; k += 1) 
                {
                    Point newPoint = new Point(newArray.get(k - 1));
                    newPoint.add(value);
                    newArray.add(k, newPoint);
                }
                
                //  Continue after the last not null known position
                i = j;
            }
        }
        
        //  Check if the last position is null
        if(newArray.get(N - 1) == null) 
        {
            //  Calculate the values of the point on the last position
            Point newPoint = new Point();
            newPoint.x = newArray.get(N - 2).x - newArray.get(N - 3).x;
            newPoint.y = newArray.get(N - 2).y - newArray.get(N - 3).y;
            newPoint.z = newArray.get(N - 2).z - newArray.get(N - 3).z;
            newPoint.add(newArray.get(N - 2));
            
            //  Add the new point on the last position
            newArray.add(N - 1, newPoint);
        }
        
        //  Replace the old array with the new array
        array = newArray;
    }
    
    /**
     * 
     * @return 
     */
    public String getName() 
    {
        return mGestureName;
    }
    
    /**
     * Return the number of frames of the gesture
     * @return 
     */
    public int getNumFrames() 
    {
        return mTimestamp.size();
    }
    
    /**
     * 
     * @return 
     */
    boolean isFileLoaded() 
    {
        return mIsFileLoaded;
    }
    
    /**
     * 
     */
    public void initArrays() 
    {
        mTimestamp = new ArrayList<>();

        //	The information of the hand
        mPalmPosition = new ArrayList<>();
        mPalmNormal = new ArrayList<>();
        mPalmVelocity = new ArrayList<>();
        mHandDirection = new ArrayList<>();
        mPinchStrength = new ArrayList<>();
        mGrabStrength = new ArrayList<>();

        //	The information of the arm
        mWristPosition = new ArrayList<>();
        mElbowPosition = new ArrayList<>();
        mArmDirection = new ArrayList<>();

        //	The information of the Thumb finger
        mThumbDirection = new ArrayList<>();
        mThumbTipPosition = new ArrayList<>();
        mThumbMetacarpalPrevJoint = new ArrayList<>();
        mThumbProximalPrevJoint = new ArrayList<>();
        mThumbIntermediatePrevJoint = new ArrayList<>();
        mThumbDistalPrevJoint = new ArrayList<>();

        //	The information of the Index finger
        mIndexDirection = new ArrayList<>();
        mIndexTipPosition = new ArrayList<>();
        mIndexMetacarpalPrevJoint = new ArrayList<>();
        mIndexProximalPrevJoint = new ArrayList<>();
        mIndexIntermediatePrevJoint = new ArrayList<>();
        mIndexDistalPrevJoint = new ArrayList<>();

        //	The information of the Middle finger
        mMiddleDirection = new ArrayList<>();
        mMiddleTipPosition = new ArrayList<>();
        mMiddleMetacarpalPrevJoint = new ArrayList<>();
        mMiddleProximalPrevJoint = new ArrayList<>();
        mMiddleIntermediatePrevJoint = new ArrayList<>();
        mMiddleDistalPrevJoint = new ArrayList<>();

        //	The information of the Ring finger
        mRingDirection = new ArrayList<>();
        mRingTipPosition = new ArrayList<>();
        mRingMetacarpalPrevJoint = new ArrayList<>();
        mRingProximalPrevJoint = new ArrayList<>();
        mRingIntermediatePrevJoint = new ArrayList<>();
        mRingDistalPrevJoint = new ArrayList<>();

        //	The information of the Pinky finger
        mPinkyDirection = new ArrayList<>();
        mPinkyTipPosition = new ArrayList<>();
        mPinkyMetacarpalPrevJoint = new ArrayList<>();
        mPinkyProximalPrevJoint = new ArrayList<>();
        mPinkyIntermediatePrevJoint = new ArrayList<>();
        mPinkyDistalPrevJoint = new ArrayList<>();
    }
    
    /**
     * 
     * @param type 
     */
    public void setGestureType(int type) 
    {
        switch(type) 
        {
            case TYPE_PINCH:
                mGestureType = TYPE_PINCH;
                break;
            
            case TYPE_CLICK:
                mGestureType = TYPE_CLICK;
                break;
                
            case TYPE_STOP:
                mGestureType = TYPE_STOP;
                break;
                
            case TYPE_WAVE:
                mGestureType = TYPE_WAVE;
                break;
                
            default:
                mGestureType = TYPE_NONE;
        }
    }
    
    /**
     * 
     * @return 
     */
    public int getGestureType() 
    {
        return mGestureType;
    }
    
    /**
     * 
     * @param array
     * @return 
     */
    public int[] getObservationSequence(ArrayList<Point> array) 
    {
        // Get the size of the gesture
        int n = array.size();
        int[] B = new int[n];
        
        for(int i = 1; i < n; i += 1) 
        {
            // Get the previous and current vectors
            Point v0 = array.get(i - 1);
            Point v1 = array.get(i);
            
            // Get the difference between both vector
            double x = v1.x - v0.x;
            double y = v1.y - v0.y;
            double z = v1.z - v0.z;
            
            // Calculate the emission according to the difference of direction
            if(x >= 0) 
            {
                if(y >= 0) 
                {
                    if(z >= 0) 
                    {
                        B[i] = 0;
                    }
                    else 
                    {
                        B[i] = 1;
                    }
                }
                else 
                {
                    if(z >= 0) 
                    {
                        B[i] = 2;
                    }
                    else 
                    {
                        B[i] = 3;
                    }
                }
            }
            else 
            {
                if(y >= 0) 
                {
                    if(z >= 0) 
                    {
                        B[i] = 4;
                    }
                    else 
                    {
                        B[i] = 5;
                    }
                }
                else 
                {
                    if(z >= 0) 
                    {
                        B[i] = 6;
                    }
                    else 
                    {
                        B[i] = 7;
                    }
                }	
            }
        }
        
        return B;
    }
    
    /**
     *  
     * @return 
     */
    public float getDurationInSecond() 
    {
        int n = mTimestamp.size();
        return (float)((mTimestamp.get(n - 1).longValue() - mTimestamp.get(0).longValue()) / 1000000.0f);
    }
    
    /**
     * 
     * @param type
     * @return 
     */
    public static final String getGestureTypeName(int type) 
    {
        switch(type) 
        {
            case TYPE_PINCH:
                return TYPE_PINCH_NAME;
                
            case TYPE_CLICK:
                return TYPE_CLICK_NAME;
                
            case TYPE_STOP:
                return TYPE_STOP_NAME;
                
            case TYPE_WAVE:
                return TYPE_WAVE_NAME;
                
            default:
                return TYPE_NONE_NAME;
        }
    }
    
}
