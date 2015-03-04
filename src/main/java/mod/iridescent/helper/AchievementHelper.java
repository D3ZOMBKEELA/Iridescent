package mod.iridescent.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;

public class AchievementHelper {
	
	// TODO Add in capability for multiple achievement pages
	// TODO Add in capability to statically give an achievement to the player
	
	private AchievementPage iridescentAchievementPage;
	private ArrayList<Achievement> iridescentAchievements = new ArrayList<Achievement>();
	private HashMap<String, Integer> achievementsLookupMap = new HashMap<String, Integer>();
	
	/**
	 * Use this to add an achievement to the iridescent achievement page
	 * @param statName The name of the stat for minecraft statistics
	 * @param achievementName The name of the achievement
	 * @param x The column for the achievement to appear in
	 * @param y The row for the achievement to appear in
	 * @param stack The item, block, or itemstack used as an icon for the achievement
	 * @param parent The parent achievement that must be acquired before this achievement. Leave null for no parent
	 * @param independant Whether or not this is an independant achievement
	 * @param special Whether or not to have the special border
	 */
	public void addAchievement(String statName, String achievementName, int x, int y, ItemStack stack, Achievement parent, boolean independant, boolean special) {
		iridescentAchievements.add(new Achievement(statName, achievementName, x, y, stack, parent));
		
		if(independant) {
			iridescentAchievements.get(iridescentAchievements.size() - 1).setIndependent();
		}
		
		if(special) {
			iridescentAchievements.get(iridescentAchievements.size() - 1).setSpecial();
		}
		
		achievementsLookupMap.put(statName.toLowerCase(), iridescentAchievements.size());
	}
	
	public Achievement getAchievementFromName(String name) {
		return iridescentAchievements.get(achievementsLookupMap.get(name.toLowerCase()) - 1);
	}
	
	/**
	 * Call this after all achievements have been added
	 */
	public void initAchievements() {
		Achievement[] achievements = new Achievement[iridescentAchievements.size()];
		
		for(int i = 0; i < iridescentAchievements.size(); ++i) {
			iridescentAchievements.get(i).func_180788_c();
			achievements[i] = iridescentAchievements.get(i);
		}
		
		iridescentAchievementPage = new AchievementPage("Iridescent", achievements);
		AchievementPage.registerAchievementPage(iridescentAchievementPage);
	}
	
}