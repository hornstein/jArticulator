package jarticulator;

import java.awt.*;
import java.io.*;
import javax.swing.*;

//import sun.audio.*;

import javax.sound.sampled.*;

import java.awt.event.*;
import javax.swing.event.*;

import jarticulator.tubemodel.*;
import jarticulator.articulatorymodel.*;

public class JArticulator implements Runnable, ActionListener, ItemListener, KeyEventDispatcher, ChangeListener
{

	public Tube mTube;
	public VocalTract mVocalTract;
	public JFrame mFrame;
	public JMenu scriptMenu;
	public JToolBar mToolBar;
	public JTabbedPane mTabbedPane;
	public JTabbedPane mTabbedPane2;
	public JAView mArticulatoryView;
	public JAView mAreaView;
	public JSplitPane mSplitPane;
	public JPanel panel;
	
	protected static JArticulator mInstance = null;
	private static final int EXTERNAL_BUFFER_SIZE = 128000;
	
	public static JArticulator getInstance() 
	{
		if (mInstance == null) 
		{
			mInstance = new JArticulator();
		}
		return mInstance;
	}
	
	public JArticulator()
	{
		// create tube model
		mTube=new Tube(20,44100);		
		// create articulatory model
		mVocalTract=new VocalTract(mTube);
		
		SwingUtilities.invokeLater(this);

	}


	public void redraw()
	{
		mFrame.repaint();
	}


	public void updateScreenValues() 
	{


	}


	/**
	 *
	 * Creates and shows the GUI. This method should be
	 *
	 * invoked on the event-dispatching thread.
	 *
	 */

	public void run() 
	{
		createAndShowGUI();
	}

	
	/**
	 * Brings up a window that contains a ClickMe component.
	 * For thread safety, this method should be invoked from
	 * the event-dispatching thread.
	 */

	private void createAndShowGUI() 
	{

		// Make sure we have nice window decorations.
		JFrame.setDefaultLookAndFeelDecorated(false);

		// Create and set up the window.
		mFrame = new JFrame("JArticulator v0.1");
		mFrame.setMinimumSize(new Dimension(800,600));

		mFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		mFrame.addWindowListener(new java.awt.event.WindowAdapter() 
		{
			public void windowClosing(final WindowEvent e) 
			{
				System.exit(1);
			}
		});

		// Set up the layout manager.
		mFrame.getContentPane().setLayout(new BorderLayout());

		// Insert 16x16 Icon on JFrame
		try{
			ImageIcon icon = new ImageIcon(getClass().getResource("icons/BoardCAD png 16x16 upright.png"));
			mFrame.setIconImage(icon.getImage());
		}catch(Exception e) {
			System.out.println("Jframe Icon error:\n" + e.getMessage());
		}

		JMenuBar menuBar;
		JPopupMenu popupMenu;

		// Menu
		menuBar = new JMenuBar();
		popupMenu = new JPopupMenu();
		final JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		
		JPopupMenu.setDefaultLightWeightPopupEnabled(false);
		
		final AbstractAction newFile = new AbstractAction() {
			static final long serialVersionUID=1L;
			{
				this.putValue(Action.NAME, "Play sound");
			};


			public void actionPerformed(ActionEvent arg0) 
			{
				final JFileChooser fc = new JFileChooser();
				//fc.setCurrentDirectory(new File(BoardCAD.defaultDirectory));

				int returnVal = fc.showOpenDialog(JArticulator.getInstance().mFrame);
				if (returnVal != JFileChooser.APPROVE_OPTION)
					return;

				File file = fc.getSelectedFile();

				String filename = file.getPath(); // Load and display
				// selection
				if (filename == null)
					return;

				//BoardCAD.defaultDirectory = file.getPath();
				
				playSound(filename);
				
				
/*				try {
					InputStream in = new FileInputStream("c:/sound.wav");
					AudioStream as = new AudioStream(in);
					AudioPlayer.player.start(as); 
			
				} catch (IOException e) {
					e.printStackTrace();
				}   				
*/
/*				try {
					File soundFile = new File("c:/sound.wav");
					AudioInputStream sound = AudioSystem.getAudioInputStream(soundFile);
					
					// load the sound into memory (a Clip)
					DataLine.Info info = new DataLine.Info(Clip.class, sound.getFormat());
					Clip clip = (Clip) AudioSystem.getLine(info);
					clip.open(sound);
					clip.start();
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}   				
*/




			}

		};
		fileMenu.add(newFile);


		final AbstractAction exit = new AbstractAction() 
		{
			static final long serialVersionUID=1L;
			{
				this.putValue(Action.NAME, "Exit");
			};

			public void actionPerformed(ActionEvent arg0)
			{
				mFrame.dispose();
			}

		};
		fileMenu.add(exit);

		menuBar.add(fileMenu);



		scriptMenu = new JMenu("Script");
		scriptMenu.setMnemonic(KeyEvent.VK_P);
		menuBar.add(scriptMenu);

		final JMenu helpMenu = new JMenu("Help");
		helpMenu.setMnemonic(KeyEvent.VK_H);

		final AbstractAction onlineHelp = new AbstractAction() 
		{
			static final long serialVersionUID=1L;
			{
				this.putValue(Action.NAME, "Webpage");
				this.putValue(AbstractAction.SMALL_ICON, new ImageIcon(getClass().getResource("icons/Help16.gif")));
				// this.putValue(Action.ACCELERATOR_KEY,
				// KeyStroke.getKeyStroke(KeyEvent.VK_PLUS, 0));
			};

			public void actionPerformed(ActionEvent arg0) 
			{
				BrowserControl.displayURL("http://www.jarticulator.org");
			}

		};
		helpMenu.add(onlineHelp);

		final AbstractAction about = new AbstractAction() 
		{
			static final long serialVersionUID=1L;
			{
				this.putValue(Action.NAME, "About");
				this.putValue(AbstractAction.SMALL_ICON, new ImageIcon(getClass().getResource("icons/Information16.gif")));
				// this.putValue(Action.ACCELERATOR_KEY,
				// KeyStroke.getKeyStroke(KeyEvent.VK_PLUS, 0));
			};

			public void actionPerformed(ActionEvent arg0) 
			{
				AboutBox box = new AboutBox();
				box.setModal(true);
				box.setVisible(true);
				box.dispose();
			}

		};
		
		helpMenu.add(about);

		menuBar.add(helpMenu);

		mFrame.setJMenuBar(menuBar);

		mToolBar = new JToolBar();

		newFile.putValue(AbstractAction.SMALL_ICON, new ImageIcon(getClass().getResource("icons/new.png")));

		mToolBar.add(newFile);


		mTabbedPane = new JTabbedPane();
		

		mArticulatoryView = new JAView() 
		{
			static final long serialVersionUID=1L;

			static final double fixedHeightBorder = 0;
			{
				setPreferredSize(new Dimension(400, 100));
			};

			public void paintComponent(final Graphics g) 
			{
				super.paintComponent(g);
			}

			public void drawPart(final Graphics2D g, final Color color, final Stroke stroke) 
			{
				g.setColor(color);
				
				// Draw palate
				double[][] points=mVocalTract.getPalatePoints();
				for(int i=0; i<points.length-1; i++)
				{
					g.drawLine(600-(int)(points[i][0]*20), 400-(int)(points[i][1]*20), 600-(int)(points[i+1][0]*20), 400-(int)(points[i+1][1]*20));
					
				}
				
				
				// Draw tongue
				points=mVocalTract.getTonguePoints();
				for(int i=0; i<points.length-1; i++)
				{
					g.drawLine(600-(int)(points[i][0]*20), 400-(int)(points[i][1]*20), 600-(int)(points[i+1][0]*20), 400-(int)(points[i+1][1]*20));
					
				}
				
				
				g.setColor(Color.GREEN);
				double[][] p1=mVocalTract.getPalatePoints();
				double[][] p2=mVocalTract.getTonguePoints();
				for(int i=1; i<points.length-1; i++)
				{
					g.drawLine(600-(int)(p1[i][0]*20), 400-(int)(p1[i][1]*20), 600-(int)(p2[i][0]*20), 400-(int)(p2[i][1]*20));
					
				}
				
				
				
				//g.setColor(color);
				//g.fillRect(100, 100, 200, 200);

			}

		};


		mTabbedPane.add("Articulatory model", mArticulatoryView);

		
		mAreaView = new JAView() 
		{
			static final long serialVersionUID=1L;

			static final double fixedHeightBorder = 0;
			{
				setPreferredSize(new Dimension(400, 100));
			};

			public void paintComponent(final Graphics g) 
			{
				super.paintComponent(g);
			}

			public void drawPart(final Graphics2D g, final Color color, final Stroke stroke) 
			{
				g.setColor(color);
				
				// Draw palate
				double[] points=mVocalTract.getArea();
				for(int i=0; i<points.length; i++)
				{
					g.fillRect(150+i*15, 300-(int)(points[i]*15), 14, (int)(points[i]*15));
					
				}
				
				//g.setColor(color);
				//g.fillRect(100, 100, 200, 200);

			}

		};


		mTabbedPane.add("Area model", mAreaView);
		
		
		
		panel=new JPanel();
		
		//add controllers for each parameter
		
		JSlider jawpos = new JSlider(JSlider.VERTICAL, -10, 10, 0);
		jawpos.addChangeListener(this);		
		//Turn on labels at major tick marks.
		jawpos.setMajorTickSpacing(5);
		jawpos.setMinorTickSpacing(1);
		jawpos.setPaintTicks(true);
		jawpos.setPaintLabels(true);			
		panel.add(jawpos);
				
		JSlider tongue1pos = new JSlider(JSlider.VERTICAL, -10, 10, 0);
		tongue1pos.addChangeListener(new ChangeListener() 
		{
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider)e.getSource();
				mVocalTract.setTongue1Position(3.0*source.getValue()/10.0);
				mFrame.repaint();				
		        }
		});		
		//Turn on labels at major tick marks.
		tongue1pos.setMajorTickSpacing(5);
		tongue1pos.setMinorTickSpacing(1);
		tongue1pos.setPaintTicks(true);
		tongue1pos.setPaintLabels(true);			
		panel.add(tongue1pos);

		JSlider tongue2pos = new JSlider(JSlider.VERTICAL, -10, 10, 0);
		tongue2pos.addChangeListener(new ChangeListener() 
		{
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider)e.getSource();
				mVocalTract.setTongue2Position(3.0*source.getValue()/10.0);
				mFrame.repaint();				
		        }
		});		
		//Turn on labels at major tick marks.
		tongue2pos.setMajorTickSpacing(5);
		tongue2pos.setMinorTickSpacing(1);
		tongue2pos.setPaintTicks(true);
		tongue2pos.setPaintLabels(true);			
		panel.add(tongue2pos);
		
		JSlider tongue3pos = new JSlider(JSlider.VERTICAL, -10, 10, 0);
		tongue3pos.addChangeListener(new ChangeListener() 
		{
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider)e.getSource();
				mVocalTract.setTongue3Position(3.0*source.getValue()/10.0);
				mFrame.repaint();				
		        }
		});		
		//Turn on labels at major tick marks.
		tongue3pos.setMajorTickSpacing(5);
		tongue3pos.setMinorTickSpacing(1);
		tongue3pos.setPaintTicks(true);
		tongue3pos.setPaintLabels(true);			
		panel.add(tongue3pos);

		JSlider lip1pos = new JSlider(JSlider.VERTICAL, -10, 10, 0);
		lip1pos.addChangeListener(new ChangeListener() 
		{
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider)e.getSource();
				mVocalTract.setLip1Position(3.0*source.getValue()/10.0);
				mFrame.repaint();				
		        }
		});		
		//Turn on labels at major tick marks.
		lip1pos.setMajorTickSpacing(5);
		lip1pos.setMinorTickSpacing(1);
		lip1pos.setPaintTicks(true);
		lip1pos.setPaintLabels(true);			
		panel.add(lip1pos);
		
		JSlider lip2pos = new JSlider(JSlider.VERTICAL, -10, 10, 0);
		lip2pos.addChangeListener(new ChangeListener() 
		{
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider)e.getSource();
				mVocalTract.setLip2Position(3.0*source.getValue()/10.0);
				mFrame.repaint();				
		        }
		});		
		//Turn on labels at major tick marks.
		lip2pos.setMajorTickSpacing(5);
		lip2pos.setMinorTickSpacing(1);
		lip2pos.setPaintTicks(true);
		lip2pos.setPaintLabels(true);			
		panel.add(lip2pos);
		
		mTabbedPane2 = new JTabbedPane(JTabbedPane.BOTTOM);
		mTabbedPane2.addTab("Control parameters", panel);

//		mFrame.getContentPane().add(panel, BorderLayout.SOUTH);
//		mFrame.getContentPane().add(mTabbedPane2, BorderLayout.SOUTH);
		//mFrame.getContentPane().add(mTabbedPane, BorderLayout.CENTER);		
		mSplitPane=new JSplitPane(JSplitPane.VERTICAL_SPLIT, mTabbedPane, mTabbedPane2);
		mSplitPane.setResizeWeight(1.0);
		mSplitPane.setOneTouchExpandable(true);
		mFrame.getContentPane().add(mSplitPane, BorderLayout.CENTER);
		Dimension mindim=new Dimension(0,0);
		mTabbedPane.setMinimumSize(mindim);
		mTabbedPane2.setMinimumSize(mindim);
		mTabbedPane.setPreferredSize(new Dimension(600,230));
		mTabbedPane2.setPreferredSize(new Dimension(600,230));
		
		//load jython script
		String scriptname="jarticulator_init.py";
		File file=new File(scriptname);
    		if (file.exists()) 
    		{
			ScriptLoader.loadScript(scriptname);
		}		
				
		//KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this);


		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = 800;//screenSize.width * 9 / 10;
		int height = 800;//screenSize.height * 9 / 10;
		
		mFrame.setLocation((screenSize.width - width) / 2, (screenSize.height - height) / 2);

		mFrame.setSize(width, height);

		mFrame.setVisible(true);

		//edit.actionPerformed(null);

		
		//mBlockGUI = false;
	}

	public void actionPerformed(final ActionEvent e) 
	{
		mFrame.repaint();
	}

	public void itemStateChanged(final ItemEvent e) 
	{

		mFrame.repaint();

	}

	
	public void stateChanged(ChangeEvent e) 
	{
		JSlider source = (JSlider)e.getSource();
		//if (!source.getValueIsAdjusting()) 
		//{
		mVocalTract.setJawPosition(3.0*source.getValue()/10.0);
		mFrame.repaint();
		//}
		
		
	}		
	
	public boolean dispatchKeyEvent(final KeyEvent e) 
	{
		mFrame.repaint();
		return true;
	}


	public static void main(final String[] args) 
	{
		JArticulator.getInstance();
	}


	static public Frame findParentFrame(Container container) 
	{
		while (container != null) 
		{
			if (container instanceof Frame) 
			{
				return (Frame) container;
			}

			container = container.getParent();
		}
		return (Frame) null;
	}
	
	
	public void playSound(String filename)
	{
		
		File soundFile = new File(filename);
	
		/*
		  We have to read in the sound file.
		*/
		AudioInputStream audioInputStream = null;
		try
		{
			audioInputStream = AudioSystem.getAudioInputStream(soundFile);
		}
		catch (Exception e)
		{
			/*
			  In case of an exception, we dump the exception
			  including the stack trace to the console output.
			  Then, we exit the program.
			*/
			e.printStackTrace();
			System.exit(1);
		}

		/*
		  From the AudioInputStream, i.e. from the sound file,
		  we fetch information about the format of the
		  audio data.
		  These information include the sampling frequency,
		  the number of
		  channels and the size of the samples.
		  These information
		  are needed to ask Java Sound for a suitable output line
		  for this audio file.
		*/
		AudioFormat audioFormat = audioInputStream.getFormat();

		/*
		  Asking for a line is a rather tricky thing.
		  We have to construct an Info object that specifies
		  the desired properties for the line.
		  First, we have to say which kind of line we want. The
		  possibilities are: SourceDataLine (for playback), Clip
		  (for repeated playback)	and TargetDataLine (for
		  recording).
		  Here, we want to do normal playback, so we ask for
		  a SourceDataLine.
		  Then, we have to pass an AudioFormat object, so that
		  the Line knows which format the data passed to it
		  will have.
		  Furthermore, we can give Java Sound a hint about how
		  big the internal buffer for the line should be. This
		  isn't used here, signaling that we
		  don't care about the exact size. Java Sound will use
		  some default value for the buffer size.
		*/
		SourceDataLine	line = null;
		DataLine.Info	info = new DataLine.Info(SourceDataLine.class, audioFormat);
		try
		{
			line = (SourceDataLine) AudioSystem.getLine(info);

			/*
			  The line is there, but it is not yet ready to
			  receive audio data. We have to open the line.
			*/
			line.open(audioFormat);
		}
		catch (LineUnavailableException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}

		/*
		  Still not enough. The line now can receive data,
		  but will not pass them on to the audio output device
		  (which means to your sound card). This has to be
		  activated.
		*/
		line.start();

		/*
		  Ok, finally the line is prepared. Now comes the real
		  job: we have to write data to the line. We do this
		  in a loop. First, we read data from the
		  AudioInputStream to a buffer. Then, we write from
		  this buffer to the Line. This is done until the end
		  of the file is reached, which is detected by a
		  return value of -1 from the read method of the
		  AudioInputStream.
		*/
		int	nBytesRead = 0;
		byte[]	abData = new byte[EXTERNAL_BUFFER_SIZE];
		while (nBytesRead != -1)
		{
			try
			{
				nBytesRead = audioInputStream.read(abData, 0, abData.length);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			if (nBytesRead >= 0)
			{
				int	nBytesWritten = line.write(abData, 0, nBytesRead);
			}
		}

		/*
		  Wait until all data are played.
		  This is only necessary because of the bug noted below.
		  (If we do not wait, we would interrupt the playback by
		  prematurely closing the line and exiting the VM.)
		 
		  Thanks to Margie Fitch for bringing me on the right
		  path to this solution.
		*/
		line.drain();

		/*
		  All data are played. We can close the shop.
		*/
		line.close();

		
		
	}
}

class BrowserControl 
{
	/**
	 * Display a file in the system browser. If you want to display a file, you
	 * must include the absolute path name.
	 *
	 * @param url
	 *            the file's url (the url must start with either "http://" or
	 *            "file://").
	 */
	// Used to identify the windows platform.
	private static final String WIN_ID = "Windows";

	// The default system browser under windows.
	private static final String WIN_PATH = "rundll32";

	// The flag to display a url.
	private static final String WIN_FLAG = "url.dll,FileProtocolHandler";

	// The default browser under unix.
	private static final String UNIX_PATH = "netscape";

	// The flag to display a url.
	private static final String UNIX_FLAG = "-remote openURL";

	public static void displayURL(final String url) 
	{
		final boolean windows = isWindowsPlatform();
		String cmd = null;
		try {
			if (windows) {
				// cmd = 'rundll32 url.dll,FileProtocolHandler http://...'
				cmd = WIN_PATH + " " + WIN_FLAG + " " + url;
				final Process p = Runtime.getRuntime().exec(cmd);
			} else {
				// Under Unix, Netscape has to be running for the "-remote"
				// command to work. So, we try sending the command and
				// check for an exit value. If the exit command is 0,
				// it worked, otherwise we need to start the browser.
				// cmd = 'netscape -remote openURL(http://www.javaworld.com)'
				cmd = UNIX_PATH + " " + UNIX_FLAG + "(" + url + ")";
				Process p = Runtime.getRuntime().exec(cmd);
				try {
					// wait for exit code -- if it's 0, command worked,
					// otherwise we need to start the browser up.
					final int exitCode = p.waitFor();
					if (exitCode != 0) {
						// Command failed, start up the browser
						// cmd = 'netscape http://www.javaworld.com'
						cmd = UNIX_PATH + " " + url;
						p = Runtime.getRuntime().exec(cmd);
					}
				} catch (final InterruptedException x) {
					System.err.println("Error bringing up browser, cmd='" + cmd
							+ "'");
					System.err.println("Caught: " + x);
				}
			}
		} catch (final IOException x) {
			// couldn't exec browser
			System.err.println("Could not invoke browser, command=" + cmd);
			System.err.println("Caught: " + x);
		}
	}

	/**
	 * Try to determine whether this application is running under Windows or
	 * some other platform by examing the "os.name" property.
	 *
	 * @return true if this application is running under a Windows OS
	 */
	public static boolean isWindowsPlatform() 
	{
		final String os = System.getProperty("os.name");
		if (os != null && os.startsWith(WIN_ID))
			return true;
		else
			return false;
	}
}


