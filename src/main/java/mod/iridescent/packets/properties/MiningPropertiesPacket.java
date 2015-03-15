package mod.iridescent.packets.properties;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import mod.iridescent.packets.AbstractPacket;
import mod.iridescent.properties.IridescentMiningProperties;

public class MiningPropertiesPacket extends AbstractPacket {
	
	private NBTTagCompound data;
	
	public MiningPropertiesPacket() {}
	
	public MiningPropertiesPacket(EntityPlayer player) {
		data = new NBTTagCompound();
		IridescentMiningProperties.get(player).saveNBTData(data);
	}
	
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		ByteBufUtils.writeTag(buffer, data);
	}
	
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		data = ByteBufUtils.readTag(buffer);
	}
	
	public void handleClientSide(EntityPlayer player) {
		IridescentMiningProperties.get(player).loadNBTData(data);
		System.out.println("\tMining:");
		System.out.println("\tLevel: " + IridescentMiningProperties.get(player).getMiningLevel());
		System.out.println("\tExp: " + IridescentMiningProperties.get(player).getCurrentExp() + "/" + IridescentMiningProperties.get(player).getMaxExp());
	}
	
	public void handleServerSide(EntityPlayer player) {
		// Never needed to be sent to the server, only recieved from
	}
}