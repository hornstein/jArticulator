import jarticulator.JArticulator

from javax.swing import JScrollPane
from java.awt import *

from javax.swing import *
from java.io import *
import java.awt.event.KeyEvent as KeyEvent

import console

def runScript(event):
	fc = JFileChooser()
	fc.setCurrentDirectory(File(jarticulator.JArticulator.getInstance().defaultDirectory))
	returnVal = fc.showOpenDialog(jarticulator.JArticulator.getInstance().getFrame())
	if (returnVal != JFileChooser.APPROVE_OPTION):
		return
	mfile = fc.getSelectedFile()
	filename = mfile.getPath()
	myconsole.insertText("execfile('" + filename + "')")
	myconsole.enter()
	#execfile(filename)


frame=jarticulator.JArticulator.getInstance().mFrame
mytab=jarticulator.JArticulator.getInstance().mTabbedPane2
myconsole = console.Console()
mytab.addTab("Jython console", JScrollPane(myconsole.text_pane))

mbar=frame.getJMenuBar()

jythonmenu=jarticulator.JArticulator.getInstance().scriptMenu
#jythonmenu=JMenu('Scripts')
#jythonmenu.setMnemonic(KeyEvent.VK_I)

mitem=JMenuItem('Run script', actionPerformed=runScript)
jythonmenu.add(mitem)

mbar.revalidate()
#mbar.add(jythonmenu)
#frame.setJMenuBar(mbar)



 




