/*
 * Copyright 2014 Igor Maznitsa.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.igormaznitsa.jhexed.swing.editor.ui.extensions;

import com.igormaznitsa.jhexed.swing.editor.ui.dialogs.AbstractDialog;
import java.awt.event.ActionEvent;

public class LongInfoDialog extends AbstractDialog {
  private static final long serialVersionUID = -5138953562455242739L;

  public LongInfoDialog(final java.awt.Frame parent, final String title, final String text) {
    super(parent, true);
    initComponents();
    this.setTitle(title == null ? "Information" : title);
    this.textArea.setText(text == null ? "" : text);
    this.setLocationRelativeTo(parent);
  }

  /**
   * This method is called from within the constructor to initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is always
   * regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    buttonOk = new javax.swing.JButton();
    scrollText = new javax.swing.JScrollPane();
    textArea = new javax.swing.JTextArea();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

    buttonOk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/igormaznitsa/jhexed/swing/editor/icons/tick.png"))); // NOI18N
    buttonOk.setText("Ok");
    buttonOk.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        buttonOkActionPerformed(evt);
      }
    });

    textArea.setEditable(false);
    textArea.setColumns(20);
    textArea.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
    textArea.setLineWrap(true);
    textArea.setRows(5);
    textArea.setWrapStyleWord(true);
    scrollText.setViewportView(textArea);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
          .addComponent(scrollText, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
          .addComponent(buttonOk, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(scrollText, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(buttonOk)
        .addContainerGap())
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void buttonOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonOkActionPerformed
    this.dispose();
  }//GEN-LAST:event_buttonOkActionPerformed

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton buttonOk;
  private javax.swing.JScrollPane scrollText;
  private javax.swing.JTextArea textArea;
  // End of variables declaration//GEN-END:variables

  @Override
  public void processEscape(final ActionEvent e) {
    buttonOkActionPerformed(e);
  }
}
