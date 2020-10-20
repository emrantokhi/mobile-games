package com.mycompany.custom;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.charts.util.ColorUtil;

public class CoolButton extends Button{
	
	public CoolButton(String title) {
		super(title);
		int fontSize = Display.getInstance().convertToPixels(4);  //For font size
		this.getAllStyles().setBgTransparency(255);  //Make sure color comes
		Font newStyle = Font.createTrueTypeFont("ChunkFive", "Chunk.ttf"
				).derive(fontSize, Font.STYLE_PLAIN);    //THe new font using the TTF file
		this.getAllStyles().setFont(newStyle);
		this.getAllStyles().setPadding(2, 4);
		this.getAllStyles().setBorder(Border.createDoubleBorder(2, ColorUtil.BLACK));
		this.getAllStyles().setAlignment(Component.CENTER);
		this.getUnselectedStyle().setBackgroundType(Style.BACKGROUND_GRADIENT_LINEAR_HORIZONTAL);
		//this.getUnselectedStyle().setBackgroundGradientStartColor(ColorUtil.red(255));  //In case I wanna switch to another button color
		//this.getUnselectedStyle().setBackgroundGradientEndColor(ColorUtil.BLUE);
		this.getAllStyles().setBackgroundGradientStartColor(ColorUtil.GRAY);
		this.getAllStyles().setBackgroundGradientEndColor(ColorUtil.WHITE);
		this.getUnselectedStyle().setFgColor(ColorUtil.BLACK);
		this.getSelectedStyle().setOpacity(127);
		
	}

}
