package bre2el.fpsreducer.mixin;

import java.util.concurrent.atomic.AtomicReferenceArray;
import net.minecraft.client.world.ClientChunkManager;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={ClientChunkManager.ClientChunkMap.class})
public interface ClientChunkMapAccessor {
    @Accessor(value="chunks")
    public AtomicReferenceArray<WorldChunk> getChunks();
}
