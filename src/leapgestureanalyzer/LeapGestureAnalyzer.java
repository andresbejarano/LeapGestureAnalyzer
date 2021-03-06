package leapgestureanalyzer;

import java.beans.PropertyVetoException;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author AndresMauricio
 */
public class LeapGestureAnalyzer extends javax.swing.JFrame 
{
    //  The desktop pane where internal frames are placed
    private final JDesktopPane mDesktopPane;
    
    //  The output console
    public final Console mConsole;
    
    //  The number of states for the HMM
    private int mNumStates;

    /**
     * Creates new form LeapGestureAnalyzer
     */
    public LeapGestureAnalyzer() 
    {    
        //  Initialize the components
        initComponents();
        setSize(1024, 600);
        
        //  The initial number of states is 4
        mNumStates = 4;
        
        //  Generate the desktop pane and add it to the frame
        mDesktopPane = new JDesktopPane();
        setContentPane(mDesktopPane);
        
        //  Initialize the console
        mConsole = new Console();
        mConsole.setVisible(true);
        mConsole.setResizable(true);
        mDesktopPane.add(mConsole);
    }
    
    /**
     * 
     * @param line 
     */
    public void write(String line) 
    {
        mConsole.write(line);
    }
    
    /**
     * 
     * @return 
     */
    public ArrayList<HandGesture> getHandGestures() 
    {
        //  Initialize the hand gesture array
        ArrayList<HandGesture> gestures = new ArrayList<>();
        
        //  Save hand gestures into the array
        JInternalFrame[] frames = mDesktopPane.getAllFrames();
        int n = frames.length;
        for(int i = 0; i < n; i += 1) 
        {
            try 
            {
                HandGestureFrame frame = (HandGestureFrame)frames[i];
                gestures.add(frame.mHandGesture);
            }
            catch(Exception e) 
            {
                write("Not the type of expected JInternalFrame: " + e.getMessage());
            }
        }
        
        //  Return the array
        return gestures;
    }
    
    /**
     * 
     * @return 
     */
    public int getNumStates() 
    {
        return mNumStates;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mMenuBar = new javax.swing.JMenuBar();
        mActionMenu = new javax.swing.JMenu();
        mOpenFileItem = new javax.swing.JMenuItem();
        mBuildHmmItem = new javax.swing.JMenuItem();
        mBuildStateMachineItem = new javax.swing.JMenuItem();
        mSeparator1 = new javax.swing.JPopupMenu.Separator();
        mMinimizeFramesItem = new javax.swing.JMenuItem();
        mCloseFramesItem = new javax.swing.JMenuItem();
        mSeparator2 = new javax.swing.JPopupMenu.Separator();
        mExitItem = new javax.swing.JMenuItem();
        mParametersMenu = new javax.swing.JMenu();
        mNumStatesItem = new javax.swing.JMenuItem();
        mConsoleMenu = new javax.swing.JMenu();
        mClearConsoleItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Leap Gesture Analyzer");

        mActionMenu.setText("Action");

        mOpenFileItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        mOpenFileItem.setText("Open File");
        mOpenFileItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mOpenFileItemActionPerformed(evt);
            }
        });
        mActionMenu.add(mOpenFileItem);

        mBuildHmmItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, java.awt.event.InputEvent.CTRL_MASK));
        mBuildHmmItem.setText("Build HMM");
        mBuildHmmItem.setEnabled(false);
        mBuildHmmItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mBuildHmmItemActionPerformed(evt);
            }
        });
        mActionMenu.add(mBuildHmmItem);

        mBuildStateMachineItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        mBuildStateMachineItem.setText("Build State Machine");
        mBuildStateMachineItem.setEnabled(false);
        mBuildStateMachineItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mBuildStateMachineItemActionPerformed(evt);
            }
        });
        mActionMenu.add(mBuildStateMachineItem);
        mActionMenu.add(mSeparator1);

        mMinimizeFramesItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.CTRL_MASK));
        mMinimizeFramesItem.setText("Minimize gesture frames");
        mMinimizeFramesItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mMinimizeFramesItemActionPerformed(evt);
            }
        });
        mActionMenu.add(mMinimizeFramesItem);

        mCloseFramesItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        mCloseFramesItem.setText("Close All Gesture Frames");
        mCloseFramesItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mCloseFramesItemActionPerformed(evt);
            }
        });
        mActionMenu.add(mCloseFramesItem);
        mActionMenu.add(mSeparator2);

        mExitItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        mExitItem.setText("Exit");
        mExitItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mExitItemActionPerformed(evt);
            }
        });
        mActionMenu.add(mExitItem);

        mMenuBar.add(mActionMenu);

        mParametersMenu.setText("Parameters");

        mNumStatesItem.setText("# States");
        mNumStatesItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mNumStatesItemActionPerformed(evt);
            }
        });
        mParametersMenu.add(mNumStatesItem);

        mMenuBar.add(mParametersMenu);

        mConsoleMenu.setText("Console");

        mClearConsoleItem.setText("Clear Console");
        mClearConsoleItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mClearConsoleItemActionPerformed(evt);
            }
        });
        mConsoleMenu.add(mClearConsoleItem);

        mMenuBar.add(mConsoleMenu);

        setJMenuBar(mMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 348, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 238, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * 
     * @param evt 
     */
    private void mOpenFileItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mOpenFileItemActionPerformed
        
        //  Set up the file chooser dialog
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(true);
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home") + "/Desktop"));
        int result = fileChooser.showOpenDialog(this);
        
        //  If at least one file was selected
        if(result == JFileChooser.APPROVE_OPTION) {
            
            //  Get the array of selected files
            File[] files = fileChooser.getSelectedFiles();
            int nFiles = files.length;
            
            //  Load each file
            for(int i = 0; i < nFiles; i += 1) 
            {
                //  Generate the hand gesture based on the current file
                HandGesture gesture = new HandGesture(this, files[i]);
            
                //  If file was successfully loaded then create the internal window
                if(gesture.isFileLoaded()) {
                    HandGestureFrame gestureFrame = new HandGestureFrame(this, gesture);
                    gestureFrame.setTitle(files[i].getPath());
                    mDesktopPane.add(gestureFrame);
                }
            }
        }
        
        //  If there's at least one window then enable the HMM and SM generation
        if(mDesktopPane.getAllFrames().length > 0) 
        {
            mBuildHmmItem.setEnabled(true);
            mBuildStateMachineItem.setEnabled(true);
        }
    }//GEN-LAST:event_mOpenFileItemActionPerformed

    /**
     * 
     * @param evt 
     */
    private void mBuildHmmItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mBuildHmmItemActionPerformed
        HMMFrame hmmFrame = new HMMFrame(this);
        hmmFrame.setVisible(true);
        hmmFrame.setClosable(true);
        hmmFrame.setIconifiable(true);
        hmmFrame.setMaximizable(true);
        mDesktopPane.add(hmmFrame);
    }//GEN-LAST:event_mBuildHmmItemActionPerformed

    /**
     * 
     * @param evt 
     */
    private void mExitItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mExitItemActionPerformed
        System.exit(0);
    }//GEN-LAST:event_mExitItemActionPerformed

    /**
     * 
     * @param evt 
     */
    private void mNumStatesItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mNumStatesItemActionPerformed
        String value = JOptionPane.showInputDialog(this, "Insert the number of states for the HMM", "Leap Gesture Analyzer", JOptionPane.QUESTION_MESSAGE);
        if(value != null) 
        {
            try
            {
                int v = Integer.parseInt(value);
                if(v <= 1) 
                {
                    JOptionPane.showMessageDialog(this, "Not a valid number. Must be > 1", "Leap Gesture Analyzer", JOptionPane.ERROR_MESSAGE);
                }
                else 
                {
                    mNumStates = v;
                    write("Number of states for the HMM now is " + mNumStates);
                }
            }
            catch(NumberFormatException e) 
            {
                JOptionPane.showMessageDialog(this, "Not a number", "Leap Gesture Analyzer", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_mNumStatesItemActionPerformed

    /**
     * 
     * @param evt 
     */
    private void mMinimizeFramesItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mMinimizeFramesItemActionPerformed
        JInternalFrame[] mFrames = mDesktopPane.getAllFrames();
        int n = mFrames.length;
        for(int i = 0; i < n; i += 1) 
        {
            if(mFrames[i] instanceof HandGestureFrame) 
            {
                try 
                {
                    mFrames[i].setIcon(true);
                }
                catch(PropertyVetoException e) 
                {
                    write("Internal frame cannot be minimized");
                }
            }
        }
    }//GEN-LAST:event_mMinimizeFramesItemActionPerformed

    /**
     * 
     * @param evt 
     */
    private void mCloseFramesItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mCloseFramesItemActionPerformed
        JInternalFrame[] mFrames = mDesktopPane.getAllFrames();
        int n = mFrames.length;
        for(int i = 0; i < n; i += 1) 
        {
            if(mFrames[i] instanceof HandGestureFrame) 
            {
                mFrames[i].dispose();
            }
        }
    }//GEN-LAST:event_mCloseFramesItemActionPerformed

    /**
     * 
     * @param evt 
     */
    private void mClearConsoleItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mClearConsoleItemActionPerformed
        mConsole.setText("Output console:");
    }//GEN-LAST:event_mClearConsoleItemActionPerformed

    /**
     * 
     * @param evt 
     */
    private void mBuildStateMachineItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mBuildStateMachineItemActionPerformed
        SMFrame smFrame = new SMFrame(this);
        smFrame.setVisible(true);
        smFrame.setClosable(true);
        smFrame.setIconifiable(true);
        smFrame.setMaximizable(true);
        mDesktopPane.add(smFrame);
    }//GEN-LAST:event_mBuildStateMachineItemActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LeapGestureAnalyzer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LeapGestureAnalyzer().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu mActionMenu;
    private javax.swing.JMenuItem mBuildHmmItem;
    private javax.swing.JMenuItem mBuildStateMachineItem;
    private javax.swing.JMenuItem mClearConsoleItem;
    private javax.swing.JMenuItem mCloseFramesItem;
    private javax.swing.JMenu mConsoleMenu;
    private javax.swing.JMenuItem mExitItem;
    private javax.swing.JMenuBar mMenuBar;
    private javax.swing.JMenuItem mMinimizeFramesItem;
    private javax.swing.JMenuItem mNumStatesItem;
    private javax.swing.JMenuItem mOpenFileItem;
    private javax.swing.JMenu mParametersMenu;
    private javax.swing.JPopupMenu.Separator mSeparator1;
    private javax.swing.JPopupMenu.Separator mSeparator2;
    // End of variables declaration//GEN-END:variables
}
