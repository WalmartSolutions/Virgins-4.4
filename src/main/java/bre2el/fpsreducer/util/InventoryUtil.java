package bre2el.fpsreducer.util;

import bre2el.fpsreducer.client.Main;
import java.util.function.Predicate;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class InventoryUtil {
    static boolean selectItemFromHotbar(Predicate<Item> item) {
        PlayerInventory var1 = Main.mc.player.getInventory();

        for (int var2 = 0; var2 < 9; var2++) {
            ItemStack var3 = var1.getStack(var2);
            if (item.test(var3.getItem())) {
                var1.selectedSlot = var2;
                return true;
            }
        }

        return false;
    }

    public static boolean hasItemInHotbar(Item item) {
        PlayerInventory var1 = Main.mc.player.getInventory();

        for (int var2 = 0; var2 < 9; var2++) {
            ItemStack var3 = var1.getStack(var2);
            if (var3.isOf(item)) {
                return true;
            }
        }

        return false;
    }

    public static boolean selectItemFromHotbar(Item item) {
        return selectItemFromHotbar((Predicate<Item>)(i -> i == item));
    }
}
