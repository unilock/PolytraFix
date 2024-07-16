package cc.unilock.polytrafix.mixin;

import cc.unilock.polytrafix.event.UpdatePlayerAbilitiesCallback;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.network.packet.c2s.play.UpdatePlayerAbilitiesC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandlerMixin {
	@Shadow
	public ServerPlayerEntity player;

	@Inject(method = "onUpdatePlayerAbilities", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/NetworkThreadUtils;forceMainThread(Lnet/minecraft/network/packet/Packet;Lnet/minecraft/network/listener/PacketListener;Lnet/minecraft/server/world/ServerWorld;)V", shift = At.Shift.AFTER), cancellable = true)
	private void onUpdatePlayerAbilities(UpdatePlayerAbilitiesC2SPacket packet, CallbackInfo ci) {
		ci.cancel();
		if (this.player.getAbilities().allowFlying && this.player.getAbilities().flying != packet.isFlying()) {
			if (UpdatePlayerAbilitiesCallback.EVENT.invoker().update(this.player)) {
				this.player.getAbilities().flying = packet.isFlying();
			} else {
				this.player.sendAbilitiesUpdate();
			}
		}
	}

	@Redirect(method = "onPlayerMove", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/PlayerAbilities;allowFlying:Z", opcode = Opcodes.GETFIELD))
	private boolean onPlayerMove(PlayerAbilities instance) {
		return instance.creativeMode || instance.invulnerable;
	}
}
