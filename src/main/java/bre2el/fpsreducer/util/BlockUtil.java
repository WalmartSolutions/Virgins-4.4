package bre2el.fpsreducer.util;

import net.minecraft.block.entity.BlockEntity;

public class BlockUtil {

    // what a joke
    public static Iterable<BlockEntity> blockEntities() {
        return BlockEntityIterator::new;
    }
}
