package tk.estecka.packrulemenus;

import java.io.IOException;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.text.Text;
import tk.estecka.packrulemenus.config.ConfigLoader;
import tk.estecka.packrulemenus.config.EButtonLocation;
import tk.estecka.packrulemenus.gui.GenericOptionScreen;


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
		ButtonWidget packs, rules;
		if (showWorldOptions){
			packs = new DatapackHandler(screen, server).CreateButton();
			rules = new GameruleHandler(screen, server).CreateButton();
		}
		else {
			packs = ButtonWidget.builder( Text.translatable("selectWorld.dataPacks"), __->{} ).build();
			rules = ButtonWidget.builder( Text.translatable("selectWorld.gameRules"), __->{} ).build();
			packs.active = false;
			rules.active = false;
		}

		screen.AddWidget(rules);
		screen.AddWidget(packs);
		screen.AddWidget(CreateConfigButton());

		return screen;
	}

	static private CyclingButtonWidget<EButtonLocation> CreateConfigButton(){
		return CyclingButtonWidget.builder(EButtonLocation::TranslatableName)
			.values(EButtonLocation.values())
			.initially(PackRuleMenus.BUTTON_LOCATION)
			.tooltip(ModMenu::GetConfigTooltip)
			.build(Text.translatable("packrule-menus.config.buttonlocation"), ModMenu::OnButtonChanged)
			;
	}

	static private Tooltip GetConfigTooltip(EButtonLocation e){
		return Tooltip.of(Text.translatable(e.TranslationKey() + ".tooltip"));
	}

	static private void OnButtonChanged(CyclingButtonWidget<?> button, EButtonLocation value){
		PackRuleMenus.BUTTON_LOCATION = value;
		try {
			PackRuleMenus.CONFIG_IO.Write(new ConfigLoader());
		}
		catch (IOException e){
			PackRuleMenus.LOGGER.error(e.getMessage());
		}
	}
}
