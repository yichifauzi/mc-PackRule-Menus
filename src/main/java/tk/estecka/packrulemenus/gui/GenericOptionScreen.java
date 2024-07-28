package tk.estecka.packrulemenus.gui;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.DirectionalLayoutWidget;
import net.minecraft.client.gui.widget.ThreePartsLayoutWidget;
import net.minecraft.text.Text;

public class GenericOptionScreen
extends Screen
{
	private final Screen parent;

	private final ThreePartsLayoutWidget layout = new ThreePartsLayoutWidget(this);
	private final DirectionalLayoutWidget body = DirectionalLayoutWidget.vertical().spacing(8);

	public GenericOptionScreen(Text title, Screen parent, ButtonWidget[] buttons) {
		super(title);
		this.parent = parent;

		for (ButtonWidget b : buttons)
			body.add(b);
	}

	@Override
	public void init(){
		layout.addHeader(this.title, this.textRenderer);
		layout.setFooterHeight(0);
		layout.addBody(body);

		layout.forEachChild(e -> this.addDrawableChild(e));
		layout.refreshPositions();
	}

	@Override
	public void close(){
		this.client.setScreen(parent);
	}
}
