package bre2el.fpsreducer.feature.module.setting;

import net.minecraft.registry.DefaultedRegistry;
import java.util.function.Consumer;
import java.util.Objects;
import net.minecraft.registry.Registries;
import java.util.Collection;
import java.util.ArrayList;
import net.minecraft.item.Item;
import java.util.List;

public class ItemSetting extends Setting
{
    public boolean extended;
    public List<Item> selected;
    public String searchResult;
    public String description;
    public List<Item> items;
    public String name;

    public ItemSetting(final String searchResult, final String description, final String name, final Item[] items) {
        super(searchResult, description);
        this.items = new ArrayList<Item>();
        this.selected = new ArrayList<Item>();
        this.name = searchResult;
        this.description = description;
        this.searchResult = name;
        this.items.addAll(getAllItems());
        if (items == null) {
            return;
        }
        for (final Item item : items) {
            if (!this.selected.contains(item)) {
                this.selected.add(item);
            }
        }
    }

    public static List<Item> getAllItems() {
        final ArrayList list = new ArrayList();
        final DefaultedRegistry item = Registries.ITEM;
        final ArrayList obj = list;
        Objects.requireNonNull(obj);
        item.forEach((Consumer)obj::add);
        return list;
    }

    public void setSearchResult(final String searchResult) {
        this.searchResult = searchResult;
    }

    public String getSearchResult() {
        return this.searchResult;
    }

    public List<Item> getItems() {
        return this.items;
    }

    public boolean isExtended() {
        return this.extended;
    }

    public List<Item> getSelected() {
        return this.selected;
    }

    public void setExtended(final boolean extended) {
        this.extended = extended;
    }
}
