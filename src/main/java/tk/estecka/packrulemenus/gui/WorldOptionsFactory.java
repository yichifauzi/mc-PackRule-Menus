package tk.estecka.packrulemenus.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.text.Text;
import tk.estecka.packrulemenus.DatapackHandler;
import tk.estecka.packrulemenus.GameruleHandler;


public class WorldOptionsFactory
{
	static public Screen WorldOptionsScreen(Screen parent, IntegratedServer server){
		final MinecraftClient client = MinecraftClient.getInstance();

		GenericOptionScreen screen = new GenericOptionScreen(Text.translatable("packrulemenus.gui.worldoptions.title"), parent);
		screen.AddWidget( CreateDificultyButton(client, server) );
		screen.AddWidget( new GameruleHandler(screen, server).CreateButton() );
		screen.AddWidget( new DatapackHandler(screen, server).CreateButton() );

		return screen;
	}

	static private Widget CreateDificultyButton(MinecraftClient client, IntegratedServer server) {
		return ButtonWidget.builder(Text.literal("Soon in your area"), b->{}).build();
	}
}
