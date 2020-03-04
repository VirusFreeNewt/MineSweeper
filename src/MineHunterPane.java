import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author timberlinepluska
 */
public class MineHunterPane extends JPanel
{
    private MineHunterGrid lbg = new MineHunterGrid(25, 10);
    private MineHunterControls lbc = new MineHunterControls(new colorListener());
    
    public MineHunterPane()
    {
            setLayout(new BorderLayout());
    	    setPreferredSize(new Dimension(800, 800));
            add(lbg,BorderLayout.CENTER);
            add(lbc, BorderLayout.WEST);
    }

    private class colorListener implements ActionListener
    {
		@Override
		public void actionPerformed(ActionEvent e)
		{
            lbg.doButtonAction(lbc.getButtonAction((JButton) e.getSource()));
		}
    }
}
