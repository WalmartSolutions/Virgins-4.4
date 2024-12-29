package bre2el.fpsreducer.mixin;

import net.minecraft.client.world.ClientChunkManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={ClientChunkManager.class})
public interface ClientChunkManagerAccessor {
    @Accessor(value="chunks")
    public ClientChunkManager.ClientChunkMap getChunks(); //need accessor
}
