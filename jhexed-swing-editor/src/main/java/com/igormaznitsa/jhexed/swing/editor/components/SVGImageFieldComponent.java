package com.igormaznitsa.jhexed.swing.editor.components;

import com.igormaznitsa.jhexed.renders.svg.SVGImage;
import com.igormaznitsa.jhexed.swing.editor.Log;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.*;
import javax.swing.*;

public final class SVGImageFieldComponent extends JComponent implements HexMapPanelComponent {

  private static final long serialVersionUID = 7548716206830303193L;

  private SVGImage svgImage;
  private final HexMapPanel parent;

  private boolean showImage = true;

  public SVGImageFieldComponent(final HexMapPanel parent) {
    super();
    this.parent = parent;

    setDoubleBuffered(false);
    updateSizeForImage();
  }

  public void setSVGImage(final SVGImage img) {
    this.svgImage = img;
    updateSizeForImage();
    revalidate();
    repaint();
  }

  public SVGImage getSVGImage() {
    return this.svgImage;
  }

  private void updateSizeForImage() {
    float w = 0;
    float h = 0;

    if (this.svgImage != null) {
      w = this.svgImage.getSVGWidth();
      h = this.svgImage.getSVGHeight();
    }

    final float zoom = this.parent.getZoom();

    w *= zoom;
    h *= zoom;

    final Dimension d = new Dimension(Math.round(w), Math.round(h));
    if (SwingUtilities.isEventDispatchThread()) {
      setSize(d);
      setMinimumSize(d);
      setMaximumSize(d);
      setPreferredSize(d);
    }
    else {
      SwingUtilities.invokeLater(new Runnable() {

        @Override
        public void run() {
          setSize(d);
          setMinimumSize(d);
          setMaximumSize(d);
          setPreferredSize(d);
        }
      });
    }
  }

  @Override
  protected void paintComponent(final Graphics g) {
    if (this.showImage) {
      try {
        final Container c = getParent();
        final Graphics2D g2d = (Graphics2D) g;
        final AffineTransform t = g2d.getTransform();

        final AffineTransform z = new AffineTransform(t);

        final float zoom = this.parent.getZoom();

        z.scale(zoom, zoom);

        g2d.setTransform(z);

        svgImage.render(g2d);

        g2d.setTransform(t);

        g2d.setColor(Color.red);
        g2d.drawRect(0, 0, getBounds().width, getBounds().height);

      }
      catch (IOException ex) {
        Log.error("Can't paint component", ex);
      }
    }
  }

  @Override
  public void onHexMapPanelInsideChange(final HexMapPanel parent, final int eventId) {
    if (eventId == HexMapPanel.EVENT_ZOOM_CHANGED) {
      updateSizeForImage();
      revalidate();
      repaint();
    }
  }

  public boolean isShowImage() {
    return this.showImage;
  }

  public boolean setShowImage(final boolean flag) {
    if (this.showImage != flag) {
      this.showImage = flag;
      revalidate();
      repaint();
      return true;
    }
    return false;
  }
}
