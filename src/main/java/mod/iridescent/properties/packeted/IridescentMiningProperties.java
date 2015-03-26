package mod.iridescent.properties.packeted;

import mod.iridescent.Iridescent;
import mod.iridescent.packets.properties.MiningPropertiesPacket;
import mod.iridescent.properties.IridescentSkillProperty;
import mod.iridescent.proxy.ServerProxy;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class IridescentMiningProperties extends IridescentSkillProperty {
	
	public static final String SKILL_NAME = "IridescentMining";
	
	public IridescentMiningProperties(EntityPlayer player) {
		super(player);
	}
	
	public void saveProxyData(EntityPlayer player) { 
		IridescentMiningProperties properties = get(player);
		NBTTagCompound savedData = new NBTTagCompound();
		properties.saveNBTData(savedData);
		ServerProxy.storeEntityData(getSaveKey(SKILL_NAME, player), savedData);
	}
	
	public void loadProxyData(EntityPlayer player) {
		IridescentMiningProperties properties = get(player);
		NBTTagCompound savedData = ServerProxy.getEntityData(getSaveKey(SKILL_NAME, player));
		
		if(savedData != null) {
			properties.loadNBTData(savedData);
		}
		
		sync(player);
	}
	
	public void sync(EntityPlayer player) {
		Iridescent.packetPipeline.sendTo(new MiningPropertiesPacket(player), player);
	}
	
	public static final void register(EntityPlayer player) {
		if(player.getExtendedProperties(SKILL_NAME) == null) {
			player.registerExtendedProperties(SKILL_NAME, new IridescentMiningProperties(player));
		}
	}
	
	public static final IridescentMiningProperties get(EntityPlayer player) {
		return (IridescentMiningProperties)player.getExtendedProperties(SKILL_NAME);
	}
	
	public void saveNBTData(NBTTagCompound compound) {
		NBTTagCompound properties = new NBTTagCompound();
		properties.setInteger("Level", skillLevel);
		properties.setInteger("CurrentExp", currentExp);
		properties.setInteger("MaxExp", maxExp);
		compound.setTag(SKILL_NAME, properties);
	}
	
	public void loadNBTData(NBTTagCompound compound) {
		NBTTagCompound properties = (NBTTagCompound)compound.getTag(SKILL_NAME);
		skillLevel = properties.getInteger("Level");
		currentExp = properties.getInteger("CurrentExp");
		maxExp = properties.getInteger("MaxExp");
	}
	
//	private void addDataWatcherObject(int id, Object object) {
//		this.thePlayer.getDataWatcher().addObject(id, object);
//	}
//	
//	private void updateDataWatcherObject(int id, Object newData) {
//		this.thePlayer.getDataWatcher().updateObject(id, newData);
//	}
//	
//	private int getDataWatcherObject(int id) {
//		return this.thePlayer.getDataWatcher().getWatchableObjectInt(id);
//	}
	
}