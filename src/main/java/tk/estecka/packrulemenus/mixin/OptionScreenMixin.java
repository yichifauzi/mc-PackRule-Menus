package tk.estecka.packrulemenus.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.server.integrated.IntegratedServer;
import tk.estecka.packrulemenus.DatapackHandler;
import tk.estecka.packrulemenus.GameruleHandler;
import tk.estecka.packrulemenus.PackRuleMenus;


@Unique
@Mixin(OptionsScreen.class)
public class OptionScreenMixin 
extends Screen
{
	private OptionScreenMixin(){ super(null); }
	
	@Inject( method="init", at=@At(value="INVOKE", ordinal=0, target="net/minecraft/client/gui/widget/GridWidget$Adder.add (Lnet/minecraft/client/gui/widget/Widget;)Lnet/minecraft/client/gui/widget/Widget;") )
	private void gameruleMenu$Init(CallbackInfo info, @Local GridWidget.Adder adder){
		if (PackRuleMenus.CanModifyWorld())
		{
			final IntegratedServer server = this.client.getServer();
			adder.add(new GameruleHandler(this, server).CreateButton());
			adder.add(new DatapackHandler(this, server).CreateButton());
		}
	}

}
