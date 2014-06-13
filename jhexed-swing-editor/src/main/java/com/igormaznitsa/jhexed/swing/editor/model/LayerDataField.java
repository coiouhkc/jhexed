package com.igormaznitsa.jhexed.swing.editor.model;

import com.igormaznitsa.jhexed.engine.*;
import com.igormaznitsa.jhexed.engine.misc.HexPosition;
import com.igormaznitsa.jhexed.renders.Utils;
import com.igormaznitsa.jhexed.swing.editor.model.values.HexValue;
import java.awt.geom.Path2D;
import java.io.*;
import java.util.*;
import org.apache.commons.io.IOUtils;

public class LayerDataField implements HexEngineModel<Byte> {

  private int columns;
  private int rows;

  private boolean visible;
  private String name;
  private String comments;

  private byte[] array;
  private final Byte defaultValue = (byte) 0;

  private final List<HexValue> values = new ArrayList<HexValue>();

  private final List<CopyOfLayerState> listUndo = new ArrayList<CopyOfLayerState>();
  private final List<CopyOfLayerState> listRedo = new ArrayList<CopyOfLayerState>();

  private static class CopyOfLayerState {
    private final String name;
    private final String comment;
    private final boolean visible;
    private final int columns;
    private final int rows;
    private final byte [] array;
    private final HexValue [] values;
    
    public CopyOfLayerState(final LayerDataField fld){
      this.name = fld.name;
      this.comment = fld.comments;
      this.array = fld.array.clone();
      this.columns = fld.columns;
      this.rows = fld.rows;
      this.visible = fld.visible;
      
      this.values = new HexValue[fld.values.size()];
      for(int i=0; i<fld.values.size();i++){
        this.values [i] = fld.values.get(i).cloneValue();
      }
    }
    
    public void restoreLayer(final LayerDataField fld){
      fld.name = this.name;
      fld.comments = this.comment;
      fld.visible = this.visible;
      fld.columns = this.columns;
      fld.rows = this.rows;
      fld.array = this.array.clone();
      
      fld.values.clear();
      for(final HexValue v : this.values){
        fld.values.add(v.cloneValue());
      }
    }
  }
  
  
  LayerDataField(final String name, final String comments, final int width, final int height) {
    if (name == null) {
      throw new NullPointerException("Name is null");
    }
    this.visible = true;
    this.comments = comments == null ? "" : comments;
    this.columns = width;
    this.rows = height;
    this.array = new byte[this.columns * this.rows];
    this.name = name;
    this.values.add(HexValue.NULL);
  }

  LayerDataField(final InputStream in) throws IOException {
    final DataInputStream din = in instanceof DataInputStream ? (DataInputStream) in : new DataInputStream(in);
    this.name = din.readUTF();
    this.comments = din.readUTF();

    final int valuesNum = din.readShort() & 0xFFFF;
    for (int i = 0; i < valuesNum; i++) {
      this.values.add(HexValue.readValue(in));
    }

    this.columns = din.readInt();
    this.rows = din.readInt();
    this.visible = din.readBoolean();
  
    final byte [] packedLayerData = new byte [din.readInt()];
    IOUtils.readFully(din, packedLayerData);
    this.array = Utils.unpackArray(packedLayerData);

    if (this.array.length!=(this.columns*this.rows)) throw new IOException("Wrong field size");
  }

  private LayerDataField(final LayerDataField layer) {
    this.visible = layer.visible;
    this.columns = layer.columns;
    this.rows = layer.rows;
    this.name = layer.name;
    this.comments = layer.comments;
    this.array = layer.array.clone();

    for (final HexValue h : layer.values) {
      this.values.add(h.cloneValue());
    }
  }

  public LayerDataField cloneLayer() {
    return new LayerDataField(this);
  }

  public void loadFromAnotherInstance(final LayerDataField layer) {
    this.visible = layer.visible;
    this.columns = layer.columns;
    this.rows = layer.rows;
    this.name = layer.name;
    this.comments = layer.comments;
    this.array = layer.array.clone();

    this.values.clear();
    this.values.addAll(layer.values);
  }

  public void write(final OutputStream out) throws IOException {
    final DataOutputStream dout = out instanceof DataOutputStream ? (DataOutputStream) out : new DataOutputStream(out);
    dout.writeUTF(this.name);
    dout.writeUTF(this.comments);

    dout.writeShort(this.values.size());
    for (int i = 0; i < this.values.size(); i++) {
      this.values.get(i).write(dout);
    }

    dout.writeInt(this.columns);
    dout.writeInt(this.rows);
    dout.writeBoolean(this.visible);
  
    final byte [] packed = Utils.packByteArray(this.array);
    dout.writeInt(packed.length);
    dout.write(packed);
    dout.flush();
  }

  public String getComments() {
    return this.comments;
  }

  public void setComments(final String text) {
    this.comments = text == null ? "" : text;
  }

  @Override
  public int getColumnNumber() {
    return this.columns;
  }

  @Override
  public int getRowNumber() {
    return this.rows;
  }

  @Override
  public Byte getValueAt(final int col, final int row) {
    if (this.isPositionValid(col, row)) {
      return Byte.valueOf(array[row * this.columns]);
    }
    else {
      return defaultValue;
    }
  }

  @Override
  public Byte getValueAt(final HexPosition pos) {
    return this.getValueAt(pos.getColumn(), pos.getRow());
  }

  @Override
  public void setValueAt(final int col, final int row, final Byte value) {
    if (this.isPositionValid(col, row)) {
      this.array[col + row * this.columns] = value.byteValue();
    }
  }

  @Override
  public void setValueAt(final HexPosition pos, final Byte value) {
    this.setValueAtPos(pos.getColumn(), pos.getRow(), value);
  }

  public void setValueAtPos(final HexPosition pos, final byte value) {
    this.setValueAtPos(pos.getColumn(), pos.getRow(), value);
  }

  public void setValueAtPos(final int col, final int row, final int i) {
    if (i > 255) {
      throw new IllegalArgumentException("Too big value, must be 0...255");
    }
    if (isPositionValid(col, row)) {
      this.array[col + row * this.columns] = (byte) i;
    }
  }

  @Override
  public boolean isPositionValid(int col, int row) {
    return col >= 0 && row >= 0 && col < this.columns && row < this.rows;
  }

  @Override
  public boolean isPositionValid(HexPosition pos) {
    return this.isPositionValid(pos.getColumn(), pos.getRow());
  }

  @Override
  public void attachedToEngine(HexEngine<?> engine) {
  }

  @Override
  public void detachedFromEngine(HexEngine<?> engine) {
  }

  public int getValueAtPos(final int column, final int row) {
    if (column < 0 || row < 0 || column >= this.columns || row >= this.rows) {
      return 0;
    }
    else {
      return this.array[column + (row * this.columns)] & 0xFF;
    }
  }

  public HexValue getHexValueAtPos(final int column, final int row) {
    final int value = this.getValueAtPos(column, row);
    return value == 0 ? null : this.values.get(value);
  }

  public String getLayerName() {
    return this.name;
  }

  public void setLayerName(final String name) {
    if (name == null) {
      throw new NullPointerException("Name is null");
    }
    this.name = name;
  }

  public boolean isLayerVisible() {
    return this.visible;
  }

  public void setVisible(final boolean flag) {
    this.visible = flag;
  }

  public void resize(final int newColumns, final int newRows) {
    final byte[] newArray = new byte[newColumns * newRows];

    Arrays.fill(newArray, defaultValue);

    final int columnsToCopy = Math.min(this.columns, newColumns);
    final int rowsToCopy = Math.min(this.rows, newRows);

    for (int i = 0; i < rowsToCopy; i++) {
      final int newLineStart = i * newColumns;
      final int oldLineStart = i * this.columns;
      System.arraycopy(this.array, oldLineStart, newArray, newLineStart, columnsToCopy);
    }

    this.array = newArray;
    this.columns = newColumns;
    this.rows = newRows;
  }

  public HexValue getHexValueForIndex(final int index) {
    if (index < 0 || index >= this.values.size()) {
      return null;
    }
    return this.values.get(index);
  }

  public int getHexValuesNumber() {
    return this.values.size();
  }

  public void replaceValues(final List<HexValue> values) {
    this.values.clear();
    this.values.addAll(values);
  }

  public void updatePrerasterizedIcons(final Path2D hexShape) {
    for (final HexValue h : this.values) {
      h.prerasterizeIcon(hexShape);
    }
  }
  
  public boolean hasUndo(){
    return !this.listUndo.isEmpty();
  }
  
  public boolean hasRedo(){
    return !this.listRedo.isEmpty();
  }
  
  public void addUndo(){
    this.listRedo.clear();
    this.listUndo.add(new CopyOfLayerState(this));
    if (this.listUndo.size()>10){
      this.listUndo.remove(0);
    }
  }
  
  public void resetRedoUndo(){
    this.listRedo.clear();
    this.listUndo.clear();
  } 
  
  public boolean undo(){
    if (!this.listUndo.isEmpty()){
      this.listRedo.add(new CopyOfLayerState(this));
      final CopyOfLayerState undoState = this.listUndo.remove(this.listUndo.size()-1);
      undoState.restoreLayer(this);
      return true;
    }
    return false;
  }
  
  public boolean redo(){
    if (!this.listRedo.isEmpty()){
      this.listUndo.add(new CopyOfLayerState(this));
      final CopyOfLayerState redoState = this.listRedo.remove(this.listRedo.size() - 1);
      redoState.restoreLayer(this);
      return true;
    }
    return false;
  }
  
  public void updateIndexes(final List<Integer> removedIndexes, final List<Integer> insertedIndexes, final int numberOfActualElements) {

    for (final Integer i : removedIndexes) {
      if (insertedIndexes.contains(i)) {
        continue;
      }
      for (int arrayIndex = 0; arrayIndex < this.array.length; arrayIndex++) {
        final int current = this.array[arrayIndex] & 0xFF;
        if (current == i) {
          this.array[arrayIndex] = 0;
        }
        else if (current > i) {
          this.array[arrayIndex] = (byte) (current - 1);
        }
      }
    }

    for (final Integer i : insertedIndexes) {
      if (removedIndexes.contains(i)) {
        continue;
      }
      for (int arrayIndex = 0; arrayIndex < this.array.length; arrayIndex++) {
        final int current = this.array[arrayIndex] & 0xFF;
        if (current == i) {
          this.array[arrayIndex] = 0;
        }
        else if (current >= i) {
          this.array[arrayIndex] = (byte) (current + 1);
        }
      }
    }

    for (int arrayIndex = 0; arrayIndex < this.array.length; arrayIndex++) {
      final int current = this.array[arrayIndex] & 0xFF;
      if (current >= numberOfActualElements) {
        this.array[arrayIndex] = 0;
      }
    }
  }
}
