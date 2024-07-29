package fr.estecka.packrulemenus.gui;

import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.WarningScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.DirectionalLayoutWidget;
import net.minecraft.client.gui.widget.LayoutWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

public class GenericWarningScreen
extends WarningScreen
{
	private ButtonWidget proceedButton, cancelButton;
	private final BooleanConsumer onConfirm;

	public GenericWarningScreen(Text header, Text message, Text checkMessage, BooleanConsumer onConfirm){
		super(header, message, checkMessage, message);
		this.onConfirm = onConfirm;
	}

	@Override
	protected LayoutWidget getLayout(){
		DirectionalLayoutWidget layout = DirectionalLayoutWidget.horizontal().spacing(8);
		this.proceedButton = ButtonWidget.builder(ScreenTexts.PROCEED, this::OnAccept).build();
		this.cancelButton  = ButtonWidget.builder(ScreenTexts.CANCEL,  this::OnCancel).build();
		layout.add(proceedButton);
		layout.add(cancelButton);
		return layout;
	}

	@Override
	public void	render(DrawContext context, int mouseX, int mouseY, float delta){
		this.proceedButton.active = this.checkbox.isChecked();
		super.render(context, mouseX, mouseY, delta);
	}

	private void	OnAccept(ButtonWidget __){
		if (checkbox.isChecked())
			this.onConfirm.accept(true);
	}

	private void	OnCancel(ButtonWidget __){
		this.onConfirm.accept(false);
	}

	@Override
	public void	close(){
		super.close();
		this.onConfirm.accept(false);
	}
}
