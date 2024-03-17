package cc.unilock.elytrafix.mixin;

import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {
	@Unique
	private final ServerPlayerEntity PLAYER = (ServerPlayerEntity) (Object) this;

	@Inject(method = "tick", at = @At("RETURN"))
	private void tick(CallbackInfo ci) {
		if (!PLAYER.getAbilities().allowFlying) {
			PLAYER.getAbilities().allowFlying = true;
			PLAYER.sendAbilitiesUpdate();
		}
	}
}
