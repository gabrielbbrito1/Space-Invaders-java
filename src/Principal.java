import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Principal {
	// atributos ----------------------------------
	public static final int LARGURA_TELA = 800;
	public static final int ALTURA_TELA = 600;
	
	// construtor ---------------------------------
	public Principal() { 
		JFrame janela = new JFrame("Space Invaders"); // cria um novo painel //
		Game game = new Game();
		game.setPreferredSize( new Dimension(LARGURA_TELA, ALTURA_TELA) );
		janela.getContentPane().add(game);
		// configurar aspectos da janela
		janela.setResizable(false); //janela não pode ser redimensionada
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // sai ao clicar no x da janela //
		janela.setLocation(100, 100); // onde janela vai abrir //
		janela.setVisible(true);
		janela.pack();
	}
	
	public static void main(String[] args) {
		new Principal(); // 
	}
}
