package com.mrcrayfish.controllable.integration;

import net.minecraft.client.Minecraft;

public class TaczSupport
{
    public static void handleUseButton(boolean pressed)
    {
        Minecraft mc = Minecraft.getInstance();
        // Directly set the use key state - TACZ should detect this
        mc.options.keyUse.setDown(pressed);
    }
    
    public static void handleAttackButton(boolean pressed)
    {
        Minecraft mc = Minecraft.getInstance();
        // Directly set the attack key state - TACZ should detect this
        mc.options.keyAttack.setDown(pressed);
    }
}