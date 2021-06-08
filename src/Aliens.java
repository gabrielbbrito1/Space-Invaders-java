import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Aliens {
	public int posX;
	public int posY;
	public int velX;
	public int velY;
	public int altura;
	public int largura;
	public BufferedImage inimigo;
	public boolean isVisible;
	
	public Aliens() {
		largura = 40;
		altura = 40;
		posX = 50;
		posY = 10;
		velX = 4;
		isVisible = true;
		try {
			inimigo = ImageIO.read(getClass().getResource("imgs/alien.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
