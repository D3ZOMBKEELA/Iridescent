package mod.iridescent.proxy;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ServerProxy extends CommonProxy {
	
	private static final Map<String, NBTTagCompound> extendedEntityData = new HashMap<String, NBTTagCompound>();
	
	public void registerRenderers() {
		
	}
	
	public Object getServerGuiElement(int guiId, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}
	
	public Object getClientGuiElement(int guiId, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}
	
	public static void storeEntityData(String name, NBTTagCompound compound) {
		extendedEntityData.put(name, compound);
	}
	
	public static NBTTagCompound getEntityData(String name) {
		if(extendedEntityData.get(name) != null) {
			return extendedEntityData.remove(name);
		} else {
			return null;
		}
	}
	
}