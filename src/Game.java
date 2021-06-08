import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Game extends JPanel{
	// atributos ----------------------------
	Spaceship nave;
	boolean k_esquerda;
	boolean k_direita;
	boolean k_space;
	boolean shooting;
	Background bg1;
	Background bg2;
	Aliens[][] listaAlien = new Aliens[6][8];
	Shot shot;
	
	// construtor ----------------------------
	public Game() {
		int X = 165; // posicao x inicial na tela do alien[1]
		int Y = 20; // posição y inicial da tela da coluna 1
		for (int i = 0; i < 6; i++) { // Instanciação dos aliens por matriz i,j 
			for (int j = 0; j < 8; j++) {
				Aliens alien = new Aliens();
	            alien.posX = X; // a cada 60 pixels um alien horizontalmente
	            alien.posY = Y; 
				listaAlien[i][j]= alien;
				X += 60;
			}
			X = 165;
			Y += 45; // a cada coluna diferença de 45 pixels.
		}
		addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
			
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_LEFT: // pega o valor da seta esquerda //
					k_esquerda = false;					
					break;
				case KeyEvent.VK_RIGHT: // pega o valor da seta direita //
					k_direita = false;
					break;
				case KeyEvent.VK_SPACE: // pega o valor do espaço //
					k_space = false;
					break;
				}
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_LEFT:
					k_esquerda = true;					
					break;
				case KeyEvent.VK_RIGHT:
					k_direita = true;
					break;
				case KeyEvent.VK_SPACE:
					k_space = true;
					break;
				}
				
			}
		});
		
		bg2 = new Background(); // instancia background 2
		bg1 = new Background(); // instancia background 1
		nave = new Spaceship(); // instancia nave
		shot = new Shot(); // instancia tiro
		bg1.posX = 0;
		bg1.posY = 0;
		bg2.posX = 0;
		bg2.posY = -1200;
		setFocusable(true); // tem a capacidade de receber foco
		setLayout(null); // 
		new Thread(new Runnable() {
			@Override
			public void run() {
				gameloop(); // é invocado em uma nova unidade de execução
			} // inner class
		} ).start();
	}

	// metodos gameloop ----------------------------

	public void gameloop() {
		while (true) { // loop infinito do gameloop
			handlerEvents();
			update();
			render();
			
			try {
				Thread.sleep(17); // pausa a thread
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
			
		}
	}
	public void handlerEvents() { // cuida das ações realizadas pelo jogador//
		nave.velX = 0;
		
		if(k_esquerda==true){// tecla esquerda pressionada
			nave.velX = -5; // movimento para esquerda
		}else if(k_direita==true) { // tecla direita pressionada
			nave.velX = 5; // movimento pra direita
		}
		if (k_space == true && shooting == false) {
			shooting = true;
			shot.posX = nave.posX+ (nave.largura/2); // posiciona a saida do tiro no centro da nave
			shot.posY = nave.posY;	// posiciona a saida do tiro na ponta da nave
			shot.active = true; // torna o tiro ativo
			try {
				shot.shot = ImageIO.read(getClass().getResource("imgs/shot.png"));
			} catch (Exception e) {
				e.printStackTrace();
			}
 		}
	}
	public void update() {
		if (bg1.posY == 1200) { // Reseta a posição do background 1 pra cima do bg2//
			bg1.posY = -1200;
		}
		if (bg2.posY == 1200){ // reseta a posição do background 2 pra cima do bg1//
			bg2.posY = -1200;			
		}
		bg1.posY += 8; // movimento do background 1//
		bg2.posY += 8;// movimento do background 2
		nave.posX += nave.velX; // atualiza o movimento do player //
		if(shooting == true) { // se estiver atirando a posição y do tiro é somada com a velocidade y do tiro //
			shot.posY+=shot.velY;
		}
		
		for (int i = 0; i < 6; i++) { // movimenta o array de aliens horizontalmente //
			for (int j = 0; j < 8; j++) {
				listaAlien[i][j].posX += listaAlien[i][j].velX; // soma posição x dos aliens com a velocidade dos aliens//
				}
			}
		testeColisao();
			
	
	}
	
	public void render() {
		repaint(); // detalhe técnico do JAVA
	}

	public void testeColisao() {
		if(nave.posX+(nave.largura) > Principal.LARGURA_TELA || nave.posX < 0) { // testa colisão horizontal//
			nave.posX -= nave.velX; // nega a velx impedindo que ande//
		}
		for (int i = 0; i < 6; i++) { // teste do colisão por matriz, setando novos atributos após colisao //
			for (int j = 0; j < 8; j++) {
				Aliens atual = listaAlien[i][j];
				if (atual.isVisible == false) { // se o inimigo ja estiver destruido nao precisa nem testar //
					continue;
				} 
				if (shot.posX <= atual.posX + atual.largura &&
					shot.posX >= atual.posX &&
					shot.posY <= atual.posY + atual.altura &&
					shot.posY >= atual.posY &&
					shot.active == true) {
					
					atual.isVisible = false; // seta inimigos atingidos pelo tiro não atingiveis //
					atual.inimigo = null; // seta imagem do inimigo nula //
					shot.active = false; // tiro deixa de estar ativo //
					shot.shot = null; // imagem do tiro setada para nulo //
					shooting = false; // possibilita atirar novamente //
					}
				}
			}
		//checagem de colisão do array de aliens com as laterais da tela e movimentação pra baixo assim que colidir. //
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 8; j++) {
				Aliens atual = listaAlien[i][j];
				if (atual.posX + atual.largura > Principal.LARGURA_TELA) {  // checa colisao com o lado direito				    
					atual.posY += atual.altura; // desce a altura de um alien//
					atual.velX *= -1.2; // aumenta em 1.2x a velocidade dos aliens quando colidirem com a parede //
				}
				if (atual.posX <= 0) { // checa colisao com lado esquerdo
					atual.posY += listaAlien[i][j].altura; // desce a altura de um alien //
					atual.velX *= -1.2; // aumenta em 1.2x a velocidade dos aliens quando colidirem com a parede //
				}
			}
			}
		if(shooting == true && shot.posY<0) { // se o tiro sair da tela, pode atirar novamente //
			shooting=false;
		}
	}

	@Override
	protected void paintComponent(Graphics g) { // método de renderização JAVA SE
		super.paintComponent(g);
		// desenha coisas na tela
		g.drawImage(bg1.bg, bg1.posX,bg1.posY , null);
		g.drawImage(bg2.bg, bg2.posX,bg2.posY , null);
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 8; j++) {
				g.drawImage(listaAlien[i][j].inimigo, listaAlien[i][j].posX, listaAlien[i][j].posY, null);
				}
			}
		g.drawImage(nave.ship, nave.posX, nave.posY, null);
		if(shooting==true) { // se estiver atirando desenha o tiro //
			g.drawImage(shot.shot, shot.posX, shot.posY, null);
		}
		
	}
}
