package jeckelflatbedrockmod.content;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.common.IWorldGenerator;

public class FlatBedrockWorlGenerator implements IWorldGenerator
{
	@Override public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
	{
		BedrockFlattener.INSTANCE.flattenChunk(world, chunkX, chunkZ);
	}
}
