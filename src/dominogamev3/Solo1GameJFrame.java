/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominogamev3;

import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

/**
 *
 * @author Sierra Kilo
 */
public class Solo1GameJFrame extends javax.swing.JFrame {
    
    JFrame previousFrame; // stores the previous frame tha led to the Solo1GameJFrame, which is the MainMenuJFrame

    Solo1GameLogic gameInstance;
    Tile chosenTile; // holds the Tile object representing the tile chosen by the user through the GUI to play with.
    int choice;
    JLabel[] rowLabelArray;
    JRadioButton[] choiceRadioButtonArray;

    /**
     * Creates new form MainGameJFrame
     */
    public Solo1GameJFrame(JFrame previousFrame) {
        initComponents();

        // initialize necessary class fields
        this.previousFrame = previousFrame;
        rowLabelArray = new JLabel[]{jRowLabel1, jRowLabel2, jRowLabel3, jRowLabel4};
        choiceRadioButtonArray = new JRadioButton[]{jRadioButton1, jRadioButton2, jRadioButton3, jRadioButton4};
        
        choice = 1; // since the jRadioButton1 is always preselected, we initialize the choice variable with 1 to reflect this
        
        // Initialize the object of the Logic Class
        initializeGameInstance();

        // update the GUI elements (heap, radiobutton text etc...)
        updateGuiElements();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        jTableLabel = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jRowLabel2 = new javax.swing.JLabel();
        jRowLabel1 = new javax.swing.JLabel();
        jRowLabel4 = new javax.swing.JLabel();
        jRowLabel3 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton();
        jSubmitButton = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("dominogamev3/Bundle"); // NOI18N
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, bundle.getString("Solo1GameJFrame.jPanel2.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 14))); // NOI18N

        jTableLabel.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jTableLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTableLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTableLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, bundle.getString("Solo1GameJFrame.jPanel3.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 14))); // NOI18N

        jRowLabel2.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jRowLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jRowLabel2.setText(bundle.getString("Solo1GameJFrame.jRowLabel2.text")); // NOI18N

        jRowLabel1.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jRowLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jRowLabel1.setText(bundle.getString("Solo1GameJFrame.jRowLabel1.text")); // NOI18N

        jRowLabel4.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jRowLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jRowLabel4.setText(bundle.getString("Solo1GameJFrame.jRowLabel4.text")); // NOI18N

        jRowLabel3.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jRowLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jRowLabel3.setText(bundle.getString("Solo1GameJFrame.jRowLabel3.text")); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jRowLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jRowLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jRowLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jRowLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jRowLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRowLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRowLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRowLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, bundle.getString("Solo1GameJFrame.jPanel4.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 14))); // NOI18N

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("Solo1GameJFrame.jPanel7.border.title"))); // NOI18N

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jRadioButton1.setSelected(true);
        jRadioButton1.setText(bundle.getString("Solo1GameJFrame.jRadioButton1.text")); // NOI18N
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jRadioButton2.setText(bundle.getString("Solo1GameJFrame.jRadioButton2.text")); // NOI18N
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButton3);
        jRadioButton3.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jRadioButton3.setText(bundle.getString("Solo1GameJFrame.jRadioButton3.text")); // NOI18N
        jRadioButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton3ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButton4);
        jRadioButton4.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jRadioButton4.setText(bundle.getString("Solo1GameJFrame.jRadioButton4.text")); // NOI18N
        jRadioButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                    .addComponent(jRadioButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jRadioButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jRadioButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jRadioButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton4)
                .addContainerGap(57, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jSubmitButton.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jSubmitButton.setText(bundle.getString("Solo1GameJFrame.jSubmitButton.text")); // NOI18N
        jSubmitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jSubmitButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addComponent(jSubmitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 545, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSubmitButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(14, 14, 14))
        );

        jMenu1.setText(bundle.getString("Solo1GameJFrame.jMenu1.text")); // NOI18N

        jMenuItem1.setText(bundle.getString("Solo1GameJFrame.jMenuItem1.text")); // NOI18N
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText(bundle.getString("Solo1GameJFrame.jMenuItem2.text")); // NOI18N
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(25, 25, 25))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jSubmitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jSubmitButtonActionPerformed
        // TODO add your handling code here:
        submitAction();
    }//GEN-LAST:event_jSubmitButtonActionPerformed

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        // TODO add your handling code here:
        choice = 1;
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        // TODO add your handling code here:
        choice = 2;
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void jRadioButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton3ActionPerformed
        // TODO add your handling code here:
        choice = 3;
    }//GEN-LAST:event_jRadioButton3ActionPerformed

    private void jRadioButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton4ActionPerformed
        // TODO add your handling code here:
        choice = 4;
    }//GEN-LAST:event_jRadioButton4ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        initializeGameInstance();
        resetChoiceRadioButtons(); // sets all choice radio buttons back to enabled.
        updateGuiElements();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:'
        this.dispose();
        previousFrame.setVisible(true);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void updateGuiElements() {

        // Get the table of the game and show in on the GUI
        Table tableObj = gameInstance.getTable();
        ArrayList<Tile> tableTiles = tableObj.getTable();
        String tableText = "";

        for (Tile piece : tableTiles) {
            tableText += "|" + piece.getNum1() + " " + piece.getNum2() + "| ";
        }

        jTableLabel.setText(tableText);

        
        // Get the heap of the game and show it on the GUI
        Heap heapObj = gameInstance.getHeap();
        ArrayList<ArrayList<Tile>> heapTiles = heapObj.getHeap();
        
        
        // for diagnostic purposes, print heap to console
        int rowsAmount = 0; // the amount of rows the heap displayed has.

        for (int i = 0; i < heapTiles.size(); i++) {
            ArrayList<Tile> row = heapTiles.get(i);
            if (row.size() > 0) {
                rowsAmount++;
            }
            for (int j = 0; j < row.size(); j++) {
                Tile piece = row.get(j);
                System.out.printf("|%d %d| ", piece.getNum1(), piece.getNum2());
            }
            System.out.printf("%n%n");
        }

        
        
        String tileText;
        String rowText;

        // IMPORTANT! Reset all row Labels' text before proceeding
        jRowLabel1.setText("");
        jRowLabel2.setText("");
        jRowLabel3.setText("");
        jRowLabel4.setText("");

        for (int i = 0; i < heapTiles.size(); i++) {
            rowText = "";
            ArrayList<Tile> row = heapTiles.get(i);

            if (row.size() > 0) {

                // if the row contains tiles, then we add them to the GUI.
                // Otherwise, the "" default text is added for an empty row on the GUI.
                for (int j = 0; j < row.size(); j++) {
                    Tile piece = row.get(j);

                    tileText = "|" + piece.getNum1() + " " + piece.getNum2() + "| ";
                    rowText += tileText;

                    if (j == row.size() - 1) {
                        choiceRadioButtonArray[i].setText(tileText);
                    }
                }
            } else {
                // if row.size() == 0
                // disable the radiobutton choice for this row

                if (choiceRadioButtonArray[i].isEnabled()) {
                    choiceRadioButtonArray[i].setText("");
                    choiceRadioButtonArray[i].setEnabled(false);
                }

            }

            rowLabelArray[i].setText(rowText);

        }
    }

    private void submitAction() {
        ArrayList<PossibleMove> result;

        chosenTile = gameInstance.getHeap().chooseTile(choice);
        result = gameInstance.checkTileChoice(chosenTile);

        // *** WORK IN PROGRESS ***
        if (result.size() == 0) {
            // there is no possible move with the chosen tile.
            System.out.println("> There is no possible move with the chosen tile! Try again!");
            JOptionPane.showMessageDialog(null, "There is no possible move with the chosen tile!\nPlease select another tile.", "Unavailable move", JOptionPane.ERROR_MESSAGE);
            // continue
        } else if (result.size() == 1) {
            // there is one possible move so tile is placed automatically
            gameInstance.humanPlays(choice, chosenTile, result.get(0).needsRotation(), result.get(0).whereToPlace());
        } else {
            // there are more 2 possible moves 
            // so the user is asked about where to place tile.

            System.out.println("> There are 2 possible moves with this tile.");
            System.out.println("> Do you want to place the tile left or right?");

            String[] values = {"Left side", "Right side"};
            Object selected = JOptionPane.showInputDialog(null, "There are 2 possible moves with this tile.\n"
                    + "On which side of the table do you want to place the tile?",
                    "Multiple moves", JOptionPane.DEFAULT_OPTION, null, values, "0");

            String selectedString = selected.toString();

            System.out.println("DIAG: object selected is: " + selected);
            System.out.println("DIAG: selectedString is: " + selectedString);

            String side;
            if (selectedString.equals("Left side")) {
                side = "left";
            } else {
                side = "right";
            }

            if (result.get(0).whereToPlace().equals(side)) {
                System.out.println("MPIKA PERIPTOSI 1");
                gameInstance.humanPlays(choice, chosenTile, result.get(0).needsRotation(), side);
            } else if (result.get(1).whereToPlace().equals(side)) {
                System.out.println("MPIKA PERIPTOSI 2");
                gameInstance.humanPlays(choice, chosenTile, result.get(1).needsRotation(), side);
            }
        }

        // *** WORK IN PROGRESS ***
        
        updateGuiElements();
        
        if (gameInstance.gameStatus() == 0) {
            System.out.println("*** Game over! There are no possible moves left! ***");
            JOptionPane.showMessageDialog(null, "Game over!\nThere are no possible moves left!",
                    "Game Over", JOptionPane.INFORMATION_MESSAGE);
        } else if (gameInstance.gameStatus() == 2) {
            System.out.println("*** Congratulations! You have won! ***");
            JOptionPane.showMessageDialog(null, "Congratulations! You have won!",
                    "Win", JOptionPane.INFORMATION_MESSAGE);
        }

        

    }
    
    public void resetChoiceRadioButtons() {
        // sets all choice Radio Buttons to enabled
        // in case a new game is started from the menu bar
        // and some choice Radio Buttons were disabled durning the previous game
        // because their corresponding row was emptied.
        
        jRadioButton1.setEnabled(true);
        jRadioButton2.setEnabled(true);
        jRadioButton3.setEnabled(true);
        jRadioButton4.setEnabled(true);
        
    }
    
    private void initializeGameInstance() {
        gameInstance = new Solo1GameLogic();
    }

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(HungarianGameJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HungarianGameJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HungarianGameJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HungarianGameJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Solo1GameJFrame(new MainMenuJFrame()).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JLabel jRowLabel1;
    private javax.swing.JLabel jRowLabel2;
    private javax.swing.JLabel jRowLabel3;
    private javax.swing.JLabel jRowLabel4;
    private javax.swing.JButton jSubmitButton;
    private javax.swing.JLabel jTableLabel;
    // End of variables declaration//GEN-END:variables
}
