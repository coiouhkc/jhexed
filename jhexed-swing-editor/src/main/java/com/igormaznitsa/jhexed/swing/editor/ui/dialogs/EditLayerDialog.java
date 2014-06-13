package com.igormaznitsa.jhexed.swing.editor.ui.dialogs;

import com.igormaznitsa.jhexed.swing.editor.ui.dialogs.hexeditors.DialogEditSVGImageValue;
import com.igormaznitsa.jhexed.swing.editor.ui.dialogs.hexeditors.DialogEditColorValue;
import com.igormaznitsa.jhexed.swing.editor.model.*;
import com.igormaznitsa.jhexed.swing.editor.model.values.*;
import com.igormaznitsa.jhexed.swing.editor.ui.dialogs.hexeditors.HexEditor;
import java.awt.*;
import java.awt.geom.Path2D;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.TableModel;

public class EditLayerDialog extends javax.swing.JDialog implements TableModel, ListSelectionListener {

  private static final int ICON_SIZE = 48;

  private static final long serialVersionUID = 3760730921364742774L;
  private final List<TableModelListener> listeners = new ArrayList<TableModelListener>();
  private final List<HexValue> values = new ArrayList<HexValue>();
  private final LayerDataField value;
  private LayerDataField result;
  private final Path2D iconShape;
  private final List<Integer> removedIndexes = new ArrayList<Integer>();
  private final List<Integer> insertedIndexes = new ArrayList<Integer>();
  
  public EditLayerDialog(final Frame parent, final LayerListModel layerModel, final LayerDataField field, final Path2D hexShape) {
    super(parent, true);
    initComponents();
    this.iconShape = hexShape;
    this.tableValues.setRowHeight(ICON_SIZE);

    if (field == null) {
      this.setTitle("New layer");
      this.value = layerModel.makeNewLayerField("", "");
    }
    else {
      this.setTitle("Edit layer \'"+field.getLayerName()+'\'');
      this.value = field.cloneLayer();
    }

    this.tableValues.getSelectionModel().addListSelectionListener(this);

    loadFields();
    this.setLocationRelativeTo(parent);
  }

  public List<Integer> getRemovedIndexes(){
    return this.removedIndexes;
  }
  
  public List<Integer> getInsertedIndexes(){
    return this.insertedIndexes;
  }
  
  public LayerDataField getResult() {
    return this.result;
  }

  private void saveFields() {
    this.value.setLayerName(this.textName.getText());
    this.value.setComments(this.textComments.getText());

    this.value.updateIndexes(this.removedIndexes, this.insertedIndexes, this.values.size());
    
    this.value.replaceValues(this.values);
  }

  private void loadFields() {
    this.textName.setText(this.value.getLayerName());
    this.textComments.setText(this.value.getComments());

    for (int i = 0; i < this.value.getHexValuesNumber(); i++) {
      this.values.add(this.value.getHexValueForIndex(i).cloneValue());
    }

    this.tableValues.setModel(this);
  }

  /**
   * This method is called from within the constructor to initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is always
   * regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    popupSelectType = new javax.swing.JPopupMenu();
    menuItemHexTypeColor = new javax.swing.JMenuItem();
    menuItemHexTypeSVG = new javax.swing.JMenuItem();
    jLabel1 = new javax.swing.JLabel();
    textName = new javax.swing.JTextField();
    buttonCancel = new javax.swing.JButton();
    buttonSave = new javax.swing.JButton();
    tableValuesScroll = new javax.swing.JScrollPane();
    tableValues = new javax.swing.JTable();
    buttonRemoveSelected = new javax.swing.JButton();
    jScrollPane2 = new javax.swing.JScrollPane();
    textComments = new javax.swing.JTextPane();
    buttonAddPopup = new javax.swing.JToggleButton();

    popupSelectType.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
      public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
      }
      public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
        popupSelectTypePopupMenuWillBecomeInvisible(evt);
      }
      public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
      }
    });

    menuItemHexTypeColor.setText("Color icon");
    menuItemHexTypeColor.setToolTipText("");
    menuItemHexTypeColor.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        menuItemHexTypeColorActionPerformed(evt);
      }
    });
    popupSelectType.add(menuItemHexTypeColor);

    menuItemHexTypeSVG.setText("SVG Icon");
    menuItemHexTypeSVG.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        menuItemHexTypeSVGActionPerformed(evt);
      }
    });
    popupSelectType.add(menuItemHexTypeSVG);

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    setLocationByPlatform(true);

    jLabel1.setText("Layer name:");

    buttonCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/igormaznitsa/jhexed/swing/editor/icons/cross.png"))); // NOI18N
    buttonCancel.setText("Cancel");
    buttonCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        buttonCancelActionPerformed(evt);
      }
    });

    buttonSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/igormaznitsa/jhexed/swing/editor/icons/tick.png"))); // NOI18N
    buttonSave.setText("Save");
    buttonSave.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        buttonSaveActionPerformed(evt);
      }
    });

    tableValues.setModel(new javax.swing.table.DefaultTableModel(
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
    tableValues.setFillsViewportHeight(true);
    tableValues.setRowHeight(64);
    tableValues.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    tableValues.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        tableValuesMouseClicked(evt);
      }
    });
    tableValuesScroll.setViewportView(tableValues);

    buttonRemoveSelected.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/igormaznitsa/jhexed/swing/editor/icons/minus-button.png"))); // NOI18N
    buttonRemoveSelected.setEnabled(false);
    buttonRemoveSelected.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        buttonRemoveSelectedActionPerformed(evt);
      }
    });

    jScrollPane2.setBorder(javax.swing.BorderFactory.createTitledBorder("Commentary"));
    jScrollPane2.setViewportView(textComments);

    buttonAddPopup.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/igormaznitsa/jhexed/swing/editor/icons/plus-button.png"))); // NOI18N
    buttonAddPopup.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        buttonAddPopupActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(jScrollPane2)
          .addGroup(layout.createSequentialGroup()
            .addComponent(jLabel1)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(textName, javax.swing.GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE))
          .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
            .addGap(0, 0, Short.MAX_VALUE)
            .addComponent(buttonSave)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(buttonCancel))
          .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
              .addComponent(buttonRemoveSelected, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(buttonAddPopup, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(tableValuesScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
        .addContainerGap())
    );

    layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {buttonCancel, buttonSave});

    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel1)
          .addComponent(textName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(tableValuesScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
          .addGroup(layout.createSequentialGroup()
            .addComponent(buttonAddPopup)
            .addGap(7, 7, 7)
            .addComponent(buttonRemoveSelected)))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(buttonCancel)
          .addComponent(buttonSave))
        .addContainerGap())
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void buttonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSaveActionPerformed
    saveFields();
    
    for(int i=0;i<this.values.size();i++){
      this.values.get(i).setIndex(i);
    }
    
    this.result = this.value;
    dispose();
  }//GEN-LAST:event_buttonSaveActionPerformed

  private void buttonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCancelActionPerformed
    dispose();
  }//GEN-LAST:event_buttonCancelActionPerformed

  private void buttonAddPopupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAddPopupActionPerformed
    if (buttonAddPopup.isSelected()) {
      popupSelectType.show((Component) evt.getSource(), 0, ((Component) evt.getSource()).getHeight());
    }
  }//GEN-LAST:event_buttonAddPopupActionPerformed

  private void popupSelectTypePopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_popupSelectTypePopupMenuWillBecomeInvisible
    buttonAddPopup.setSelected(false);
  }//GEN-LAST:event_popupSelectTypePopupMenuWillBecomeInvisible

  private void menuItemHexTypeColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemHexTypeColorActionPerformed
    final DialogEditColorValue dlg = new DialogEditColorValue(null, null);
    
    final int position = getNewPosition();
    
    final HexColorValue r = (HexColorValue)dlg.showDialog();
    if (r != null) {
      addNewHexValueToList(position, r);
    }
  }//GEN-LAST:event_menuItemHexTypeColorActionPerformed

  private void buttonRemoveSelectedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonRemoveSelectedActionPerformed
    final int selectedRowIndex = this.tableValues.getSelectedRow();
    if (selectedRowIndex > 0) {
      if (JOptionPane.showConfirmDialog(null, "Do you want to remove the index from layer field?","Confitrmation",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION){
        this.removedIndexes.add(Integer.valueOf(selectedRowIndex));
      }

      this.values.remove(selectedRowIndex);
      
      for(int i=0;i<this.values.size();i++){
        this.values.get(i).setIndex(i);
      }
      
      for (final TableModelListener l : this.listeners) {
        l.tableChanged(new TableModelEvent(this));
      }
    }
  }//GEN-LAST:event_buttonRemoveSelectedActionPerformed

  private void menuItemHexTypeSVGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemHexTypeSVGActionPerformed
    final int position = getNewPosition();
    
    final DialogEditSVGImageValue dlg = new DialogEditSVGImageValue(null, null);
    final HexSVGImageValue val = (HexSVGImageValue)dlg.showDialog();
    if (val != null) {
      addNewHexValueToList(position, val);
    }
  }//GEN-LAST:event_menuItemHexTypeSVGActionPerformed

  private void tableValuesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableValuesMouseClicked
    if (evt.isPopupTrigger()){
      
    }else if (evt.getClickCount()>1){
      final int selectedRow = this.tableValues.getSelectedRow();
      if (selectedRow>0){
        final HexValue origValue = this.values.get(selectedRow);
        final HexEditor dlg;
        if (origValue instanceof HexColorValue){
          dlg = new DialogEditColorValue(null, (HexColorValue)origValue);
        }else if (origValue instanceof HexSVGImageValue){
          dlg = new DialogEditSVGImageValue(null, (HexSVGImageValue) origValue);
        }else{
          JOptionPane.showMessageDialog(this, "unsupported Hex value","Error",JOptionPane.ERROR_MESSAGE);
          return;
        }
        final HexValue editedValue = dlg.showDialog();
        if (editedValue!=null){
          origValue.load(editedValue);
          for(final TableModelListener l : this.listeners){
            l.tableChanged(new TableModelEvent(this, selectedRow));
          }
        }
      }
    }
  }//GEN-LAST:event_tableValuesMouseClicked

  private void addNewHexValueToList(final int position, final HexValue v) {
    v.setIndex(position);
    if (position<this.values.size()){
      this.insertedIndexes.add(position);
    }
    
    this.values.add(position, v);
    for (final TableModelListener l : this.listeners) {
      l.tableChanged(new TableModelEvent(this));
    }
  }

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JToggleButton buttonAddPopup;
  private javax.swing.JButton buttonCancel;
  private javax.swing.JButton buttonRemoveSelected;
  private javax.swing.JButton buttonSave;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JScrollPane jScrollPane2;
  private javax.swing.JMenuItem menuItemHexTypeColor;
  private javax.swing.JMenuItem menuItemHexTypeSVG;
  private javax.swing.JPopupMenu popupSelectType;
  private javax.swing.JTable tableValues;
  private javax.swing.JScrollPane tableValuesScroll;
  private javax.swing.JTextPane textComments;
  private javax.swing.JTextField textName;
  // End of variables declaration//GEN-END:variables

  @Override
  public int getRowCount() {
    return this.values.size();
  }

  @Override
  public int getColumnCount() {
    return 4;
  }

  @Override
  public String getColumnName(final int columnIndex) {
    switch (columnIndex) {
      case 0:
        return "Value";
      case 1:
        return "Icon";
      case 2:
        return "Name";
      case 3:
        return "Commentary";
    }
    return null;
  }

  @Override
  public Class<?> getColumnClass(final int columnIndex) {
    switch (columnIndex) {
      case 0:
        return Integer.class;
      case 1:
        return Icon.class;
      case 2:
        return String.class;
      case 3:
        return String.class;
    }
    return null;
  }

  @Override
  public boolean isCellEditable(final int rowIndex, final int columnIndex) {
    return false;
  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
    final HexValue value = this.values.get(rowIndex);
    switch (columnIndex) {
      case 0:
        return rowIndex;
      case 1: {
        final Image prerasterized = value.makeIcon(ICON_SIZE, ICON_SIZE, this.iconShape);
        return new ImageIcon(prerasterized);
      }
      case 2:
        return value.getName();
      case 3:
        return value.getComment();
    }
    return null;
  }

  @Override
  public void setValueAt(final Object aValue, final int rowIndex, final int columnIndex) {
    final HexValue value = this.values.get(rowIndex);
    switch (columnIndex) {
      case 1:
        break;
      case 2:
        value.setName((String) aValue);
        break;
      case 3:
        value.setComment((String) aValue);
        break;
    }
  }

  public int getNewPosition(){
    final int currentSelected = this.tableValues.getSelectedRow();
    if (currentSelected <= 0) return this.values.size();
    if (JOptionPane.showConfirmDialog(null, "Insert the new value before the selected one?\nIf 'No' then the new one will be added at the end.","Confirmation",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
      return currentSelected;
    }else{
      return this.values.size();
    }
  }
  
  @Override
  public void addTableModelListener(final TableModelListener l) {
    this.listeners.add(l);
  }

  @Override
  public void removeTableModelListener(final TableModelListener l) {
    this.listeners.remove(l);
  }

  @Override
  public void valueChanged(final ListSelectionEvent e) {
    final int selectedRowindex = this.tableValues.getSelectedRow();
    this.buttonRemoveSelected.setEnabled(selectedRowindex > 0);
  }
}
