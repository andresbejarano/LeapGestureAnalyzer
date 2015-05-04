package leapgestureanalyzer;

import be.ac.ulg.montefiore.run.jahmm.Hmm;
import be.ac.ulg.montefiore.run.jahmm.ObservationDiscrete;
import be.ac.ulg.montefiore.run.jahmm.OpdfDiscreteFactory;
import be.ac.ulg.montefiore.run.jahmm.learn.BaumWelchLearner;
import java.util.ArrayList;
import java.util.List;
import leapgestureanalyzer.HandGesture.Direction;

/**
 *
 * @author AndresMauricio
 * 
 * Examples on:
 * https://code.google.com/p/jahmm/wiki/Example
 * http://ranger.uta.edu/~huber/cse4392_SmartHome/Hwk/Tutorial.html
 */
public class HMMFrame extends javax.swing.JInternalFrame 
{
    //  Reference to the main class of the application
    private final LeapGestureAnalyzer mParent;
    
    //  The gestures in the current analysis
    private final ArrayList<HandGesture> mHandGestures;
    
    //  The confussion matrix of the analysis
    private float[][] mConfussionMatrix;
    
    //  The HMM for pinch gestures
    private final List<List<ObservationDiscrete<Direction>>> mPinchSequences;
    private Hmm<ObservationDiscrete<Direction>> mPinchHmm;
    
    //  The HMM for click gestures
    private final List<List<ObservationDiscrete<Direction>>> mClickSequences;
    private Hmm<ObservationDiscrete<Direction>> mClickHmm;
    
    //  The HMM for stop gestures
    private final List<List<ObservationDiscrete<Direction>>> mStopSequences;
    private Hmm<ObservationDiscrete<Direction>> mStopHmm;
    
    //  The HMM for wave gestures
    private final List<List<ObservationDiscrete<Direction>>> mWaveSequences;
    private Hmm<ObservationDiscrete<Direction>> mWaveHmm;

    /**
     * 
     * @param parent 
     */
    public HMMFrame(LeapGestureAnalyzer parent) 
    {
        mParent = parent;
        mHandGestures = mParent.getHandGestures();
        
        //  Init the GUI components
        initComponents();
        
        //  Initialize sequences
        mPinchSequences = new ArrayList<>();
        mClickSequences = new ArrayList<>();
        mStopSequences = new ArrayList<>();
        mWaveSequences = new ArrayList<>();
        
        int n = mHandGestures.size();
        mParent.write("Found: " + n + " gestures for analysis");
        
        if(n <= 0) 
        {
            mParent.write("No analysis can be performed. At least one gesture is required");
            return;
        }
        
        mParent.write("Building HMMs with " + mParent.getNumStates() + " states");
        
        //  Set up the HMM analysis
        setTrainingSequences();
        buildHmm();
    }
    
    /**
     * 
     * @param sequence 
     */
    private void writeSequence(int[] sequence) {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < sequence.length; i += 1) 
        {
            builder.append(sequence[i]).append(",");
        }
        mParent.write("Sequence: " + builder.toString());
    }
    
    /**
     * 
     */
    private void buildHmm() 
    {
        // Train the Pinch HMM
        int nStates = mParent.getNumStates();
        
        Hmm mInitPinchHmm = new Hmm<>(nStates, new OpdfDiscreteFactory<>(Direction.class));
        BaumWelchLearner mPinchBwl = new BaumWelchLearner();
        mPinchHmm = mPinchBwl.learn(mInitPinchHmm, mPinchSequences);
        mParent.write("Pinch HMM learned from Baum-Welch");
        
        Hmm mInitClickHmm = new Hmm<>(nStates, new OpdfDiscreteFactory<>(Direction.class));
        BaumWelchLearner mClickBwl = new BaumWelchLearner();
        mClickHmm = mClickBwl.learn(mInitClickHmm, mClickSequences);
        mParent.write("Click HMM learned from Baum-Welch");
        
        Hmm mInitStopHmm = new Hmm<>(nStates, new OpdfDiscreteFactory<>(Direction.class));
        BaumWelchLearner mStopBwl = new BaumWelchLearner();
        mStopHmm = mStopBwl.learn(mInitStopHmm, mStopSequences);
        mParent.write("Stop HMM learned from Baum-Welch");
        
        Hmm mInitWaveHmm = new Hmm<>(nStates, new OpdfDiscreteFactory<>(Direction.class));
        BaumWelchLearner mWaveBwl = new BaumWelchLearner();
        mWaveHmm = mWaveBwl.learn(mInitWaveHmm, mWaveSequences);
        mParent.write("Wave HMM learned from Baum-Welch");
        
        /*
        KMeansLearner mPinchKml;
        mPinchKml = new KMeansLearner(
                mParent.getNumStates(),
                new OpdfIntegerFactory(8), 
                mPinchSequences);
        mParent.write("Initiating KMeans learner for Pinch's HMM");
        mPinchHmm = mPinchKml.learn();
        mParent.write("Pinch's HMM learned from KMeans");
        //BaumWelchLearner mPinchBwl = new BaumWelchLearner();
        //mPinchHmm = mPinchBwl.learn(mPinchHmm, mPinchSequences);
        mParent.write("Pinch's HMM learned from Baum-Welch");
        mParent.write("HMM for Pinch gesture trained");
        */
        
        //  Train the Click HMM
        /*
        KMeansLearner mClickKml;
        mClickKml = new KMeansLearner(
                mParent.getNumStates(),
                new OpdfIntegerFactory(8), 
                mClickSequences);
        mParent.write("Initiating KMeans learner for Click's HMM");
        mClickHmm = mClickKml.learn();
        mParent.write("Click's HMM learned from KMeans");
        //BaumWelchLearner mClickBwl = new BaumWelchLearner();
        //mClickHmm = mClickBwl.learn(mClickHmm, mClickSequences);
        mParent.write("Click's HMM learned from Baum-Welch");
        mParent.write("HMM for Click gesture trained");
        */
        
        //  Train the Stop HMM
        /*
        KMeansLearner mStopKml = new KMeansLearner(
                mParent.getNumStates(),
                new OpdfIntegerFactory(8), 
                mStopSequences);
        mParent.write("Initiating KMeans learner for Stop's HMM");
        mStopHmm = mStopKml.learn();
        mParent.write("Stop's HMM learned from KMeans");
        //BaumWelchLearner mStopBwl = new BaumWelchLearner();
        //mStopHmm = mStopBwl.learn(mStopHmm, mStopSequences);
        mParent.write("Stop's HMM learned from Baum-Welch");
        mParent.write("HMM for Stop gesture trained");
        */
        
        //  Train the Wave HMM
        /*
        KMeansLearner mWaveKml = new KMeansLearner(
                mParent.getNumStates(),
                new OpdfIntegerFactory(8), 
                mWaveSequences);
        mParent.write("Initiating KMeans learner for Wave's HMM");
        mWaveHmm = mWaveKml.learn();
        mParent.write("Wave's HMM learned from KMeans");
        //BaumWelchLearner mWaveBwl = new BaumWelchLearner();
        //mWaveHmm = mWaveBwl.learn(mWaveHmm, mWaveSequences);
        mParent.write("Wave's HMM learned from Baum-Welch");
        mParent.write("HMM for Wave gesture trained");
        */
    }
    
    /**
     * 
     */
    private void setTrainingSequences() 
    {
        //  Train with the sequences found for each gesture
        int n = mHandGestures.size();
        for(int i = 0; i < n; i += 1) 
        {
            //  Get the gesture object
            HandGesture gesture = mHandGestures.get(i);
            
            //  Add the training sequence to the respective list
            switch(gesture.getGestureType()) 
            {
                case HandGesture.TYPE_PINCH:
                {
                    mParent.write(gesture.getName() + " used for training as a Pinch gesture");
                    List<ObservationDiscrete<Direction>> list = gesture.getDiscreteObservationSequence(gesture.mPalmPosition);
                    mPinchSequences.add(gesture.getDiscreteObservationSequence(gesture.mPalmPosition));
                    mParent.write("Training sequence: " + list.toString());
                    break;
                }
                
                case HandGesture.TYPE_CLICK:
                {
                    mParent.write(gesture.getName() + " used for training as a Click gesture");
                    List<ObservationDiscrete<Direction>> list = gesture.getDiscreteObservationSequence(gesture.mPalmPosition);
                    mClickSequences.add(gesture.getDiscreteObservationSequence(gesture.mPalmPosition));
                    mParent.write("Training sequence: " + list.toString());
                    break;
                }
                
                case HandGesture.TYPE_STOP:
                {
                    mParent.write(gesture.getName() + " used for training as a Stop gesture");
                    List<ObservationDiscrete<Direction>> list = gesture.getDiscreteObservationSequence(gesture.mPalmPosition);
                    mStopSequences.add(gesture.getDiscreteObservationSequence(gesture.mPalmPosition));
                    mParent.write("Training sequence: " + list.toString());
                    break;
                }
                
                case HandGesture.TYPE_WAVE:
                {
                    mParent.write(gesture.getName() + " used for training as a Wave gesture");
                    List<ObservationDiscrete<Direction>> list = gesture.getDiscreteObservationSequence(gesture.mPalmPosition);
                    mWaveSequences.add(gesture.getDiscreteObservationSequence(gesture.mPalmPosition));
                    mParent.write("Training sequence: " + list.toString());
                    break;
                }
                
                default:
                    mParent.write("No suitable gesture type for a gesture");
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        mGeneralInfoTextArea = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        mConfussionTable = new javax.swing.JTable();

        setTitle("Gesture Analysis");

        jLabel1.setText("Information about gestures:");

        mGeneralInfoTextArea.setEditable(false);
        mGeneralInfoTextArea.setColumns(20);
        mGeneralInfoTextArea.setRows(5);
        jScrollPane1.setViewportView(mGeneralInfoTextArea);

        mConfussionTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(mConfussionTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 561, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable mConfussionTable;
    private javax.swing.JTextArea mGeneralInfoTextArea;
    // End of variables declaration//GEN-END:variables
}
