# Data-Pack / Game-Rule Menus

Takes the titular menus from the World-Creation menu, and makes them available from already created singleplayer worlds.
This provides a more user-friendly alternative to the vanilla `/gamerule` and `/datapack` commands.

Like those commands, the menus will only be accessible in worlds with cheats/commands enabled.

Unlike the vanilla `/datapack` command, the Datapack menu from this mod can be used to toggle experimental features!


## Word of caution: Datapacks

Some datapacks listed below require a world restart to take effect.
Whenever possible, the mod will show a warning after toggling one of them, and offer you the option to either back-out, or exit the world gracefully.

### Registry Packs
Packs that add data to registries (painting variants, etc), require a world restart to fully take effects, but **are not detected by the mod,** thus no warning will be displayed for them.

Toggling these packs in the menu is no different from using the command; it may cause some errors in the log, but those are benign so long as you restart the world immediately after.

### Experimental Feature
Packs that include experimental features (such as bundles) are properly detected by the mod. Toggling them will also toggle, the corresponding feature-flag on the world, and show the proper warnings.

### Vanilla Datapack
The Vanilla datapack can technically be disabled, but doing so will break worlds most of the times, so you probably don't want to do it. Their are some marginal use cases for this, so the option won't be removed.
An additional warning screen will appear when trying to disable this pack.

If you can't load a world after having disabled the Vanilla datapack, loading it in Safe Mode should be able to restore it.