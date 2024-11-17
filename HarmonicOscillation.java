import javax.swing.*;
import java.awt.*;

public class HarmonicOscillation extends JFrame {
    private JPanel canvas;
    private JButton startButton, stopButton;
    private JTextField qField, wField;
    private Timer timer;
    private double q = 200; // Длина отрезка
    private double w = 1;   // Угловая частота
    private double t = 0;   // Время

    public HarmonicOscillation() {
        setTitle("Гармонические колебания");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Поля ввода и кнопки
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(2, 3));

        controlPanel.add(new JLabel("Длина отрезка (q):"));
        qField = new JTextField(Double.toString(q));
        controlPanel.add(qField);

        controlPanel.add(new JLabel("Угловая частота (w):"));
        wField = new JTextField(Double.toString(w));
        controlPanel.add(wField);

        startButton = new JButton("Старт");
        stopButton = new JButton("Стоп");
        stopButton.setEnabled(false);

        controlPanel.add(startButton);
        controlPanel.add(stopButton);

        add(controlPanel, BorderLayout.NORTH);

        // Поле рисования
        canvas = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawOscillation(g);
            }
        };
        add(canvas, BorderLayout.CENTER);

        // Таймер для анимации
        timer = new Timer(16, e -> {
            t += 0.05;
            canvas.repaint();
        });

        // Слушатели для кнопок
        startButton.addActionListener(e -> startAnimation());
        stopButton.addActionListener(e -> stopAnimation());
    }

    private void drawOscillation(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        // Центр линии
        int lineY = height / 2;
        int leftX = (width - (int) q) / 2;
        int rightX = leftX + (int) q;

        // Рисуем линию
        g2d.drawLine(leftX, lineY, rightX, lineY);

        // Вычисляем положение точки
        int pointX = leftX + (int) (q * (1 + Math.cos(w * t)) / 2);
        int pointY = lineY;

        // Рисуем точку
        g2d.fillOval(pointX - 5, pointY - 5, 10, 10);
    }

    private void startAnimation() {
        try {
            q = Double.parseDouble(qField.getText());
            w = Double.parseDouble(wField.getText());
            t = 0;
            timer.start();
            startButton.setEnabled(false);
            stopButton.setEnabled(true);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Введите корректные значения для q и w.", "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void stopAnimation() {
        timer.stop();
        startButton.setEnabled(true);
        stopButton.setEnabled(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HarmonicOscillation frame = new HarmonicOscillation();
            frame.setVisible(true);
        });
    }
}
