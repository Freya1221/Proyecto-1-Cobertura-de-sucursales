/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaces;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author sebas
 */
public class GraphScrollPanel extends JPanel {
    private JScrollPane scrollPane;

    public GraphScrollPanel(JPanel graphPanel) {
        // Crear un JScrollPane que envuelve el panel del grafo
        scrollPane = new JScrollPane(graphPanel);
        scrollPane.setPreferredSize(new Dimension(1000, 800));  // Ajustar el tamaño de la ventana
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Añadir el JScrollPane al panel principal
        this.setLayout(new BorderLayout());
        this.add(scrollPane, BorderLayout.CENTER);
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }
}
