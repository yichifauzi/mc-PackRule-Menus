package tk.estecka.packrulemenus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.integrated.IntegratedServer;


public class PackRuleMenus
{
	static public final Logger LOGGER = LoggerFactory.getLogger("packrule-menus");

	static public boolean CanModifyWorld(){
		final MinecraftClient client = MinecraftClient.getInstance();
		final IntegratedServer server = client.getServer();

		return client.isIntegratedServerRunning()
		    && server.getSaveProperties().areCommandsAllowed()
		    && server.getOverworld() != null
		    ;
	}
}
