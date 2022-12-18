package figury;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;

public class AnimatorApp extends JFrame {

    /**
     *
     */
    @Serial
    private static final long serialVersionUID = 1L;

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
        JPanel contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(null);

        AnimPanel kanwa = new AnimPanel();
        kanwa.setBounds(10, 11, 422, 219);
        contentPane.add(kanwa);
        SwingUtilities.invokeLater(kanwa::initialize);

        JButton btnAdd = new JButton("Add");
        btnAdd.addActionListener(e -> kanwa.addFig());
        btnAdd.setBounds(10, 239, 80, 23);
        contentPane.add(btnAdd);

        JButton btnAnimate = new JButton("Animate");
        btnAnimate.addActionListener(e -> kanwa.animate());
        btnAnimate.setBounds(100, 239, 80, 23);
        contentPane.add(btnAnimate);

    }

}