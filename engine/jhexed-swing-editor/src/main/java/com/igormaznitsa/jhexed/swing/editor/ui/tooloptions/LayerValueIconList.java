/* 
 * Copyright 2014 Igor Maznitsa (http://www.igormaznitsa.com).
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
package com.igormaznitsa.jhexed.swing.editor.ui.tooloptions;

import com.igormaznitsa.jhexed.hexmap.HexFieldLayer;
import com.igormaznitsa.jhexed.values.HexFieldValue;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Path2D;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.apache.commons.lang3.StringEscapeUtils;
import org.jdesktop.swingx.WrapLayout;

public class LayerValueIconList extends JScrollPane implements ListSelectionListener {
  private static final long serialVersionUID = 4067088203855017500L;
 
  private static final int ICON_SIZE = 48;
  private HexFieldLayer currentLayerField;

  private static final Border EMPTY_BORDER = BorderFactory.createEmptyBorder(3, 3, 3, 3);
  private static final Border LINE_BORDER = BorderFactory.createDashedBorder(Color.ORANGE.darker().darker(),  3.0f, 2.0f, 1.0f, true);

  private ListSelectionModel selectionModel;

  public interface LayerIconListListener {
    void onLeftClick(final HexFieldValue h, final ImageIcon icon);
    void onRightClick(final HexFieldValue h, final ImageIcon icon);
  }
  
  public static class HexButton extends JLabel {
    private static final long serialVersionUID = -6733971540369351944L;
    private final HexFieldValue value;
    private final LayerValueIconList parent;
    
    public HexFieldValue getHexValue(){
      return this.value;
    }
    
    public HexButton(final LayerValueIconList parent, final HexFieldValue hex){
      super();
      this.setBorder(EMPTY_BORDER);
      
      this.parent = parent;
      this.value = hex;
      setOpaque(false);
      setIcon(new ImageIcon(hex.makeIcon(ICON_SIZE, ICON_SIZE, this.parent.iconHexShape, false)));

      final HexButton theInstance = this;
      
      addMouseListener(new MouseListener() {

        @Override
        public void mouseClicked(MouseEvent e) {
          parent.processMouseClick(theInstance,e);
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
      });

      if (!hex.getName().isEmpty() || !hex.getComment().isEmpty()){
        final String name = hex.getName().isEmpty() ? "": "<h3>"+StringEscapeUtils.escapeHtml4(hex.getName())+"</h3>";
        final String comment = hex.getComment().isEmpty() ? "": "<p>"+StringEscapeUtils.escapeHtml4(hex.getComment())+"</p>";
        this.setToolTipText("<html>"+name+comment+"</html>");
      }
    }

    public ImageIcon getHexIcon(){
      return (ImageIcon) getIcon();
    }
  }
  
  private final JPanel content;
  private Path2D iconHexShape;
  
  private final List<LayerIconListListener> listeners = new ArrayList<LayerIconListListener>();
  
  public LayerValueIconList(){
    super();

    this.content = new JPanel(new WrapLayout(WrapLayout.LEFT));
    this.getViewport().setBackground(Color.white);
    this.content.setBackground(Color.white);
  
    this.getViewport().add(this.content);

    this.selectionModel = new DefaultListSelectionModel();
    this.selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    this.selectionModel.addListSelectionListener(this);
  }

  public HexFieldValue [] getSelected(){
    final List<HexFieldValue> selected = new ArrayList<HexFieldValue>();
    for(final Component compo : this.content.getComponents()){
      final HexButton b = (HexButton)compo;
      if (this.selectionModel.isSelectedIndex(b.value.getIndex())){
        selected.add(b.value);
      }
    }
    return selected.toArray(new HexFieldValue[selected.size()]);
  }
  
  public ListSelectionModel getSelectionModel(){
    return this.selectionModel;
  }
  
  public void setSelectionModel(final ListSelectionModel selectionModel){
    this.selectionModel.removeListSelectionListener(this);
    this.selectionModel = selectionModel;
    this.selectionModel.addListSelectionListener(this);
    valueChanged(null);
  }

  @Override
  public void valueChanged(final ListSelectionEvent e) {
    for(final Component compo : this.content.getComponents()){
      final HexButton b = (HexButton) compo;
      if (this.selectionModel.isSelectedIndex(b.value.getIndex())){
        b.setBorder(LINE_BORDER);
      }else{
        b.setBorder(EMPTY_BORDER);
      }
    }
    this.content.revalidate();
    this.content.repaint();
  }
  
  protected void processMouseClick(final HexButton source, final MouseEvent evt){
    if ((evt.getModifiersEx() & (MouseEvent.CTRL_DOWN_MASK| MouseEvent.SHIFT_DOWN_MASK)) != 0){
      if ((evt.getModifiersEx() & MouseEvent.CTRL_DOWN_MASK)!=0){
        this.selectionModel.addSelectionInterval(source.value.getIndex(), source.value.getIndex());
      }else {
        final int selIndex = source.value.getIndex();
        final int minindex = Math.min(selIndex, this.selectionModel.getMinSelectionIndex());
        final int maxindex = Math.max(selIndex, this.selectionModel.getMaxSelectionIndex());
        this.selectionModel.setSelectionInterval(Math.max(0, minindex), Math.max(0, maxindex));
      }
    }else{
      this.selectionModel.setSelectionInterval(source.value.getIndex(), source.value.getIndex());
    }
    
    switch(evt.getButton()){
      case MouseEvent.BUTTON1:{
        for (final LayerIconListListener l : this.listeners) {
          l.onLeftClick(source.getHexValue(), source.getHexIcon());
        }
      }break;
      case MouseEvent.BUTTON3:{
        for(final LayerIconListListener l : this.listeners){
          l.onRightClick(source.getHexValue(),source.getHexIcon());
        }
      }break;
    }
  }

  public void clearSelection(){
    this.selectionModel.clearSelection();
  }
  
  public void addLayerIconListListener(final LayerIconListListener l){
    this.listeners.add(l);
  }
  
  public void removeLayerIconListListener(final LayerIconListListener l){
    this.listeners.remove(l);
  }
  
  public void setLayerField(final HexFieldLayer layer){
    this.currentLayerField = layer;  
    refill();
  }

  public void setIconShape(final Path2D hexShape){
    this.iconHexShape = hexShape;
    refill();
  }
  
  private void refill(){
    this.content.removeAll();

    if (currentLayerField != null) {
      for (int i = 0; i < currentLayerField.getHexValuesNumber(); i++) {
        final HexFieldValue value = currentLayerField.getHexValueForIndex(i);
        this.content.add(new HexButton(this, value));
      }
    }

    this.content.revalidate();
    valueChanged(null);
    repaint();
  }
  
}
