package com.lypaka.catalystterapokemon.TeraItems;

import java.util.List;

public class TeraShard {

    private final String type;
    private final String id;
    private final String displayName;
    private final List<String> guiLore;
    private final List<String> itemLore;

    public TeraShard (String type, String id, String displayName, List<String> guiLore, List<String> itemLore) {

        this.type = type;
        this.id = id;
        this.displayName = displayName;
        this.guiLore = guiLore;
        this.itemLore = itemLore;

    }

    public String getType() {

        return this.type;

    }

    public String getID() {

        return this.id;

    }

    public String getDisplayName() {

        return this.displayName;

    }

    public List<String> getGUILore() {

        return this.guiLore;

    }

    public List<String> getItemLore() {

        return this.itemLore;

    }

}
