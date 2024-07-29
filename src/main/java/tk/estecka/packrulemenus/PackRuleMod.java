package tk.estecka.packrulemenus;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.integrated.IntegratedServer;
import tk.estecka.packrulemenus.config.ConfigIO;
import tk.estecka.packrulemenus.config.ConfigLoader;
import tk.estecka.packrulemenus.config.EButtonLocation;


public class PackRuleMod
{
	static public final String MODID = "packrule-menus";
	static public final Logger LOGGER = LoggerFactory.getLogger(MODID);

	static public final ConfigIO CONFIG_IO = new ConfigIO(MODID+".properties");
	static public EButtonLocation BUTTON_LOCATION = EButtonLocation.OPTIONS_HEADER;

	static
	{
		try {
			CONFIG_IO.GetIfExists(new ConfigLoader());
		}
		catch (IOException e){
			LOGGER.error(e.getMessage());
		}
	}

	static public boolean CanModifyWorld(){
		final MinecraftClient client = MinecraftClient.getInstance();
		final IntegratedServer server = client.getServer();

		return client.isIntegratedServerRunning()
		    && server.getSaveProperties().areCommandsAllowed()
		    && server.getOverworld() != null
		    ;
	}
}
