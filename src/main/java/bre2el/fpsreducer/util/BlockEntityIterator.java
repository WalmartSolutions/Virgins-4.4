package bre2el.fpsreducer.util;

import bre2el.fpsreducer.mixin.ChunkAccessor;
import java.util.Iterator;
import java.util.Map;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.chunk.Chunk;

public class BlockEntityIterator implements Iterator {
    public Iterator<Chunk> chunks = new ChunkIterator(false);
    public Iterator<BlockEntity> blockEntities;

    public BlockEntity next() {
        return (BlockEntity)this.blockEntities.next();
    }

    public BlockEntityIterator() {
        this.nextChunk();
    }

    void nextChunk() {
        while (this.chunks.hasNext()) {
            Map var1 = ((ChunkAccessor)this.chunks.next()).getBlockEntities();
            if (var1.size() > 0) {
                this.blockEntities = var1.values().iterator();
                break;
            }
        }
    }

    public boolean hasNext() {
        if (this.blockEntities == null) {
            return false;
        } else if (!this.blockEntities.hasNext()) {
            this.nextChunk();
            return this.blockEntities.hasNext();
        } else {
            return true;
        }
    }
}
