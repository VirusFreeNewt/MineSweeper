import javax.swing.JFrame;

/**
 *
 * @author timberlinepluska
 */
public class MineHunter
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        JFrame frame = new JFrame("Mine Hunter");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new MineHunterPane());
		frame.pack();
		frame.setVisible(true);
    }
}
