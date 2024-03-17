package cc.unilock.elytrafix;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ElytraFix implements ModInitializer {
	public static final String MOD_ID = "elytrafix";
    public static final Logger LOGGER = LoggerFactory.getLogger("ElytraFix");
	//public static final ElytraFixConfig CONFIG = ElytraFixConfig.createToml(FabricLoader.getInstance().getConfigDir(), MOD_ID, "config", ElytraFixConfig.class);

	@Override
	public void onInitialize() {
		LOGGER.info("Started");
	}

	public static Identifier id(String path) {
		return new Identifier(ElytraFix.MOD_ID, path);
	}
}
