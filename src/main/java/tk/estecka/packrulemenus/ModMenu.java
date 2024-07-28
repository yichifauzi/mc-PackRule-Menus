package tk.estecka.packrulemenus;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ButtonWidget.PressAction;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.text.Text;
import tk.estecka.packrulemenus.gui.GenericOptionScreen;
import tk.estecka.packrulemenus.gui.WorldOptionsFactory;

public class ModMenu
implements ModMenuApi
{
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory(){
		return ModMenu::ModMenuScreen;
	}

	static public Screen ModMenuScreen(Screen parent){
		final MinecraftClient client = MinecraftClient.getInstance();
		final IntegratedServer server = client.getServer();
		GenericOptionScreen screen = new GenericOptionScreen(Text.translatable("packrulemenus.gui.main.title"), parent);
		boolean showWorldOptions = PackRuleMenus.CanModifyWorld();

		ButtonWidget world;
		PressAction worldAction = showWorldOptions ? b->client.setScreen(WorldOptionsFactory.WorldOptionsScreen(screen, server)) : b->{};
		world = ButtonWidget.builder(Text.translatable("packrulemenus.gui.worldoptions.title"), worldAction).build();
		world.active = showWorldOptions;

		screen.AddWidget(world);
		screen.AddWidget( ButtonWidget.builder(Text.literal("Button 2"), (b->{})).build() );

		return screen;
	}
}
