package cc.unilock.elytrafix;

import cc.unilock.elytrafix.event.UpdatePlayerAbilitiesCallback;
import net.fabricmc.api.ModInitializer;
import pw.lakuna.elytra_trinket.ElytraTrinket;

public class ElytraFix implements ModInitializer {
	//public static final String MOD_ID = "elytrafix";
    //public static final Logger LOGGER = LoggerFactory.getLogger("ElytraFix");

	@Override
	public void onInitialize() {
		UpdatePlayerAbilitiesCallback.EVENT.register(player -> {
			if (ElytraTrinket.isEquipped(player)) {
				player.getAbilities().allowFlying = false;
				player.getAbilities().flying = false;

				player.startFallFlying();

				return false;
			}

            return true;
        });
	}
}
