package mod.iridescent.proxy;

import mod.iridescent.gui.GuiIridescentProperties;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {
	
	public void registerRenderers() {
		MinecraftForge.EVENT_BUS.register(new GuiIridescentProperties(Minecraft.getMinecraft()));
	}
	
	public Object getServerGuiElement(int guiId, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}
	
	public Object getClientGuiElement(int guiId, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}
	
}