package com.mycompany.custom;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Component;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;

public class CoolCheckBox extends CheckBox {
	
	/**
	 * An updated, cooler looking checkbox
	 * @param s
	 */
	
	public CoolCheckBox(String s) {
		super(s);
		int fontSize = Display.getInstance().convertToPixels(4);
		this.getAllStyles().setBgTransparency(255);
		this.getAllStyles().setFont(Font.createTrueTypeFont("ChunkFive", "Chunk.ttf").derive(fontSize, Font.STYLE_PLAIN));
		this.getAllStyles().setPadding(2, 4);
		this.getAllStyles().setBorder(Border.createDoubleBorder(2, ColorUtil.BLACK));
		this.getAllStyles().setAlignment(Component.CENTER);
		this.getAllStyles().setBackgroundType(Style.BACKGROUND_GRADIENT_LINEAR_HORIZONTAL);
		//this.getAllStyles().setBackgroundGradientStartColor(ColorUtil.red(255));  //In case I wanna switch the colors
		//this.getAllStyles().setBackgroundGradientEndColor(ColorUtil.BLUE);
		this.getAllStyles().setBackgroundGradientStartColor(ColorUtil.GRAY);
		this.getAllStyles().setBackgroundGradientEndColor(ColorUtil.WHITE);
		this.getAllStyles().setFgColor(ColorUtil.BLACK);
	}
}
