package mod.iridescent.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public interface IProxy {
	
	public void registerRenderers();
	
	public Object getServerGuiElement(int guiId, EntityPlayer player, World world, int x, int y, int z);
	
	public Object getClientGuiElement(int guiId, EntityPlayer player, World world, int x, int y, int z);
		
}