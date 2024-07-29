package tk.estecka.packrulemenus.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.widget.DirectionalLayoutWidget;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.gui.widget.ThreePartsLayoutWidget;
import net.minecraft.server.integrated.IntegratedServer;
import tk.estecka.packrulemenus.PackRuleMenus;
import tk.estecka.packrulemenus.DatapackHandler;
import tk.estecka.packrulemenus.GameruleHandler;
import tk.estecka.packrulemenus.config.EButtonLocation;


@Unique
@Mixin(OptionsScreen.class)
public class OptionScreenMixin
extends Screen
{
	@Shadow private @Final ThreePartsLayoutWidget layout;

	private OptionScreenMixin(){ super(null); }
	
	@Inject( method="init", at=@At(value="INVOKE", ordinal=0, target="net/minecraft/client/gui/widget/GridWidget$Adder.add (Lnet/minecraft/client/gui/widget/Widget;)Lnet/minecraft/client/gui/widget/Widget;") )
	private void gameruleMenu$Init(CallbackInfo info, @Local GridWidget.Adder adder, @Local(ordinal=0) DirectionalLayoutWidget header){
		final IntegratedServer server = this.client.getServer();

		if (!PackRuleMenus.CanModifyWorld())
			return;

		switch (PackRuleMenus.BUTTON_LOCATION)
		{
			default: return;

			case EButtonLocation.OPTIONS_BODY: {
				adder.add(new GameruleHandler(this, server).CreateButton());
				adder.add(new DatapackHandler(this, server).CreateButton());
				break;
			}

			case EButtonLocation.OPTIONS_HEADER: {
				DirectionalLayoutWidget subHeader = DirectionalLayoutWidget.horizontal().spacing(8);
				subHeader.add(new GameruleHandler(this, server).CreateButton());
				subHeader.add(new DatapackHandler(this, server).CreateButton());
	
				header.add(subHeader);
				header.spacing(4);
				this.layout.setHeaderHeight(layout.getHeaderHeight() + 28);
				break;
			}
		}

	}

}
