package jeckelflatbedrockmod.content;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class BedrockFlattener
{
	static { INSTANCE = new BedrockFlattener(); }
	
	public static final BedrockFlattener INSTANCE;
	
	public int flattenChunk(final World world, int chunkX, int chunkZ)
	{
		int countFloor = 0;
		int countCeiling = 0;
		for (int offsetX = 0; offsetX < 16; offsetX++)
		{
			for (int offsetZ = 0; offsetZ < 16; offsetZ++)
			{
				final int blockX = chunkX * 16 + offsetX;
				final int blockZ = chunkZ * 16 + offsetZ;

				if (Block.isEqualTo(Blocks.bedrock, world.getBlock(blockX, 0, blockZ)))
				{
					final int startY = 1;
					countFloor += this.flattenFloor(world, startY, blockX, blockZ);
				}
				
				if (Block.isEqualTo(Blocks.bedrock, world.getBlock(blockX, world.getActualHeight(), blockZ)))
				{
					final int startY = world.getActualHeight() - 1 - 5;
					countCeiling += this.flattenCeiling(world, startY, blockX, blockZ);
				}
			}
		}
		return countFloor + countCeiling;
	}
	
	public int flattenFloor(final World world, final int startY, final int blockX, final int blockZ)
	{
		Block block = this.getFloorReplacementBlock(world, blockX, blockZ);
		return this.flattenColumn(world, startY, blockX, blockZ, block);
	}
	
	public int flattenCeiling(final World world, final int startY, final int blockX, final int blockZ)
	{
		Block block = this.getCeilingReplacementBlock(world, blockX, blockZ);
		return this.flattenColumn(world, startY, blockX, blockZ, block);
	}
	
	public int flattenColumn(final World world, final int startY, final int blockX, final int blockZ, final Block block)
	{
		int count = 0;
		final int maxY = world.getActualHeight();
		for (int blockY = startY; blockY < maxY; blockY++)
		{
			if (Block.isEqualTo(Blocks.bedrock, world.getBlock(blockX, blockY, blockZ)))
			{
				world.setBlock(blockX, blockY, blockZ, block, 0, 2);
				count++;
			}
			else if (blockY > startY + 20) { break; }
		}
		return count;
	}
	
	public Block getFloorReplacementBlock(final World world, final int blockX, final int blockZ)
	{
		Block block = Blocks.dirt;
		final int maxY = world.getActualHeight() / 2;
		for (int blockY = 1; blockY < maxY; blockY++)
		{
			if (Block.isEqualTo(Blocks.bedrock, world.getBlock(blockX, blockY, blockZ)) || world.getTileEntity(blockX, blockY, blockZ) != null) { continue; }
			block = world.getBlock(blockX, blockY, blockZ);
			break;
		}
		return block;
	}
	
	public Block getCeilingReplacementBlock(final World world, final int blockX, final int blockZ)
	{
		Block block = Blocks.air;
		final int minY = world.getActualHeight() / 2;
		for (int blockY = world.getActualHeight() - 1; blockY > minY; blockY--)
		{
			if (Block.isEqualTo(Blocks.bedrock, world.getBlock(blockX, blockY, blockZ)) || world.getTileEntity(blockX, blockY, blockZ) != null) { continue; }
			block = world.getBlock(blockX, blockY, blockZ);
			break;
		}
		return block;
	}
}
