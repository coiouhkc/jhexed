package com.igormaznitsa.jhexed.hexmap;

import com.igormaznitsa.jhexed.engine.*;
import com.igormaznitsa.jhexed.renders.swing.ColorHexRender;
import com.igormaznitsa.jhexed.values.HexFieldValue;
import java.awt.*;

public class LayerableHexValueSourceRender extends ColorHexRender {

  private float lineWidth = 0.2f;
  private Stroke lineStroke = new BasicStroke(lineWidth);
  private Color borderColor = Color.black;
  private boolean showBorders = true;

  public LayerableHexValueSourceRender() {
    super();
  }

  public Color getCommonBorderColor() {
    return this.borderColor;
  }

  public void setCommonBorderColor(final Color color) {
    this.borderColor = color;
  }

  public void setLineWidth(final float value) {
    this.lineWidth = value;
    this.lineStroke = new BasicStroke(value);
  }

  public float getLineWidth() {
    return this.lineWidth;
  }

  public boolean isShowBorders() {
    return this.showBorders;
  }

  public void setShowBorders(final boolean value) {
    this.showBorders = value;
  }

  @Override
  public Color getBorderColor(final HexEngineModel<?> model, final int col, final int row) {
    if (showBorders) {
      return this.borderColor;
    }
    else {
      return null;
    }
  }

  @Override
  public Stroke getStroke() {
    return this.lineStroke;
  }

  @Override
  public Color getFillColor(final HexEngineModel<?> model, final int col, final int row) {
    return null;
  }

  @Override
  public void drawExtra(final HexEngine<Graphics2D> engine, final Graphics2D g, final int col, final int row, final Color borderColor, final Color fillColor) {
  }

  @Override
  public void drawUnderBorder(final HexEngine<Graphics2D> engine, final Graphics2D g, final int col, final int row, final Color borderColor, final Color fillColor) {
    final LayerableHexValueSource stackSource = (LayerableHexValueSource) engine.getModel();
    for(final HexFieldValue v : stackSource.getHexStackAtPosition(col, row)){
      if (v != null) {
        g.drawImage(v.getPrerasterized(), 0, 0, null);
      }
    }
  }

}