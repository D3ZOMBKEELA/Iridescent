package mod.iridescent.properties;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.IExtendedEntityProperties;

public interface ISkillProperty extends IExtendedEntityProperties {
	
	public void sync(EntityPlayer player);
	
}