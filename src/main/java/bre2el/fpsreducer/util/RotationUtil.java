package bre2el.fpsreducer.util;


import bre2el.fpsreducer.client.Main;
import bre2el.fpsreducer.util.RotationManager.Rotation;
import java.util.concurrent.CompletableFuture;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;

public class RotationUtil {
    public static double getTotalDiff(Rotation rotation1, Rotation rotation2) {
        Rotation var2 = getDiff(rotation1, rotation2);
        return (double)(var2.getYaw() + var2.getPitch());
    }

    public static HitResult getHitResultOther(PlayerEntity entity, boolean ignoreInvisible, float yaw, float pitch, double reach) {
        HitResult var6 = null;
        if (entity != null && Main.mc.world != null) {
            Vec3d var9 = entity.getCameraPosVec(Main.mc.getRenderTickCounter().getTickDelta(true));
            Vec3d var10 = getPlayerLookVec(yaw, pitch);
            Vec3d var11 = var9.add(var10.x * reach, var10.y * reach, var10.z * reach);
            var6 = Main.mc.world.raycast(new RaycastContext(var9, var11, ShapeType.OUTLINE, FluidHandling.NONE, entity));
            boolean var12 = false;
            double var13 = reach;
            double var7;
            if (!(Main.mc.player.getBlockInteractionRange() > 4.5)) {
                if (reach > 3.0) {
                    var12 = true;
                }

                var7 = reach;
            } else {
                var13 = 6.0;
                var7 = 6.0;
            }

            var13 *= var13;
            if (var6 != null) {
                var13 = var6.getPos().squaredDistanceTo(var9);
            }

            Vec3d var15 = var9.add(var10.x * var7, var10.y * var7, var10.z * var7);
            Box var17 = entity.getBoundingBox().stretch(var10.multiply(var7)).expand(1.0, 1.0, 1.0);
            EntityHitResult var18 = ProjectileUtil.raycast(
                    entity, var9, var15, var17, entityx -> !entityx.isSpectator() && entityx.canHit() && !entityx.isInvisible(), var13
            );
            if (var18 != null) {
                Vec3d var19 = var18.getPos();
                double var20 = var9.squaredDistanceTo(var19);
                if (var12 && var20 > 9.0) {
                    var6 = BlockHitResult.createMissed(var19, Direction.getFacing(var10.x, var10.y, var10.z), BlockPos.ofFloored(var19));
                } else if (var20 < var13 || var6 == null) {
                    var6 = var18;
                }
            }
        }

        return var6;
    }

    public static Rotation getDirection(Entity entity, Vec3d vec) {
        double var2 = vec.x - entity.getX();
        double var4 = vec.y - entity.getY();
        double var6 = vec.z - entity.getZ();
        double var8 = (double)MathHelper.sqrt((float)(var2 * var2 + var6 * var6));
        return new Rotation(
                (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(var6, var2)) - 90.0), (float)(-MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(var4, var8))))
        );
    }

    public static Rotation getDiff(Rotation rotation1, Rotation rotation2) {
        double var2 = (double)Math.abs(Math.max(rotation1.getYaw(), rotation2.getYaw()) - Math.min(rotation1.getYaw(), rotation2.getYaw()));
        double var4 = (double)Math.abs(Math.max(rotation1.getPitch(), rotation2.getPitch()) - Math.min(rotation1.getPitch(), rotation2.getPitch()));
        return new Rotation((float)var2, (float)var4);
    }

    public static double getAngleToLookVecPlayers(PlayerEntity vec, Vec3d player) {
        Rotation var2 = getNeededRotationsPlayers(vec, player);
        float var3 = MathHelper.wrapDegrees(vec.getYaw());
        float var4 = MathHelper.wrapDegrees(vec.getPitch());
        float var5 = MathHelper.wrapDegrees(var3 - var2.getYaw());
        float var6 = MathHelper.wrapDegrees(var4 - var2.getPitch());
        return Math.sqrt((double)(var5 * var5 + var6 * var6));
    }

    public static Rotation getNeededRotations(Vec3d vec3d) {
        return getNeededRotations((float)vec3d.x, (float)vec3d.y, (float)vec3d.z);
    }

    public static boolean checkRotations() {
        return RotationManager.INSTANCE.isEnabled() || RotationManager.INSTANCE.isRotateBack() || RotationManager.INSTANCE.isResetRotation();
    }

    public static Rotation getDir(Entity entity, Vec3d vec) {
        double var2 = vec.x - entity.getX();
        double var4 = vec.y - entity.getY();
        double var6 = vec.z - entity.getZ();
        double var8 = (double)MathHelper.sqrt((float)(var2 * var2 + var6 * var6));
        return new Rotation(
                (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(var6, var2)) - 90.0), (float)(-MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(var4, var8))))
        );
    }

    public static Vec3d getPlayerLookVec(PlayerEntity player) {
        return getPlayerLookVec(player.getYaw(), player.getPitch());
    }

    // this shit took me 5 whole minutes
    public static HitResult getHitResultIgnorePlayers(PlayerEntity entity, boolean ignoreInvisible, float yaw, float pitch, double reach) {
        HitResult var6 = null;
        if (entity != null && Main.mc.world != null) {
            Vec3d var9 = entity.getCameraPosVec(Main.mc.getRenderTickCounter().getTickDelta(true));
            Vec3d var10 = getPlayerLookVec(yaw, pitch);
            Vec3d var11 = var9.add(var10.x * reach, var10.y * reach, var10.z * reach);
            var6 = Main.mc.world.raycast(new RaycastContext(var9, var11, ShapeType.OUTLINE, FluidHandling.NONE, entity));
            boolean var12 = false;
            double var13 = reach;
            double var7;
            if (!(Main.mc.player.getBlockInteractionRange() > 4.5)) {
                if (reach > 3.0) {
                    var12 = true;
                }

                var7 = reach;
            } else {
                var13 = 6.0;
                var7 = 6.0;
            }

            var13 *= var13;
            if (var6 != null) {
                var13 = var6.getPos().squaredDistanceTo(var9);
            }

            Vec3d var15 = var9.add(var10.x * var7, var10.y * var7, var10.z * var7);
            Box var17 = entity.getBoundingBox().stretch(var10.multiply(var7)).expand(1.0, 1.0, 1.0);
            EntityHitResult var18 = ProjectileUtil.raycast(entity, var9, var15, var17, entityx -> !entityx.isPlayer(), var13);
            if (var18 != null) {
                Vec3d var19 = var18.getPos();
                double var20 = var9.squaredDistanceTo(var19);
                if (var12 && var20 > 9.0) {
                    var6 = BlockHitResult.createMissed(var19, Direction.getFacing(var10.x, var10.y, var10.z), BlockPos.ofFloored(var19));
                } else if (var20 < var13 || var6 == null) {
                    var6 = var18;
                }
            }
        }

        return var6;
    }

    public static Rotation getNeededRotations(float f3, float f, float f2) {
        Vec3d var3 = Main.mc.player.getEyePos();
        double var4 = (double)f3 - var3.x;
        double var6 = (double)f - var3.y;
        double var8 = (double)f2 - var3.z;
        float[] var10 = new float[]{Main.mc.player.getYaw(), Main.mc.player.getPitch()};
        return new Rotation(
                var10[0] + wrap((float)Math.toDegrees(Math.atan2(var8, var4)) - 90.0F - var10[0]),
                var10[1] + wrap((float)(-Math.toDegrees(Math.atan2(var6, Math.sqrt(var4 * var4 + var8 * var8)))) - var10[1])
        );
    }

    static CompletableFuture<HitResult> getHitResultAsync(PlayerEntity entity, boolean yaw, float pitch, float ignoreInvisible) {
        return CompletableFuture.supplyAsync(
                () -> {
                    if (entity != null && Main.mc.world != null) {
                        double var4 = Main.mc.player.getEntityInteractionRange();
                        Vec3d var6 = entity.getCameraPosVec(Main.mc.getRenderTickCounter().getTickDelta(true));
                        Vec3d var7 = getPlayerLookVec(pitch, ignoreInvisible);
                        Vec3d var8 = var6.add(var7.multiply(var4));
                        BlockHitResult var9 = Main.mc.world.raycast(new RaycastContext(var6, var8, ShapeType.OUTLINE, FluidHandling.NONE, entity));
                        double var10 = Math.min(var4, 6.0);
                        double var12 = var10 * var10;
                        if (var9 != null) {
                            var12 = var9.getPos().squaredDistanceTo(var6);
                        }

                        Vec3d var14 = var6.add(var7.multiply(var10));
                        Box var15 = entity.getBoundingBox().stretch(var7.multiply(var4)).expand(1.0, 1.0, 1.0);
                        EntityHitResult var16 = ProjectileUtil.raycast(
                                entity,
                                var6,
                                var14,
                                var15,
                                ignoreInvisiblexx -> !ignoreInvisiblexx.isSpectator() && ignoreInvisiblexx.canHit() && (!ignoreInvisiblexx.isInvisible() || yaw),
                                var12
                        );
                        if (var16 != null) {
                            Vec3d var17 = var16.getPos();
                            double var18 = var6.squaredDistanceTo(var17);
                            if (var18 < var12 || var9 == null) {
                                return var16;
                            }
                        }

                        return var9 != null
                                ? BlockHitResult.createMissed(var9.getPos(), Direction.getFacing(var7.x, var7.y, var7.z), BlockPos.ofFloored(var9.getPos()))
                                : null;
                    } else {
                        return null;
                    }
                }
        );
    }

    public static double getAngleToLookVecByRot(float vec, float pitch, Vec3d yaw) {
        Rotation var3 = getNeededRotations(yaw);
        float var4 = MathHelper.wrapDegrees(vec);
        float var5 = MathHelper.wrapDegrees(pitch);
        float var6 = MathHelper.wrapDegrees(var4 - var3.getYaw());
        float var7 = MathHelper.wrapDegrees(var5 - var3.getPitch());
        return Math.sqrt((double)(var6 * var6 + var7 * var7));
    }

    public static double getAngleToLookVec(Vec3d vec) {
        Rotation var1 = getNeededRotations(vec);
        ClientPlayerEntity var2 = Main.mc.player;
        float var3 = MathHelper.wrapDegrees(var2.getYaw());
        float var4 = MathHelper.wrapDegrees(var2.getPitch());
        float var5 = MathHelper.wrapDegrees(var3 - var1.getYaw());
        float var6 = MathHelper.wrapDegrees(var4 - var1.getPitch());
        return Math.sqrt((double)(var5 * var5 + var6 * var6));
    }

    public static Rotation getNeededRotationsPlayers(PlayerEntity vec3d, Vec3d player) {
        return getNeededRotationsPlayers(vec3d, (float)player.x, (float)player.y, (float)player.z);
    }

    public static Rotation getNeededRotationsPlayers(PlayerEntity player, float f, float f2, float f3) {
        Vec3d var4 = player.getEyePos();
        double var5 = (double)f - var4.x;
        double var7 = (double)f2 - var4.y;
        double var9 = (double)f3 - var4.z;
        float[] var11 = new float[]{player.getYaw(), player.getPitch()};
        return new Rotation(
                var11[0] + wrap((float)Math.toDegrees(Math.atan2(var9, var5)) - 90.0F - var11[0]),
                var11[1] + wrap((float)(-Math.toDegrees(Math.atan2(var7, Math.sqrt(var5 * var5 + var9 * var9)))) - var11[1])
        );
    }

    public static boolean canPlaceCrystalServer(BlockPos pos) {
        if (Main.mc.world == null) {
            return false;
        } else {
            BlockState var1 = Main.mc.world.getBlockState(pos);
            if (var1.getBlock() != Blocks.OBSIDIAN && var1.getBlock() != Blocks.BEDROCK) {
                return false;
            } else {
                BlockPos var2 = pos.up();
                BlockState var3 = Main.mc.world.getBlockState(var2);
                if (Main.mc.world.isAir(var2) && var3.getBlock() != Blocks.OBSIDIAN && var3.getBlock() != Blocks.BEDROCK) {
                    Box var4 = new Box(var2).offset(0.5, 0.0, 0.5).stretch(0.0, 2.0, 0.0);

                    for (Entity var7 : Main.mc.world.getEntitiesByClass(Entity.class, var4, e -> !(e instanceof ClientPlayerEntity))) {
                        if (var7 instanceof EndCrystalEntity) {
                            return false;
                        }
                    }

                    return true;
                } else {
                    return false;
                }
            }
        }
    }

    public static HitResult getHitResult(PlayerEntity pitch, boolean playerEntity, float ignoreInvisible, float yaw) {
        return (HitResult)getHitResultAsync(pitch, playerEntity, ignoreInvisible, yaw).join();
    }

    public static Rotation getSmoothRotation(Rotation from, Rotation to, double speed) {
        return new Rotation(
                MathHelper.lerpAngleDegrees((float)speed, from.getYaw(), to.getYaw()), MathHelper.lerpAngleDegrees((float)speed, from.getPitch(), to.getPitch())
        );
    }

    static Vec3d getPlayerLookVec(float pitch, float yaw) {
        float var2 = yaw * (float) (Math.PI / 180.0);
        float var3 = -pitch * (float) (Math.PI / 180.0);
        float var4 = MathHelper.cos(var3);
        float var5 = MathHelper.sin(var3);
        float var6 = MathHelper.cos(var2);
        float var7 = MathHelper.sin(var2);
        return new Vec3d((double)(var5 * var6), (double)(-var7), (double)(var4 * var6));
    }

    static float wrap(float f) {
        float var1;
        if ((var1 = f % 360.0F) >= 180.0F) {
            var1 -= 360.0F;
        }

        if (var1 < -180.0F) {
            var1 += 360.0F;
        }

        return var1;
    }

    public static HitResult getHitResultBlock(PlayerEntity ignoreInvisible, boolean reach, float yaw, float pitch, double entity) {
        BlockHitResult var6 = null;
        if (ignoreInvisible != null && Main.mc.world != null) {
            Vec3d var9 = ignoreInvisible.getCameraPosVec(Main.mc.getRenderTickCounter().getTickDelta(true));
            Vec3d var10 = getPlayerLookVec(yaw, pitch);
            Vec3d var11 = var9.add(var10.x * entity, var10.y * entity, var10.z * entity);
            var6 = Main.mc.world.raycast(new RaycastContext(var9, var11, ShapeType.OUTLINE, FluidHandling.NONE, ignoreInvisible));
            boolean var12 = false;
            double var13 = entity;
            double var7;
            if (!(Main.mc.player.getBlockInteractionRange() > 4.5)) {
                if (entity > 4.5) {
                    var12 = true;
                }

                var7 = entity;
            } else {
                var13 = 6.0;
                var7 = 6.0;
            }

            double var10000 = var13 * var13;
            if (var6 != null) {
                var6.getPos().squaredDistanceTo(var9);
            }

            var9.add(var10.x * var7, var10.y * var7, var10.z * var7);
            ignoreInvisible.getBoundingBox().stretch(var10.multiply(var7)).expand(1.0, 1.0, 1.0);
            if (var6 != null) {
                Vec3d var18 = var6.getPos();
                double var19 = var9.squaredDistanceTo(var18);
                if (var12 && var19 > 9.0) {
                    var6 = BlockHitResult.createMissed(var18, Direction.getFacing(var10.x, var10.y, var10.z), BlockPos.ofFloored(var18));
                }
            }
        }

        return var6;
    }

    public static Rotation getRotation() {
        return !checkRotations()
                ? new Rotation(Main.mc.player.getYaw(), Main.mc.player.getPitch())
                : new Rotation(RotationManager.INSTANCE.getServerYaw(), RotationManager.INSTANCE.getServerPitch());
    }
}
