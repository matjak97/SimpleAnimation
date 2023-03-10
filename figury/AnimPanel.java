package figury;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

public class AnimPanel extends JPanel implements ActionListener {
    /**
     *
     */
    @Serial
    private static final long serialVersionUID = 1L;

    // bufor
    Image image;
    // wykreslacz ekranowy
    Graphics2D device;
    // wykreslacz bufora
    Graphics2D buffer;

    private int delay = 70;

    private final Timer timer;

    private static int numer = 0;

    public AnimPanel() {
        super();
        setBackground(Color.WHITE);
        timer = new Timer(delay, this);
    }

    public void initialize() {
        int width = getWidth();
        int height = getHeight();

        image = createImage(width, height);
        buffer = (Graphics2D) image.getGraphics();
        buffer.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        buffer.setBackground(Color.WHITE);
        device = (Graphics2D) getGraphics();
        device.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    void addFig() {
        Figura fig = (numer++ % 2 == 0) ? new Kwadrat(buffer, delay, getWidth(), getHeight())
                : new Elipsa(buffer, delay, getWidth(), getHeight());
        timer.addActionListener(fig);
        new Thread(fig).start();
    }

    void animate() {
        if (timer.isRunning()) {
            timer.stop();
            Figura.isMoving = false;
        } else {
            timer.start();
            Figura.isMoving = true;
        }
    }

    void changeSpeed(int delay){
        timer.setDelay(delay);
        this.delay = delay;
        Figura.updateDelay(timer.getDelay());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Image image = createImage(getWidth(), getHeight());
        image.getGraphics().drawImage(this.image,0,0,null);
        buffer = (Graphics2D) image.getGraphics();
        buffer.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        this.image = image;
        buffer.setBackground(Color.WHITE);
        Figura.updateCanvas(buffer, getWidth(), getHeight());
        device = (Graphics2D) getGraphics();
        device.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        device.drawImage(this.image, 0, 0, null);
        buffer.clearRect(0, 0, getWidth(), getHeight());
    }
}