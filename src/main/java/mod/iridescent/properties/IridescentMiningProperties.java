package mod.iridescent.properties;

import mod.iridescent.Iridescent;
import mod.iridescent.packets.properties.MiningPropertiesPacket;
import mod.iridescent.proxy.ServerProxy;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class IridescentMiningProperties implements IExtendedEntityProperties {
	
	public static final String MINING_PROP_NAME = "IridescentMining";
	
	private int miningLevel;
	private int currentExp;
	private int maxExp;
	
	private final EntityPlayer thePlayer;
	
	public IridescentMiningProperties(EntityPlayer player) {
		this.thePlayer = player;
		
		miningLevel = 1;
		currentExp = 0;
		maxExp = 15;
	}
	
	private static String getSaveKey(EntityPlayer player) {
		return player.getName() + ":" + MINING_PROP_NAME;
	}
	
	public static void saveProxyData(EntityPlayer player) { 
		IridescentMiningProperties properties = get(player);
		NBTTagCompound savedData = new NBTTagCompound();
		properties.saveNBTData(savedData);
		ServerProxy.storeEntityData(getSaveKey(player), savedData);
	}
	
	public static void loadProxyData(EntityPlayer player) {
		IridescentMiningProperties properties = get(player);
		NBTTagCompound savedData = ServerProxy.getEntityData(getSaveKey(player));
		
		if(savedData != null) {
			properties.loadNBTData(savedData);
		}
		
		sync(player);
	}
	
	public static void sync(EntityPlayer player) {
		Iridescent.packetPipeline.sendTo(new MiningPropertiesPacket(player), (EntityPlayerMP)player);
	}
	
	public static final void register(EntityPlayer player) {
		if(player.getExtendedProperties(MINING_PROP_NAME) == null) {
			player.registerExtendedProperties(MINING_PROP_NAME, new IridescentMiningProperties(player));
		}
	}
	
	public static final IridescentMiningProperties get(EntityPlayer player) {
		return (IridescentMiningProperties)player.getExtendedProperties(MINING_PROP_NAME);
	}
	
	public void saveNBTData(NBTTagCompound compound) {
		NBTTagCompound properties = new NBTTagCompound();
		
		properties.setInteger("Level", miningLevel);
		properties.setInteger("CurrentExp", currentExp);
		properties.setInteger("MaxExp", maxExp);
		
		compound.setTag(MINING_PROP_NAME, properties);
	}
	
	public void loadNBTData(NBTTagCompound compound) {
		NBTTagCompound properties = (NBTTagCompound)compound.getTag(MINING_PROP_NAME);
		
		miningLevel = properties.getInteger("Level");
		currentExp = properties.getInteger("CurrentExp");
		maxExp = properties.getInteger("MaxExp");
	}
	
	public boolean giveExp(int amount) {
		boolean levelUp = false;
		int extraExp = 0;
		
		if(amount + currentExp >= maxExp) {
			levelUp = true;
			miningLevel++;
			extraExp = amount + currentExp - maxExp;
			
			while(extraExp >= maxExp) {
				miningLevel++;
				extraExp -= maxExp;
			}
			
			currentExp = extraExp;
		} else {
			currentExp += amount;
		}
		
		sync(thePlayer);
		
		return levelUp;
	}
	
	public void increaseLevel(int levels) {
		giveExp(maxExp - currentExp);
	}
	
	public void increaseLevel() {
		increaseLevel(1);
	}
	
	public int getCurrentExp() {
		return currentExp;
	}
	
	public int getMaxExp() {
		return maxExp;
	}
	
	public int getMiningLevel() {
		return miningLevel;
	}
	
	@Override
	public void init(Entity entity, World world) {
	}
	
	private void addDataWatcherObject(int id, Object object) {
		this.thePlayer.getDataWatcher().addObject(id, object);
	}
	
	private void updateDataWatcherObject(int id, Object newData) {
		this.thePlayer.getDataWatcher().updateObject(id, newData);
	}
	
	private int getDataWatcherObject(int id) {
		return this.thePlayer.getDataWatcher().getWatchableObjectInt(id);
	}
	
}