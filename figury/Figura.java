/**
 *
 */
package figury;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.util.Random;

/**
 * @author tb
 *
 */
public abstract class Figura implements Runnable, ActionListener/*, Shape*/ {

    // wspolny bufor
    protected static Graphics2D buffer;
    protected Area area;
    // do wykreslania
    protected Shape shape;
    // przeksztalcenie obiektu
    protected AffineTransform aft;

    // przesuniecie
    private int dx, dy;
    // rozciaganie
    private double sf;
    // kat obrotu
    private final double an;
    private static int delay;
    private static int width;
    private static int height;
    private final Color clr;
    public static boolean isMoving = false;

    protected static final Random rand = new Random();

    public Figura(Graphics2D buf, int del, int w, int h) {
        delay = del;
        buffer = buf;
        width = w;
        height = h;

        dx = 1 + rand.nextInt(5);
        dy = 1 + rand.nextInt(5);
        sf = 1 + 0.05 * rand.nextDouble();
        an = 0.1 * rand.nextDouble();

        clr = new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));
        // reszta musi być zawarta w realizacji klasy Figure
        // (tworzenie figury i przygotowanie transformacji)
    }

    @Override
    public void run() {
        // przesuniecie na srodek
        aft.translate(100, 100);
        area.transform(aft);
        shape = area;

        while (true) {
            // przygotowanie nastepnego kadru
            if(isMoving)
                shape = nextFrame();
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
            }
        }
    }

    protected Shape nextFrame() {
        // zapamietanie na zmiennej tymczasowej
        // aby nie przeszkadzalo w wykreslaniu
        area = new Area(area);
        aft = new AffineTransform();
        Rectangle bounds = area.getBounds();
        int cx = bounds.x + bounds.width / 2;
        int cy = bounds.y + bounds.height / 2;
        // odbicie
        if (dx < 0 && cx < 0 || dx > 0 && cx > width)
            dx = -dx;
        if (dy < 0 && cy < 0 || dy > 0 && cy > height)
            dy = -dy;
        // zwiekszenie lub zmniejszenie
        if (sf > 1 && bounds.height > height / 2 || sf < 1 && bounds.height < 20)
            sf = 1 / sf;
        // konstrukcja przeksztalcenia
        aft.translate(cx, cy);
        aft.scale(sf, sf);
        aft.rotate(an);
        aft.translate(-cx, -cy);
        aft.translate(dx, dy);
        // przeksztalcenie obiektu
        area.transform(aft);
        return area;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        // wypelnienie obiektu
        buffer.setColor(clr.brighter());
        buffer.fill(shape);
        // wykreslenie ramki
        buffer.setColor(clr.darker());
        buffer.draw(shape);
    }

    public static void updateCanvas(Graphics2D buf, int w, int h){
        buffer = buf;
        width = w;
        height = h;
    }

    public static void updateDelay(int del){
        delay = del;
    }

}