package leapgestureanalyzer;

import be.ac.ulg.montefiore.run.jahmm.Hmm;
import be.ac.ulg.montefiore.run.jahmm.ObservationInteger;
import be.ac.ulg.montefiore.run.jahmm.OpdfIntegerFactory;
import be.ac.ulg.montefiore.run.jahmm.learn.KMeansLearner;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author AndresMauricio
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
    private final List<List<ObservationInteger>> mPinchSequences;
    private int nPinchTraining;
    private Hmm<ObservationInteger> mPinchHmm;
    
    //  The HMM for click gestures
    private final List<List<ObservationInteger>> mClickSequences;
    private int nClickTraining;
    private Hmm<ObservationInteger> mClickHmm;
    
    //  The HMM for stop gestures
    private final List<List<ObservationInteger>> mStopSequences;
    private int nStopTraining;
    private Hmm<ObservationInteger> mStopHmm;
    
    //  The HMM for wave gestures
    private final List<List<ObservationInteger>> mWaveSequences;
    private int nWaveTraining;
    private Hmm<ObservationInteger> mWaveHmm;

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
        //  Train the Pinch HMM
        KMeansLearner<ObservationInteger> mPinchKml = new KMeansLearner<>(
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
        
        //  Train the Click HMM
        KMeansLearner<ObservationInteger> mClickKml = new KMeansLearner<>(
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
        
        //  Train the Stop HMM
        KMeansLearner<ObservationInteger> mStopKml = new KMeansLearner<>(
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
        
        //  Train the Wave HMM
        KMeansLearner<ObservationInteger> mWaveKml = new KMeansLearner<>(
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
                    List<ObservationInteger> trainingSequence = new ArrayList<>();
                    int[] sequences = gesture.getObservationSequence(gesture.mPalmPosition);
                    writeSequence(sequences);
                    for(int j = 0; j < sequences.length; j += 1) 
                    {
                        trainingSequence.add(new ObservationInteger(sequences[j]));
                    }
                    mParent.write("Training sequence: " + trainingSequence.toString());
                    mPinchSequences.add(trainingSequence);
                    nPinchTraining += 1;
                    break;
                }
                
                case HandGesture.TYPE_CLICK:
                {
                    mParent.write(gesture.getName() + " used for training as a Click gesture");
                    List<ObservationInteger> trainingSequence = new ArrayList<>();
                    int[] sequences = gesture.getObservationSequence(gesture.mPalmPosition);
                    writeSequence(sequences);
                    for(int j = 0; j < sequences.length; j += 1) 
                    {
                        trainingSequence.add(new ObservationInteger(sequences[j]));
                    }
                    mParent.write("Training sequence: " + trainingSequence.toString());
                    mClickSequences.add(trainingSequence);
                    nClickTraining += 1;
                    break;
                }
                
                case HandGesture.TYPE_STOP:
                {
                    mParent.write(gesture.getName() + " used for training as a Stop gesture");
                    List<ObservationInteger> trainingSequence = new ArrayList<>();
                    int[] sequences = gesture.getObservationSequence(gesture.mPalmPosition);
                    writeSequence(sequences);
                    for(int j = 0; j < sequences.length; j += 1) 
                    {
                        trainingSequence.add(new ObservationInteger(sequences[j]));
                    }
                    mParent.write("Training sequence: " + trainingSequence.toString());
                    mStopSequences.add(trainingSequence);
                    nStopTraining += 1;
                    break;
                }
                
                case HandGesture.TYPE_WAVE:
                {
                    mParent.write(gesture.getName() + " used for training as a Wave gesture");
                    List<ObservationInteger> trainingSequence = new ArrayList<>();
                    int[] sequences = gesture.getObservationSequence(gesture.mPalmPosition);
                    writeSequence(sequences);
                    for(int j = 0; j < sequences.length; j += 1) 
                    {
                        trainingSequence.add(new ObservationInteger(sequences[j]));
                    }
                    mParent.write("Training sequence: " + trainingSequence.toString());
                    mWaveSequences.add(trainingSequence);
                    nWaveTraining += 1;
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
