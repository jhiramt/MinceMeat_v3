import javax.swing.*;
import java.awt.event.*;

public class CloseableFrame extends JFrame
{
	public CloseableFrame() {
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
}
