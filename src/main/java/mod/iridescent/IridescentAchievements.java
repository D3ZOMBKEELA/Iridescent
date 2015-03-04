package mod.iridescent;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import mod.iridescent.helper.AchievementHelper;

public class IridescentAchievements {
	
	AchievementHelper achievementHelper = new AchievementHelper();
	
	public void postInit() {
		achievementHelper.initAchievements();
	}
	
}