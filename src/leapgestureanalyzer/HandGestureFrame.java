package leapgestureanalyzer;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author AndresMauricio
 */
public class HandGestureFrame extends javax.swing.JInternalFrame 
{
    //  The column names for the table
    public static final String[] columnNames = {
            "timestamp",
            "PalmPosition.x","PalmPosition.y","PalmPosition.z",
            "PalmNormal.x","PalmNormal.y","PalmNormal.z",
            "PalmVelocity.x","PalmVelocity.y","PalmVelocity.z",
            "HandDirection.x","HandDirection.y","HandDirection.z",
            "PinchStrength",
            "GrabStrength",
            "WristPosition.x","WristPosition.y","WristPosition.z",
            "ElbowPosition.x","ElbowPosition.y","ElbowPosition.z",
            "ArmDirection.x","ArmDirection.y","ArmDirection.z",
            "ThumbDirection.x","ThumbDirection.y","ThumbDirection.z",
            "ThumbTipPosition.x","ThumbTipPosition.y","ThumbTipPosition.z",
            "ThumbMetacarpalPrevJoint.x","ThumbMetacarpalPrevJoint.y","ThumbMetacarpalPrevJoint.z",
            "ThumbProximalPrevJoint.x","ThumbProximalPrevJoint.y","ThumbProximalPrevJoint.z",
            "ThumbIntermediatePrevJoint.x","ThumbIntermediatePrevJoint.y","ThumbIntermediatePrevJoint.z",
            "ThumbDistalPrevJoint.x","ThumbDistalPrevJoint.y","ThumbDistalPrevJoint.z",
            "IndexDirection.x","IndexDirection.y","IndexDirection.z",
            "IndexTipPosition.x","IndexTipPosition.y","IndexTipPosition.z",
            "IndexMetacarpalPrevJoint.x","IndexMetacarpalPrevJoint.y","IndexMetacarpalPrevJoint.z",
            "IndexProximalPrevJoint.x","IndexProximalPrevJoint.y","IndexProximalPrevJoint.z",
            "IndexIntermediatePrevJoint.x","IndexIntermediatePrevJoint.y","IndexIntermediatePrevJoint.z",
            "IndexDistalPrevJoint.x","IndexDistalPrevJoint.y","IndexDistalPrevJoint.z",
            "MiddleDirection.x","MiddleDirection.y","MiddleDirection.z",
            "MiddleTipPosition.x","MiddleTipPosition.y","MiddleTipPosition.z",
            "MiddleMetacarpalPrevJoint.x","MiddleMetacarpalPrevJoint.y","MiddleMetacarpalPrevJoint.z",
            "MiddleProximalPrevJoint.x","MiddleProximalPrevJoint.y","MiddleProximalPrevJoint.z",
            "MiddleIntermediatePrevJoint.x","MiddleIntermediatePrevJoint.y","MiddleIntermediatePrevJoint.z",
            "MiddleDistalPrevJoint.x","MiddleDistalPrevJoint.y","MiddleDistalPrevJoint.z",
            "RingDirection.x","RingDirection.y","RingDirection.z",
            "RingTipPosition.x","RingTipPosition.y","RingTipPosition.z",
            "RingMetacarpalPrevJoint.x","RingMetacarpalPrevJoint.y","RingMetacarpalPrevJoint.z",
            "RingProximalPrevJoint.x","RingProximalPrevJoint.y","RingProximalPrevJoint.z",
            "RingIntermediatePrevJoint.x","RingIntermediatePrevJoint.y","RingIntermediatePrevJoint.z",
            "RingDistalPrevJoint.x","RingDistalPrevJoint.y","RingDistalPrevJoint.z",
            "PinkyDirection.x","PinkyDirection.y","PinkyDirection.z",
            "PinkyTipPosition.x","PinkyTipPosition.y","PinkyTipPosition.z",
            "PinkyMetacarpalPrevJoint.x","PinkyMetacarpalPrevJoint.y","PinkyMetacarpalPrevJoint.z",
            "PinkyProximalPrevJoint.x","PinkyProximalPrevJoint.y","PinkyProximalPrevJoint.z",
            "PinkyIntermediatePrevJoint.x","PinkyIntermediatePrevJoint.y","PinkyIntermediatePrevJoint.z",
            "PinkyDistalPrevJoint.x","PinkyDistalPrevJoint.y","PinkyDistalPrevJoint.z"
        };
    
    //  The reference to the main class
    private LeapGestureAnalyzer mParent;
    
    //  The gesture data in matrix format
    private Object[][] rowData;
    
    /**
     *  The class containing the hand gesture
     */
    public HandGesture mHandGesture;

    /**
     * 
     * @param parent
     * @param gesture 
     */
    public HandGestureFrame(LeapGestureAnalyzer parent, HandGesture gesture) 
    {
        //  Set the reference to the main class of the application
        mParent = parent;
        
        initComponents();
        mHandGesture = gesture;
        
        if(mHandGesture.getGestureUsage() == HandGesture.TYPE_TRAINING) 
        {
            mUsageToggle.setText("Training");
            mUsageToggle.setSelected(false);
        }
        else 
        {
            mUsageToggle.setText("Tracking");
            mUsageToggle.setSelected(true);
        }
        
        //  Write the type of gesture according to the name of the file
        switch(mHandGesture.getGestureType()) 
        {
            case HandGesture.TYPE_PINCH:
                mGestureTypeTextField.setText(HandGesture.TYPE_PINCH_NAME);
                break;
                
            case HandGesture.TYPE_CLICK:
                mGestureTypeTextField.setText(HandGesture.TYPE_CLICK_NAME);
                break;
                
            case HandGesture.TYPE_STOP:
                mGestureTypeTextField.setText(HandGesture.TYPE_STOP_NAME);
                break;
                
            case HandGesture.TYPE_WAVE:
                mGestureTypeTextField.setText(HandGesture.TYPE_WAVE_NAME);
                break;
                
            default:
                mGestureTypeTextField.setText(HandGesture.TYPE_NONE_NAME);
        }
        
        //  Write the length of the gesture
        mSpeedTextField.setText(mHandGesture.getDurationInSecond() + "s");
        
        //  Write the number of frames of the captured gesture
        mFramesTextField.setText(String.valueOf(mHandGesture.mTimestamp.size()));
        
        //  Write the observation sequence
        StringBuilder builder = new StringBuilder();
        builder.append("Emission codes: \n");
        builder.append("INIT = 0\n");
        builder.append("RUF = 1\n");
        builder.append("RU = 2\n");
        builder.append("RUB = 3\n");
        builder.append("RF = 4\n");
        builder.append("R = 5\n");
        builder.append("RB = 6\n");
        builder.append("RDF = 7\n");
        builder.append("RD = 8\n");
        builder.append("RDB = 9\n");
        builder.append("UF = 10\n");
        builder.append("U = 11\n");
        builder.append("UB = 12\n");
        builder.append("F = 13\n");
        builder.append("C = 14\n");
        builder.append("B = 15\n");
        builder.append("DF = 16\n");
        builder.append("D = 17\n");
        builder.append("DB = 18\n");
        builder.append("LUF = 19\n");
        builder.append("LU = 20\n");
        builder.append("LUB = 21\n");
        builder.append("LU = 22\n");
        builder.append("L = 23\n");
        builder.append("LB = 24\n");
        builder.append("LDF = 25\n");
        builder.append("LD = 26\n");
        builder.append("LDB = 27\n");
        
        //  Generate observation sequence for palm position
        builder.append("\nPalm Position: ");
        int[] observationSequence = mHandGesture.getObservationSequence(mHandGesture.mPalmPosition);
        int n = observationSequence.length;
        for(int i = 0; i < n; i += 1) 
        {
            builder.append(String.valueOf(observationSequence[i] + ","));
        }
        
        //  Generate observation sequence for palm position
        builder.append("\nPalm Normal: ");
        observationSequence = mHandGesture.getObservationSequence(mHandGesture.mPalmNormal);
        n = observationSequence.length;
        for(int i = 0; i < n; i += 1) 
        {
            builder.append(String.valueOf(observationSequence[i] + ","));
        }
        
        //  Generate observation sequence for hand direction
        builder.append("\nHand Direction: ");
        observationSequence = mHandGesture.getObservationSequence(mHandGesture.mHandDirection);
        n = observationSequence.length;
        for(int i = 0; i < n; i += 1) 
        {
            builder.append(String.valueOf(observationSequence[i] + ","));
        }
        
        //  Set generated text to text area
        mEmissionCodeTextArea.setText(builder.toString());
        
        //  Add gesture information to table
        generateData();
        DefaultTableModel tableModel;
        tableModel = new DefaultTableModel(rowData, columnNames){
            
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            
        };
        mGestureDataTable.setModel(tableModel);
    }
    
    /**
     * 
     */
    private void generateData() 
    {
        //  Build the data as a matrix
        int n = mHandGesture.mTimestamp.size();
        rowData = new Object[n][114];
        for(int i = 0; i < n; i += 1) 
        {
            //  The information of the timestamp
            rowData[i][0] = mHandGesture.mTimestamp.get(i);
            
            //  The information of the hand
            rowData[i][1] = mHandGesture.mPalmPosition.get(i).x;
            rowData[i][2] = mHandGesture.mPalmPosition.get(i).y;
            rowData[i][3] = mHandGesture.mPalmPosition.get(i).z;
            rowData[i][4] = mHandGesture.mPalmNormal.get(i).x;
            rowData[i][5] = mHandGesture.mPalmNormal.get(i).y;
            rowData[i][6] = mHandGesture.mPalmNormal.get(i).z;
            rowData[i][7] = mHandGesture.mPalmVelocity.get(i).x;
            rowData[i][8] = mHandGesture.mPalmVelocity.get(i).y;
            rowData[i][9] = mHandGesture.mPalmVelocity.get(i).z;
            rowData[i][10] = mHandGesture.mHandDirection.get(i).x;
            rowData[i][11] = mHandGesture.mHandDirection.get(i).y;
            rowData[i][12] = mHandGesture.mHandDirection.get(i).z;
            rowData[i][13] = mHandGesture.mPinchStrength.get(i);
            rowData[i][14] = mHandGesture.mGrabStrength.get(i);
            
            //  The information of the arm
            rowData[i][15] = mHandGesture.mWristPosition.get(i).x;
            rowData[i][16] = mHandGesture.mWristPosition.get(i).y;
            rowData[i][17] = mHandGesture.mWristPosition.get(i).z;
            rowData[i][18] = mHandGesture.mElbowPosition.get(i).x;
            rowData[i][19] = mHandGesture.mElbowPosition.get(i).y;
            rowData[i][20] = mHandGesture.mElbowPosition.get(i).z;
            rowData[i][21] = mHandGesture.mArmDirection.get(i).x;
            rowData[i][22] = mHandGesture.mArmDirection.get(i).y;
            rowData[i][23] = mHandGesture.mArmDirection.get(i).z;
            
            //  The information of the thumb
            rowData[i][24] = mHandGesture.mThumbDirection.get(i).x;
            rowData[i][25] = mHandGesture.mThumbDirection.get(i).y;
            rowData[i][26] = mHandGesture.mThumbDirection.get(i).z;
            rowData[i][27] = mHandGesture.mThumbTipPosition.get(i).x;
            rowData[i][28] = mHandGesture.mThumbTipPosition.get(i).y;
            rowData[i][29] = mHandGesture.mThumbTipPosition.get(i).z;
            rowData[i][30] = mHandGesture.mThumbMetacarpalPrevJoint.get(i).x;
            rowData[i][31] = mHandGesture.mThumbMetacarpalPrevJoint.get(i).y;
            rowData[i][32] = mHandGesture.mThumbMetacarpalPrevJoint.get(i).z;
            rowData[i][33] = mHandGesture.mThumbProximalPrevJoint.get(i).x;
            rowData[i][34] = mHandGesture.mThumbProximalPrevJoint.get(i).y;
            rowData[i][35] = mHandGesture.mThumbProximalPrevJoint.get(i).z;
            rowData[i][36] = mHandGesture.mThumbIntermediatePrevJoint.get(i).x;
            rowData[i][37] = mHandGesture.mThumbIntermediatePrevJoint.get(i).y;
            rowData[i][38] = mHandGesture.mThumbIntermediatePrevJoint.get(i).z;
            rowData[i][39] = mHandGesture.mThumbDistalPrevJoint.get(i).x;
            rowData[i][40] = mHandGesture.mThumbDistalPrevJoint.get(i).y;
            rowData[i][41] = mHandGesture.mThumbDistalPrevJoint.get(i).z;
            
            //  The information of the Index
            rowData[i][42] = mHandGesture.mIndexDirection.get(i).x;
            rowData[i][43] = mHandGesture.mIndexDirection.get(i).y;
            rowData[i][44] = mHandGesture.mIndexDirection.get(i).z;
            rowData[i][45] = mHandGesture.mIndexTipPosition.get(i).x;
            rowData[i][46] = mHandGesture.mIndexTipPosition.get(i).y;
            rowData[i][47] = mHandGesture.mIndexTipPosition.get(i).z;
            rowData[i][48] = mHandGesture.mIndexMetacarpalPrevJoint.get(i).x;
            rowData[i][49] = mHandGesture.mIndexMetacarpalPrevJoint.get(i).y;
            rowData[i][50] = mHandGesture.mIndexMetacarpalPrevJoint.get(i).z;
            rowData[i][51] = mHandGesture.mIndexProximalPrevJoint.get(i).x;
            rowData[i][52] = mHandGesture.mIndexProximalPrevJoint.get(i).y;
            rowData[i][53] = mHandGesture.mIndexProximalPrevJoint.get(i).z;
            rowData[i][54] = mHandGesture.mIndexIntermediatePrevJoint.get(i).x;
            rowData[i][55] = mHandGesture.mIndexIntermediatePrevJoint.get(i).y;
            rowData[i][56] = mHandGesture.mIndexIntermediatePrevJoint.get(i).z;
            rowData[i][57] = mHandGesture.mIndexDistalPrevJoint.get(i).x;
            rowData[i][58] = mHandGesture.mIndexDistalPrevJoint.get(i).y;
            rowData[i][59] = mHandGesture.mIndexDistalPrevJoint.get(i).z;
            
            //  The information of the Middle
            rowData[i][60] = mHandGesture.mMiddleDirection.get(i).x;
            rowData[i][61] = mHandGesture.mMiddleDirection.get(i).y;
            rowData[i][62] = mHandGesture.mMiddleDirection.get(i).z;
            rowData[i][63] = mHandGesture.mMiddleTipPosition.get(i).x;
            rowData[i][64] = mHandGesture.mMiddleTipPosition.get(i).y;
            rowData[i][65] = mHandGesture.mMiddleTipPosition.get(i).z;
            rowData[i][66] = mHandGesture.mMiddleMetacarpalPrevJoint.get(i).x;
            rowData[i][67] = mHandGesture.mMiddleMetacarpalPrevJoint.get(i).y;
            rowData[i][68] = mHandGesture.mMiddleMetacarpalPrevJoint.get(i).z;
            rowData[i][69] = mHandGesture.mMiddleProximalPrevJoint.get(i).x;
            rowData[i][70] = mHandGesture.mMiddleProximalPrevJoint.get(i).y;
            rowData[i][71] = mHandGesture.mMiddleProximalPrevJoint.get(i).z;
            rowData[i][72] = mHandGesture.mMiddleIntermediatePrevJoint.get(i).x;
            rowData[i][73] = mHandGesture.mMiddleIntermediatePrevJoint.get(i).y;
            rowData[i][74] = mHandGesture.mMiddleIntermediatePrevJoint.get(i).z;
            rowData[i][75] = mHandGesture.mMiddleDistalPrevJoint.get(i).x;
            rowData[i][76] = mHandGesture.mMiddleDistalPrevJoint.get(i).y;
            rowData[i][77] = mHandGesture.mMiddleDistalPrevJoint.get(i).z;
            
            //  The information of the Ring
            rowData[i][78] = mHandGesture.mRingDirection.get(i).x;
            rowData[i][79] = mHandGesture.mRingDirection.get(i).y;
            rowData[i][80] = mHandGesture.mRingDirection.get(i).z;
            rowData[i][81] = mHandGesture.mRingTipPosition.get(i).x;
            rowData[i][82] = mHandGesture.mRingTipPosition.get(i).y;
            rowData[i][83] = mHandGesture.mRingTipPosition.get(i).z;
            rowData[i][84] = mHandGesture.mRingMetacarpalPrevJoint.get(i).x;
            rowData[i][85] = mHandGesture.mRingMetacarpalPrevJoint.get(i).y;
            rowData[i][86] = mHandGesture.mRingMetacarpalPrevJoint.get(i).z;
            rowData[i][87] = mHandGesture.mRingProximalPrevJoint.get(i).x;
            rowData[i][88] = mHandGesture.mRingProximalPrevJoint.get(i).y;
            rowData[i][89] = mHandGesture.mRingProximalPrevJoint.get(i).z;
            rowData[i][90] = mHandGesture.mRingIntermediatePrevJoint.get(i).x;
            rowData[i][91] = mHandGesture.mRingIntermediatePrevJoint.get(i).y;
            rowData[i][92] = mHandGesture.mRingIntermediatePrevJoint.get(i).z;
            rowData[i][93] = mHandGesture.mRingDistalPrevJoint.get(i).x;
            rowData[i][94] = mHandGesture.mRingDistalPrevJoint.get(i).y;
            rowData[i][95] = mHandGesture.mRingDistalPrevJoint.get(i).z;
            
            //  The information of the Pinky
            rowData[i][96] = mHandGesture.mPinkyDirection.get(i).x;
            rowData[i][97] = mHandGesture.mPinkyDirection.get(i).y;
            rowData[i][98] = mHandGesture.mPinkyDirection.get(i).z;
            rowData[i][99] = mHandGesture.mPinkyTipPosition.get(i).x;
            rowData[i][100] = mHandGesture.mPinkyTipPosition.get(i).y;
            rowData[i][101] = mHandGesture.mPinkyTipPosition.get(i).z;
            rowData[i][102] = mHandGesture.mPinkyMetacarpalPrevJoint.get(i).x;
            rowData[i][103] = mHandGesture.mPinkyMetacarpalPrevJoint.get(i).y;
            rowData[i][104] = mHandGesture.mPinkyMetacarpalPrevJoint.get(i).z;
            rowData[i][105] = mHandGesture.mPinkyProximalPrevJoint.get(i).x;
            rowData[i][106] = mHandGesture.mPinkyProximalPrevJoint.get(i).y;
            rowData[i][107] = mHandGesture.mPinkyProximalPrevJoint.get(i).z;
            rowData[i][108] = mHandGesture.mPinkyIntermediatePrevJoint.get(i).x;
            rowData[i][109] = mHandGesture.mPinkyIntermediatePrevJoint.get(i).y;
            rowData[i][110] = mHandGesture.mPinkyIntermediatePrevJoint.get(i).z;
            rowData[i][111] = mHandGesture.mPinkyDistalPrevJoint.get(i).x;
            rowData[i][112] = mHandGesture.mPinkyDistalPrevJoint.get(i).y;
            rowData[i][113] = mHandGesture.mPinkyDistalPrevJoint.get(i).z;
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        mGestureTypeTextField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        mSpeedTextField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        mFramesTextField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        mUsageToggle = new javax.swing.JToggleButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        mEmissionCodeTextArea = new javax.swing.JTextArea();
        jScrollPane1 = new javax.swing.JScrollPane();
        mGestureDataTable = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setVisible(true);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Gesture Data"));

        jLabel1.setText("Type");

        mGestureTypeTextField.setEditable(false);
        mGestureTypeTextField.setText("jTextField1");
        mGestureTypeTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mGestureTypeTextFieldActionPerformed(evt);
            }
        });

        jLabel3.setText("Speed");

        mSpeedTextField.setEditable(false);
        mSpeedTextField.setText("jTextField1");

        jLabel4.setText("Frames");

        mFramesTextField.setEditable(false);
        mFramesTextField.setText("jTextField1");

        jLabel6.setText("Usage");

        mUsageToggle.setText("jToggleButton1");
        mUsageToggle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mUsageToggleActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(mUsageToggle)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(mFramesTextField)
                    .addComponent(mSpeedTextField)
                    .addComponent(mGestureTypeTextField))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(mGestureTypeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(mSpeedTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(mFramesTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5))
                    .addComponent(mUsageToggle))
                .addContainerGap(41, Short.MAX_VALUE))
        );

        jLabel2.setText("General Information");

        mEmissionCodeTextArea.setEditable(false);
        mEmissionCodeTextArea.setColumns(20);
        mEmissionCodeTextArea.setRows(5);
        jScrollPane2.setViewportView(mEmissionCodeTextArea);

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        mGestureDataTable.setModel(new javax.swing.table.DefaultTableModel(
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
        mGestureDataTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane1.setViewportView(mGestureDataTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * 
     * @param evt 
     */
    private void mGestureTypeTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mGestureTypeTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mGestureTypeTextFieldActionPerformed

    /**
     * 
     * @param evt 
     */
    private void mUsageToggleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mUsageToggleActionPerformed
        mHandGesture.toggleUsage();
        mUsageToggle.setText(mHandGesture.getGestureUsageName());
    }//GEN-LAST:event_mUsageToggleActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea mEmissionCodeTextArea;
    private javax.swing.JTextField mFramesTextField;
    private javax.swing.JTable mGestureDataTable;
    private javax.swing.JTextField mGestureTypeTextField;
    private javax.swing.JTextField mSpeedTextField;
    private javax.swing.JToggleButton mUsageToggle;
    // End of variables declaration//GEN-END:variables
}
