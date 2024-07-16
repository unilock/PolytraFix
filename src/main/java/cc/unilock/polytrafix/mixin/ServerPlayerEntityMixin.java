package cc.unilock.polytrafix.mixin;

import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pw.lakuna.elytra_trinket.ServerTools;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {
	@Unique
	private final ServerPlayerEntity PLAYER = (ServerPlayerEntity) (Object) this;

	@Inject(method = "tick", at = @At("RETURN"))
	private void tick(CallbackInfo ci) {
		if (PLAYER.isCreative()) return;

		if (ServerTools.isElytraTrinketEquipped(PLAYER) && !PLAYER.isFallFlying()) {
			if (!PLAYER.getAbilities().allowFlying) {
				PLAYER.getAbilities().allowFlying = true;
				PLAYER.sendAbilitiesUpdate();
			}
		} else {
			PLAYER.getAbilities().allowFlying = false;
			PLAYER.getAbilities().flying = false;
			PLAYER.sendAbilitiesUpdate();
		}
	}
}
