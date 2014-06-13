package com.igormaznitsa.jhexed.swing.editor.components;

import com.igormaznitsa.jhexed.engine.*;
import com.igormaznitsa.jhexed.engine.misc.HexPosition;
import com.igormaznitsa.jhexed.engine.misc.HexRect2D;
import java.awt.*;
import java.awt.geom.Path2D;
import java.util.concurrent.locks.ReentrantLock;
import javax.swing.JComponent;

public class HexFieldComponent extends JComponent implements HexMapPanelComponent {

  private static final long serialVersionUID = 3010379244247138341L;

  private final ReentrantLock engineLock = new ReentrantLock();
  private HexEngine<Graphics2D> engine;
  private final HexMapPanel parent;
  private final LayerHexFieldRender renderer = new LayerHexFieldRender();

  private float insideZoomX = 1.0f;
  private float insideZoomY = 1.0f;

  public final static int DEFAULT_ADAPT_WIDTH = 7000;
  public final static int DEFAULT_ADAPT_HEIGHT = 5000;

  private int adaptWidth = DEFAULT_ADAPT_WIDTH;
  private int adaptHeigh = DEFAULT_ADAPT_HEIGHT;

  public HexFieldComponent(final HexMapPanel parent, final HexEngineModel<?> model) {
    super();
    this.parent = parent;
    setOpaque(false);
    setDoubleBuffered(false);

    this.engine = new HexEngine<Graphics2D>(48, 48, HexEngine.ORIENTATION_HORIZONTAL);
    this.engine.setModel(model);
    this.engine.setRenderer(renderer);
    processSize();
  }

  public void reconfigureEngine(final float cellWidth, final float cellheight, final int orientation) {
    this.engineLock.lock();
    try {
      this.engine = new HexEngine<Graphics2D>(cellWidth, cellheight, orientation);
      this.engine.setRenderer(this.renderer);
      adaptToSize(this.adaptWidth, this.adaptHeigh);
    }
    finally {
      this.engineLock.unlock();
    }
    repaint();
  }

  @Override
  public void onHexMapPanelInsideChange(final HexMapPanel parent, final int eventId) {
    if (eventId == HexMapPanel.EVENT_ZOOM_CHANGED) {
      processSize();
    }
  }

  private void processSize() {
    final float zoom = this.parent.getZoom();

    final float scalex = this.insideZoomX * zoom;
    final float scaley = this.insideZoomY * zoom;

    this.engineLock.lock();
    try {

      if (this.engine != null) {
        this.engine.setScale(scalex, scaley);

        final HexRect2D viewBox = engine.getVisibleSize();

        final Dimension dim = new Dimension(viewBox.getWidthAsInt() + 1, viewBox.getHeightAsInt() + 1);
        this.setSize(dim);
        this.setMaximumSize(dim);
        this.setMinimumSize(dim);
        this.setPreferredSize(dim);
        revalidate();
        repaint();
      }
    }
    finally {
      this.engineLock.unlock();
    }
  }

  @Override
  public void paintComponent(final Graphics g) {
    this.engineLock.lock();
    try {
      final Rectangle clipRect = g.getClipBounds();
      final HexRect2D r = new HexRect2D(clipRect.x, clipRect.y, clipRect.width, clipRect.height);
      if (this.engine != null) {
        engine.drawArea((Graphics2D) g, r, false);
      }
    }
    finally {
      this.engineLock.lock();
    }
  }

  public boolean changeEngineParameters(int orientation) {
    this.engineLock.lock();
    try {
      if (this.engine != null && this.engine.getOrientation() != orientation) {
        this.engine.changeEngineBaseParameters(this.engine.getCellWidth(), this.engine.getCellHeight(), orientation);
        adaptToSize(this.adaptWidth, this.adaptHeigh);
        return true;
      }
    }
    finally {
      this.engineLock.lock();
    }
    return false;
  }

  public HexPosition getHexCoordForPoint(final Point pnt) {
    this.engineLock.lock();
    try {
      final int column = this.engine.calculateColumn(pnt.x, pnt.y);
      final int row = this.engine.calculateRow(pnt.x, pnt.y);
      return new HexPosition(column, row);
    }
    finally {
      this.engineLock.unlock();
    }
  }

  public void setAntialiased(final boolean flag) {
    if (this.renderer != null) {
      this.renderer.setAntialias(flag);
      this.repaint();
    }
  }

  public void adaptToSize(final int width, final int height) {
    this.adaptWidth = width;
    this.adaptHeigh = height;

    this.engineLock.lock();
    try {
      this.engine.setScale(this.parent.getZoom(), this.parent.getZoom());
      final HexRect2D rect = this.engine.getVisibleSize();

      final float xcoeff = (float) width / rect.getWidth();
      final float ycoeff = (float) height / rect.getHeight();

      this.insideZoomX = xcoeff;
      this.insideZoomY = ycoeff;

      processSize();
    }
    finally {
      this.engineLock.unlock();
    }
  }

  public LayerHexFieldRender getRenderer() {
    return this.renderer;
  }

  public int getHexOrientation() {
    this.engineLock.lock();
    try {
      return this.engine.getOrientation();
    }
    finally {
      this.engineLock.unlock();
    }
  }

  public Path2D getHexShape() {
    return ((LayerHexFieldRender) this.engine.getRenderer()).getHexPath();
  }

  public boolean isPositionValid(final HexPosition position) {
    return this.engine.getModel().isPositionValid(position);
  }

  public HexEngine<?> getHexEngine() {
    return this.engine;
  }
}
