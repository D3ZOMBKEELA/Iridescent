package mod.iridescent.properties;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public abstract class IridescentSkillProperty implements ISkillProperty {
	
	protected int skillLevel;
	protected int currentExp;
	protected int maxExp;
	
	protected final EntityPlayer thePlayer;
	
	public IridescentSkillProperty(EntityPlayer player) {
		this.thePlayer = player;
		
		skillLevel = 1;
		currentExp = 0;
		maxExp = 15;
	}
	
	protected static String getSaveKey(String skillName, EntityPlayer player) {
		return player.getName() + ":" + skillName;
	}
	
	public boolean giveExp(int amount) {
		boolean levelUp = false;
		int extraExp = 0;
		
		if(currentExp + amount >= maxExp) {
			levelUp = true;
			skillLevel++;
			extraExp = currentExp + amount - maxExp;
			
			while(extraExp >= maxExp) {
				skillLevel++;
				extraExp -= maxExp;
			}
			
			currentExp = extraExp;
		} else {
			currentExp += amount;
		}
		
		sync(thePlayer);
		
		return levelUp;
	}
	
	public int getCurrentExp() {
		return currentExp;
	}
	
	public int getMaxExp() {
		return maxExp;
	}
	
	public int getSkillLevel() {
		return skillLevel;
	}
	
	public void init(Entity player, World world) {
	}
	
}