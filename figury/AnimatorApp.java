package figury;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.Serial;

public class AnimatorApp extends JFrame {

    /**
     *
     */
    @Serial
    private static final long serialVersionUID = 1L;

    AnimPanel kanwa;

    JPanel contentPane;
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                final AnimatorApp frame = new AnimatorApp();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the frame.
     * @param delay
     */
    public AnimatorApp() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int ww = 450, wh = 300;
        setBounds((screen.width-ww)/2, (screen.height-wh)/2, ww, wh);
        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(null);

        kanwa = new AnimPanel();
        kanwa.setBounds(10, 11, 422, 219);
        contentPane.add(kanwa);
        SwingUtilities.invokeLater(kanwa::initialize);

        JButton btnAdd = new JButton("Add");
        btnAdd.addActionListener(e -> kanwa.addFig());
        btnAdd.setBounds(10, 239, 90, 23);
        contentPane.add(btnAdd);

        JButton btnAnimate = new JButton("Animate");
        btnAnimate.addActionListener(e -> kanwa.animate());
        btnAnimate.setBounds(110, 239, 90, 23);
        contentPane.add(btnAnimate);

        JToggleButton btnSpeedx5 = new JToggleButton("Speed x5");
        btnSpeedx5.addItemListener(e -> {
            int state = e.getStateChange();
            if (state == ItemEvent.SELECTED) {
                kanwa.changeSpeed(14);
            } else {
                kanwa.changeSpeed(70);
            }
        });
        btnSpeedx5.setBounds(210, 239,90,23);
        contentPane.add(btnSpeedx5);

        contentPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                updateSize(btnAdd, btnAnimate, btnSpeedx5);
            }

            @Override
            public void mouseEntered(MouseEvent e){
                updateSize(btnAdd, btnAnimate, btnSpeedx5);
            }
        });
    }

    private void updateSize(JButton btnAdd, JButton btnAnim, JToggleButton btnSpeedx5){
        kanwa.setSize(getWidth()-28,getHeight()-81);
        btnAdd.setLocation(10,getHeight()-61);
        btnAnim.setLocation(110,getHeight()-61);
        btnSpeedx5.setLocation(210, getHeight()-61);
    }

}