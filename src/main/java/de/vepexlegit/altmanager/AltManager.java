package de.vepexlegit.altmanager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.Session;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import java.lang.reflect.Field;

@Mod(modid = AltManager.MODID, version = AltManager.VERSION)
public class AltManager {
    public static final String MODID = "altmanager";
    public static final String VERSION = "1.0";

    @EventHandler
    public void init(FMLInitializationEvent event) {
        if (event.getSide() == Side.CLIENT) {
            MinecraftForge.EVENT_BUS.register(this);
        }
    }

    public static Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public void onGuiScreenInit(GuiScreenEvent.InitGuiEvent.Post event) {
        GuiScreen screen = event.gui;
        if (screen instanceof GuiIngameMenu) {
            int x = event.gui.width / 2 - 100;
            int y = event.gui.height / 4 + 128;
            event.buttonList.add(new GuiButton(101, x, y, "Alt Manager"));
        }
    }

    @SubscribeEvent
    public void onGuiScreenActionPerformed(GuiScreenEvent.ActionPerformedEvent.Post event) {
        GuiScreen screen = event.gui;
        if (screen instanceof GuiIngameMenu && event.button.id == 101) {
            mc.displayGuiScreen(new AltManagerGui());
        }
    }

    public static void setSession(Session s) {
        Class<? extends Minecraft> mc = Minecraft.getMinecraft().getClass();
        try {
            Field session = null;
            for (Field f : mc.getDeclaredFields()) {
                if (f.getType().isInstance(s)) {
                    session = f;
                }
            }
            if (session == null) {
                throw new IllegalStateException("Session Null");
            }
            session.setAccessible(true);
            session.set(Minecraft.getMinecraft(), s);
            session.setAccessible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
