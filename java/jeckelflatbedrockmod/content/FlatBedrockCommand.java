package jeckelflatbedrockmod.content;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraft.world.chunk.Chunk;

public class FlatBedrockCommand extends CommandBase
{
	public FlatBedrockCommand()
	{
		this.aliases = new ArrayList<String>();
	    this.aliases.add("flatbedrock");
	    this.aliases.add("flattenbedrock");
	}

	private List<String> aliases;
	
	@Override public String getCommandName() { return (String) this.aliases.get(0); }

	@SuppressWarnings("rawtypes")
	@Override public List getCommandAliases() { return this.aliases; }

	@Override public String getCommandUsage(ICommandSender sender)
	{
		return "/" + this.aliases.get(0) + " - " + StatCollector.translateToLocal("command.flatbedrock.usage.text");
	}
	
	@Override public boolean canCommandSenderUseCommand(ICommandSender p_71519_1_)
    {
        return true;
    }

	@Override public void processCommand(ICommandSender sender, String[] args)
	{
        if (args.length == 0)
        {
        	Chunk chunk = sender.getEntityWorld().getChunkFromBlockCoords(sender.getPlayerCoordinates().posX, sender.getPlayerCoordinates().posZ);
        	int count = BedrockFlattener.INSTANCE.flattenChunk(sender.getEntityWorld(), chunk.xPosition, chunk.zPosition);
        	String text = StatCollector.translateToLocalFormatted("info.flatten.complete.text", count, chunk.xPosition, chunk.zPosition);
    		sender.addChatMessage(new ChatComponentText(text));
        }
        else
        {
            throw new WrongUsageException(this.getCommandUsage(sender), new Object[0]);
        }
    }
}
