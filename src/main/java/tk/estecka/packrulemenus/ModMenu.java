package tk.estecka.packrulemenus;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import tk.estecka.packrulemenus.gui.GenericOptionScreen;

public class ModMenu
implements ModMenuApi
{
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory(){
		return (parent) -> new GenericOptionScreen(
			Text.translatable("packrulemenus.gui.main.title"),
			parent,
			new ButtonWidget[]{
				ButtonWidget.builder(Text.literal("Button 1"), (b)->{}).build(),
				ButtonWidget.builder(Text.literal("Button 2"), (b->{})).build(),
			}
		);
	}
}
