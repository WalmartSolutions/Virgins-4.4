package bre2el.fpsreducer.mixin;

import java.util.Map;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={Chunk.class})
public interface ChunkAccessor {
    @Accessor(value="blockEntities")
    public Map<BlockPos, BlockEntity> getBlockEntities();
}
