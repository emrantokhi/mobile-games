package com.mycompany.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.util.UITimer;
import com.mycompany.commands.*;
import com.mycompany.custom.*;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;

public class Game extends Form implements Runnable{

	private GameWorld gw;                                       //All variables are private fields
	private ScoreView score;
	private MapView map;
	private UITimer timer;
	private boolean paused;
	private CoolButton pause;
	private CoolButton accelerate;              
	private CoolButton left;
	private CoolButton strategy;
	private CoolButton position;
	private CoolButton brake;
	private CoolButton right;
	private Toolbar toolbar;
	private CoolCheckBox sound;
	private CoolButton about;
	private CoolButton exit;
	private static int width;
	private static int height;
	private static int milliSecs;
	
	/**
	 * Constructor for the Game class, it creates a new GameWorld
	 * Sets the flag to false for now, prints out the title on console
	 * initializes the gameobjects in gameworld, and finally accepts
	 * commands through the play method.
	 */
	
	public Game() {
		milliSecs = 20;
		paused = false;
		this.gw = new GameWorld();                                   //Instantiate a new gameworld
		this.setLayout(new BorderLayout());
		this.score = new ScoreView();
		this.map = new MapView(gw, this); //Trying to see if this will allow the static values for width and height in mapview
		gw.addObserver(score);
		gw.addObserver(map);      // Since calling mapview after building the gui
		buildMapView();
		buildGUI();
		width = map.getWidth();
		height = map.getHeight();
		gw.init();                                              //Initialize gameworld with gameobjects
		timer = new UITimer(this);
		startTimer();
	}
	
	public static int getMapWidth() {
		return width;
	}
	
	public static int getMapHeight() {
		return height;
	}
	
	public static int getMilliSecs() {
		return milliSecs;
	}
	
	public boolean isStopped() {
		return paused;
	}
	
	public void setPaused(boolean paused) {
		this.paused = paused;
	}
	
	/**
	 * For PlayCmd to use to start the game again
	 */
	public void startTimer() {
		pause.setText("Pause");
		pause.setCommand(new PauseCmd(this));
		gw.playBGMusic(gw.getSound());
		timer.schedule(milliSecs, true, this);
	}
	
	/**
	 * For PauseCmd to use to stop the game from running
	 */
	public void stopTimer() {
		gw.playBGMusic(false);
		timer.cancel();
		pause.setText("Play");
		pause.setCommand(new PlayCmd(this, map));
	}
	
	public void run() {
		gw.clockTick();
	}
	
	/**
	 * Just to either enable or disable commands if the game is in pause/play mode
	 */
	public void commandSet() {
		if(paused) {
			position.setEnabled(true);
			sound.setEnabled(false);
			accelerate.setEnabled(false);
			left.setEnabled(false);
			right.setEnabled(false);
			brake.setEnabled(false);
			strategy.setEnabled(false);
			removeKeyboardCmds();
			repaint();
		}else {
			position.setEnabled(false);
			sound.setEnabled(true);
			accelerate.setEnabled(true);
			left.setEnabled(true);
			right.setEnabled(true);
			brake.setEnabled(true);
			strategy.setEnabled(true);
			setKeyboardCmds();
			repaint();
		}
	}
	
	private void buildGUI() {
		buildNorth();     //ScoreView
		buildSouth();
		buildWest();
		buildSouth();
		buildEast();
		buildToolbar();
		commandSet();
		this.show();
	}
	
	
	private void buildWest() {
		Container westContainer = new Container(BoxLayout.yCenter());         //Container style
		westContainer.getAllStyles().setBorder(Border.createLineBorder(2));
		westContainer.getAllStyles().setBgTransparency(255);
		westContainer.getAllStyles().setBgColor(ColorUtil.rgb(189, 232, 238));
		accelerate = new CoolButton("Accelerate");                //Buttons
		left = new CoolButton("Left");
		strategy = new CoolButton("Change Strategy");
		accelerate.setCommand(new AccelerateCmd(gw));                        //Setting Commands on buttons
		left.setCommand(new LeftCmd(gw));                                              
		strategy.setCommand(new StrategyCmd(gw));
		westContainer.add(accelerate);                                     //Adding the commands to container
		westContainer.add(new Label(" "));
		westContainer.add(left);
		westContainer.add(new Label(" "));
		westContainer.add(strategy);
		this.add(BorderLayout.WEST, westContainer);                     //Adding container to the form
	}
	
	private void buildSouth() {
		Container southContainer = new Container(BoxLayout.xCenter());
		southContainer.getAllStyles().setBorder(Border.createLineBorder(2));
		southContainer.getAllStyles().setBgTransparency(255);
		southContainer.getAllStyles().setBgColor(ColorUtil.rgb(189, 232, 238));
		position = new CoolButton("Position");
		pause = new CoolButton("Pause");
		pause.getAllStyles().setAlignment(4);
		position.setCommand(new PositionCmd());
		pause.setCommand(new PauseCmd(this));
		southContainer.add(position);
		southContainer.add(pause);
		this.add(BorderLayout.SOUTH, southContainer);
	}
	
	private void buildEast() {
		Container eastContainer = new Container(BoxLayout.yCenter());
		eastContainer.getAllStyles().setBorder(Border.createLineBorder(2));
		eastContainer.getAllStyles().setBgTransparency(255);
		eastContainer.getAllStyles().setBgColor(ColorUtil.rgb(189, 232, 238));
		brake = new CoolButton("Brake");;
		right = new CoolButton("Right");
		brake.setCommand(new BrakeCmd(gw));
		right.setCommand(new RightCmd(gw));
		eastContainer.add(right);
		eastContainer.add(new Label(" "));
		eastContainer.add(brake);
		this.add(BorderLayout.EAST, eastContainer);
	}
	
	private void buildNorth() {
		this.add(BorderLayout.NORTH, score);
	}
	
	private void buildMapView() {
		this.add(BorderLayout.CENTER, map);
	}
	
	private void buildToolbar() {
		toolbar = new Toolbar();
		setToolbar(toolbar);
		sound = new CoolCheckBox("Sound");
		sound.setSelected(true);
		sound.setCommand(new SoundCmd(gw));
		toolbar.addComponentToLeftSideMenu(sound);
		about = new CoolButton("About");
		about.setCommand(new AboutCmd(gw));
		toolbar.addComponentToLeftSideMenu(about);
		exit = new CoolButton("Exit");
		exit.setCommand(new ExitCmd());
		toolbar.addComponentToLeftSideMenu(exit);
		toolbar.addCommandToRightBar(new HelpCmd());
		//Title of toolbar
		int sizePixels = Display.getInstance().convertToPixels(7);
		Font style = Font.createTrueTypeFont("Anasthesia", "Anasthesia.ttf"
				).derive(sizePixels, Font.STYLE_PLAIN);
		Label title = new Label("Welcome to: Sili-Challenge!");
		title.getAllStyles().setFont(style);
		toolbar.setTitleComponent(title);
	}
	
	private void setKeyboardCmds() {
		addKeyListener('w', new AccelerateCmd(gw));
		addKeyListener('s', new BrakeCmd(gw));
		addKeyListener('a', new LeftCmd(gw));
		addKeyListener('d', new RightCmd(gw));
		addKeyListener('f', new StrategyCmd(gw));	
	}
	
	private void removeKeyboardCmds() {
		removeKeyListener('w', new AccelerateCmd(gw));
		removeKeyListener('s', new BrakeCmd(gw));
		removeKeyListener('a', new LeftCmd(gw));
		removeKeyListener('d', new RightCmd(gw));
		removeKeyListener('f', new StrategyCmd(gw));
	}
}
