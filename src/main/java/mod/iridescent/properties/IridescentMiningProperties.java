package mod.iridescent.properties;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class IridescentMiningProperties implements IExtendedEntityProperties {
	
	public static final String MINING_PROP_NAME = "IridescentMining";
	
	private int miningLevel;
	private int maxLevelExp;
	private int currentLevelExp;
	
	private final EntityPlayer thePlayer;
	
	public IridescentMiningProperties(EntityPlayer player) {
		this.thePlayer = player;
		this.miningLevel = 1;
		this.currentLevelExp = 0;
		this.maxLevelExp = 15;
	}
	
	public static void register(EntityPlayer player) {
		player.registerExtendedProperties(MINING_PROP_NAME, new IridescentMiningProperties(player));
	}
	
	public static IridescentMiningProperties get(EntityPlayer player) {
		return (IridescentMiningProperties)player.getExtendedProperties(MINING_PROP_NAME);
	}
	
	@Override
	public void saveNBTData(NBTTagCompound compound) {
		NBTTagCompound properties = new NBTTagCompound();
		
		properties.setInteger("Level", this.miningLevel);
		properties.setInteger("CurrentExp", this.currentLevelExp);
		properties.setInteger("MaxExp", this.maxLevelExp);
		
		compound.setTag(MINING_PROP_NAME, properties);
	}
	
	@Override
	public void loadNBTData(NBTTagCompound compound) {
		NBTTagCompound properties = (NBTTagCompound)compound.getTag(MINING_PROP_NAME);
		
		this.miningLevel = properties.getInteger("Level");
		this.currentLevelExp = properties.getInteger("CurrentExp");
		this.maxLevelExp = properties.getInteger("MaxExp");
		
		printMiningDetails();
	}
	
	public boolean giveExp(int amount) {
		boolean levelUp = false;
		int extraExp = 0;
		
		if(amount + this.currentLevelExp >= this.maxLevelExp) {
			levelUp = true;
			this.miningLevel++;
			extraExp = amount + this.currentLevelExp - this.maxLevelExp;
			
			while(extraExp >= this.maxLevelExp) {
				this.miningLevel++;
				extraExp -= this.maxLevelExp;
			}
			
			this.currentLevelExp = extraExp;
			
		} else {
			this.currentLevelExp += amount;
		}
		
		printMiningDetails();
		
		return levelUp;
	}
	
	public void increaseLevel(int levels) {
		giveExp(this.maxLevelExp - this.currentLevelExp);
	}
	
	public void increaseLevel() {
		increaseLevel(1);
	}
	
	public void printMiningDetails() {
		System.out.println("\tMining:");
		System.out.println("\tLevel: " + this.miningLevel);
		System.out.println("\tExp: " + this.currentLevelExp + "/" + this.maxLevelExp);
	}
	
	@Override
	public void init(Entity entity, World world) {
	}
	
}