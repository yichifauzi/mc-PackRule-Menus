package tk.estecka.packrulemenus.config;

import java.util.HashMap;
import java.util.Map;
import tk.estecka.packrulemenus.PackRuleMod;
import tk.estecka.packrulemenus.config.ConfigIO.Property;

public class ConfigLoader
extends ConfigIO.AFixedCoded
{
	@Override
	public Map<String, Property<?>> GetProperties(){
		return new HashMap<>(){{
			put("button.location", CreateProperty());
		}};
	}

	static public Property<EButtonLocation> CreateProperty(){
		return new Property<EButtonLocation>(
			() -> PackRuleMod.BUTTON_LOCATION,
			e -> PackRuleMod.BUTTON_LOCATION = e,
			EButtonLocation::parse,
			EButtonLocation::toString
		);
	}
}
