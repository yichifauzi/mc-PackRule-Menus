package tk.estecka.packrulemenus.config;

import net.minecraft.text.Text;

public enum EButtonLocation
{
	NONE("none"),
	OPTIONS("options"),
	;

	private final String name;

	private EButtonLocation(String name){
		this.name = name;
	}

	static public EButtonLocation parse(String name)
	throws IllegalArgumentException
	{
		for (EButtonLocation e : values())
			if (e.toString().equals(name))
				return e;

		throw new IllegalArgumentException("Invalid enum value:" + name);
	}

	@Override
	public String toString(){
		return this.name;
	}

	public Text TranslatableName() {
		return Text.translatable("packrule-menus.config.buttonlocation." + this.name);
	}

}
