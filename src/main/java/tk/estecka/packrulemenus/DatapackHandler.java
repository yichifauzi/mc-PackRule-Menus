package tk.estecka.packrulemenus;

import java.util.Collection;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.MessageScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.pack.PackScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.resource.DataConfiguration;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.WorldSavePath;
import tk.estecka.packrulemenus.gui.GenericWarningScreen;
import tk.estecka.packrulemenus.mixin.IMinecraftServerMixin;

public class DatapackHandler
{
	private final Screen parent;
	private final IntegratedServer server;
	private final MinecraftClient client = MinecraftClient.getInstance();

	public DatapackHandler(Screen parent, IntegratedServer server){
		this.parent = parent;
		this.server = server;
	}

	private void	RevertScreen(){
		client.setScreen(parent);
	};

	public ButtonWidget CreateButton(){
		return ButtonWidget.builder(
				Text.translatable("selectWorld.dataPacks"),
				__->client.setScreen( CreateScreen() )
			).build();
	}

	public PackScreen CreateScreen(){
		Collection<String> rollback = server.getDataPackManager().getEnabledIds();

		return new PackScreen(
			server.getDataPackManager(),
			manager -> { HandleDatapackRefresh(manager, rollback); },
			server.getSavePath(WorldSavePath.DATAPACKS),
			Text.translatable("dataPack.title")
		);
	}

	private void	HandleDatapackRefresh(final ResourcePackManager manager, Collection<String> rollback){
		FeatureSet neoFeatures = manager.getRequestedFeatures();
		FeatureSet oldFeatures = server.getSaveProperties().getEnabledFeatures();

		if (neoFeatures.equals(oldFeatures)) {
			ReloadPacks(manager);
			RevertScreen();
		}
		else {
			boolean isExperimental = FeatureFlags.isNotVanilla(neoFeatures);
			boolean wasVanillaRemoved = oldFeatures.contains(FeatureFlags.VANILLA) && !neoFeatures.contains(FeatureFlags.VANILLA);
			BooleanConsumer onConfirm = confirmed -> {
				if (confirmed){
					this.ApplyFlags(manager);
					this.server.stop(false);
					if (this.client.world != null)
						this.client.world.disconnect();
					this.client.disconnect(new MessageScreen(Text.translatable("menu.savingLevel")));
					this.client.setScreen(new TitleScreen());
				} else {
					manager.setEnabledProfiles(rollback);
					RevertScreen();
				}
			};

			client.setScreen(FeatureWarning(isExperimental, confirmed -> {
				if (!wasVanillaRemoved || !confirmed)
					onConfirm.accept(confirmed);
				else
					client.setScreen(VanillaWarning(onConfirm));
			}));
		}
	}

	private void	ApplyFlags(final ResourcePackManager manager){
		FeatureSet features = manager.getRequestedFeatures();

		String featureNames = "";
		for (Identifier id : FeatureFlags.FEATURE_MANAGER.toId(features))
			featureNames += id.toString()+", ";
		PackRuleMod.LOGGER.info("Reloading packs with features: {}", featureNames);

		server.getSaveProperties().updateLevelInfo(new DataConfiguration(IMinecraftServerMixin.callCreateDataPackSettings(manager, true), features));		
	}


	private void	ReloadPacks(final ResourcePackManager manager){
		client.inGameHud.getChatHud().addMessage(Text.translatable("commands.reload.success"));

		server.reloadResources(manager.getEnabledIds()).exceptionally(e -> {
			PackRuleMod.LOGGER.error("{}", e);
			client.inGameHud.getChatHud().addMessage(Text.translatable("commands.reload.failure").formatted(Formatting.RED));
			return null;
		});
	}

	static public GenericWarningScreen	FeatureWarning(boolean isExperimental, BooleanConsumer onConfirm){
		MutableText msg = Text.translatable("packrulemenus.featureflag.warning.message");
		if (isExperimental)
			msg.append(Text.translatable("selectWorld.experimental.message"));

		return new GenericWarningScreen(
			Text.translatable("packrulemenus.featureflag.warning.title"),
			msg,
			Text.translatable("packrulemenus.featureflag.warning.checkbox"),
			onConfirm
		);
	}

	static public GenericWarningScreen	VanillaWarning(BooleanConsumer onConfirm){
		return new GenericWarningScreen(
			Text.translatable("packrulemenus.vanillapack.warning.title"),
			Text.translatable("packrulemenus.vanillapack.warning.message"),
			Text.translatable("packrulemenus.vanillapack.warning.checkbox"),
			onConfirm
		);
	}
}
