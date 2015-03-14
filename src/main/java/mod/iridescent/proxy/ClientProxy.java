package mod.iridescent.proxy;

import mod.iridescent.gui.GuiIridescentProperties;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {
	
	public void registerRenderers() {
		MinecraftForge.EVENT_BUS.register(new GuiIridescentProperties(Minecraft.getMinecraft()));
	}
	
}