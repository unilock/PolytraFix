package cc.unilock.polytrafix;

import cc.unilock.polytrafix.event.UpdatePlayerAbilitiesCallback;
import net.fabricmc.api.ModInitializer;
import pw.lakuna.elytra_trinket.ServerTools;

public class PolytraFix implements ModInitializer {
	//public static final String MOD_ID = "polytrafix";
    //public static final Logger LOGGER = LoggerFactory.getLogger("PolytraFix");

	@Override
	public void onInitialize() {
		UpdatePlayerAbilitiesCallback.EVENT.register(player -> {
			if (ServerTools.isElytraTrinketEquipped(player)) {
				player.getAbilities().allowFlying = false;
				player.getAbilities().flying = false;

				player.startFallFlying();

				return false;
			}

            return true;
        });
	}
}
