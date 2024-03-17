package cc.unilock.elytrafix.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.network.ServerPlayerEntity;

public interface UpdatePlayerAbilitiesCallback {
	/**
	 * Return false to cancel the update and send a PlayerAbilitiesS2CPacket back to the player
	 */
	Event<UpdatePlayerAbilitiesCallback> EVENT = EventFactory.createArrayBacked(UpdatePlayerAbilitiesCallback.class,
		(callbacks) -> (player) -> {
			for (var callback : callbacks) {
				if (!callback.update(player)) {
					return false;
				}
			}

			return true;
		}
	);

	boolean update(ServerPlayerEntity player);
}
