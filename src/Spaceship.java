import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Spaceship {
	public int posX;
	public int posY;
	public int velX;
	public int altura;
	public int largura;
	public BufferedImage ship;
	
	public Spaceship() {
		largura = 60;
		altura = 60;
		posX = 340;
		posY = 520;
		velX = 0;
		try {
			ship = ImageIO.read(getClass().getResource("imgs/ship.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
