package com.igormaznitsa.jhexed.swing.editor.ui.tooloptions;

import com.igormaznitsa.jhexed.swing.editor.model.*;
import com.igormaznitsa.jhexed.swing.editor.model.values.HexValue;
import java.awt.geom.Path2D;
import javax.swing.ImageIcon;

public class EraserOptions extends javax.swing.JPanel implements AppBus.AppBusListener, LayerIconList.LayerIconListListener {
  private static final long serialVersionUID = 2906524676479899740L;
  
  private HexValue selectedValue;
  
  public EraserOptions() {
    initComponents();
    AppBus.getInstance().addAppBusListener(this);
    AppBus.getInstance().fireEvent(this, AppBus.AppBusEvent.REQUEST_EVENT, AppBus.AppBusEvent.HEX_SHAPE);
  
    this.layerIconList.addLayerIconListListener(this);
  }

  public HexValue getHexValue(){
    return this.selectedValue;
  }
  
  public int getPencilWidth(){
    return this.sliderWidth.getValue();
  }
  
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jPanel1 = new javax.swing.JPanel();
    sliderWidth = new javax.swing.JSlider();
    layerIconList = new com.igormaznitsa.jhexed.swing.editor.ui.tooloptions.LayerIconList();

    jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Radius of the eraser"));

    sliderWidth.setMajorTickSpacing(1);
    sliderWidth.setMaximum(10);
    sliderWidth.setMinimum(1);
    sliderWidth.setMinorTickSpacing(1);
    sliderWidth.setPaintLabels(true);
    sliderWidth.setPaintTicks(true);
    sliderWidth.setSnapToTicks(true);
    sliderWidth.setToolTipText("Radius of the tool");
    sliderWidth.setValue(1);

    javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
    jPanel1.setLayout(jPanel1Layout);
    jPanel1Layout.setHorizontalGroup(
      jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel1Layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(sliderWidth, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
        .addContainerGap())
    );
    jPanel1Layout.setVerticalGroup(
      jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel1Layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(sliderWidth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap())
    );

    sliderWidth.getAccessibleContext().setAccessibleDescription("Width of the pencil");

    layerIconList.setBorder(javax.swing.BorderFactory.createTitledBorder("Value to erase"));

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
          .addComponent(layerIconList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(layerIconList, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
        .addContainerGap())
    );
  }// </editor-fold>//GEN-END:initComponents


  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JPanel jPanel1;
  private com.igormaznitsa.jhexed.swing.editor.ui.tooloptions.LayerIconList layerIconList;
  private javax.swing.JSlider sliderWidth;
  // End of variables declaration//GEN-END:variables


  @Override
  public void onAppBusEvent(final Object source, final AppBus bus, final AppBus.AppBusEvent event, final Object... objects) {
    switch(event){
      case SELECTED_LAYER_CHANGED:{
        final LayerDataField layer = (LayerDataField) objects[0];
        layerIconList.setLayerField(layer);
        this.selectedValue = null;
        this.layerIconList.setSelectedHexValue(null);
      }break;
      case HEX_SHAPE:{
        this.layerIconList.setIconShape((Path2D)objects[0]);
      }break;
    }
  }

  @Override
  public void onLeftClick(final HexValue value, final ImageIcon icon) {
    this.selectedValue = value;
    this.layerIconList.setSelectedHexValue(value);
  }

  @Override
  public void onRightClick(final HexValue value, final ImageIcon icon) {
    this.selectedValue = value;
    this.layerIconList.setSelectedHexValue(value);
  }

  
}
