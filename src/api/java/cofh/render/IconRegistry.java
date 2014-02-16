package cofh.render;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;

import java.util.HashMap;
import java.util.Map;

public class IconRegistry {
    public static Map<String, Icon> icons = new HashMap<String, Icon>();

    public static void addIcon(String iconName, String iconLocation, IconRegister ir) {

        icons.put(iconName, ir.registerIcon(iconLocation));
    }

    public static void addIcon(String iconName, Icon icon) {

        icons.put(iconName, icon);
    }

    public static Icon getIcon(String iconName) {

        return icons.get(iconName);
    }

    public static Icon getIcon(String iconName, int iconOffset) {

        return icons.get(iconName + iconOffset);
    }
}
