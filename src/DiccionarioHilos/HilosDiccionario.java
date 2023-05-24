package DiccionarioHilos;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class HilosDiccionario extends JFrame {
    JTextArea textArea;
    private AutoGuardar autoGuardar;
    private CorregirOrtografia corregirOrtografia;
    private List<String> diccionario;

    public HilosDiccionario() {
        setTitle("Formulario usando Hilos ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        textArea = new JTextArea();
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        cargarDiccionario();

        autoGuardar = new AutoGuardar();
        autoGuardar.start();

        corregirOrtografia = new CorregirOrtografia();
        corregirOrtografia.start();

        setSize(500, 300);
        setLocationRelativeTo(null);
        setVisible(true);

        cargarTexto();
    }

    private void cargarDiccionario() {
        diccionario = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("dict.txt"))) {
            String word;
            while ((word = reader.readLine()) != null) {
                if (word.matches("[a-zA-Z]+")) {
                    diccionario.add(word);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void cargarTexto() {
        try (BufferedReader reader = new BufferedReader(new FileReader("guardar.txt"))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            textArea.setText(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void guardarTexto() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("guardar.txt"))) {
            writer.write(textArea.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class AutoGuardar extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    sleep(5000);
                    guardarTexto();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class CorregirOrtografia extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    sleep(60000);
                    String[] words = textArea.getText().split("\\s+");
                    for (String word : words) {
                        if (word.matches("[a-zA-Z]+") && !diccionario.contains(word.toLowerCase())) {
                            SwingUtilities.invokeLater(() -> {
                                int startIndex = textArea.getText().indexOf(word);
                                int endIndex = startIndex + word.length();
                                Highlighter.HighlightPainter highlightPainter =
                                        new DefaultHighlighter.DefaultHighlightPainter(Color.RED);
                                try {
                                    textArea.getHighlighter().addHighlight(startIndex, endIndex, highlightPainter);
                                } catch (BadLocationException e) {
                                    e.printStackTrace();
                                }
                            });
                        } else {
                            SwingUtilities.invokeLater(() -> {
                                int startIndex = textArea.getText().indexOf(word);
                                int endIndex = startIndex + word.length();
                                Highlighter.Highlight[] highlights = textArea.getHighlighter().getHighlights();
                                for (Highlighter.Highlight highlight : highlights) {
                                    if (highlight.getStartOffset() >= startIndex && highlight.getEndOffset() <= endIndex) {
                                        textArea.getHighlighter().removeHighlight(highlight);
                                    }
                                }
                            });
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(HilosDiccionario::new);
    }
}