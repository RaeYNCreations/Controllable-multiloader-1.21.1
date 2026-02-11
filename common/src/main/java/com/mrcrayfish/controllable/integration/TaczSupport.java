package com.mrcrayfish.controllable.integration;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;

public class TaczSupport {
    private static boolean lastAimState = false;
    
    public static void handleAimButton(boolean pressed) {
        try {
            LocalPlayer player = Minecraft.getInstance().player;
            if (player == null) {
                lastAimState = false;
                return;
            }
            
            Class<?> operatorClass = Class.forName("com.tacz.guns.api.client.gameplay.IClientPlayerGunOperator");
            Class<?> iGun = Class.forName("com.tacz.guns.api.item.IGun");
            
            boolean holdingGun = (boolean) iGun.getMethod("mainHandHoldGun", net.minecraft.world.entity.player.Player.class).invoke(null, player);
            if (!holdingGun) {
                lastAimState = false;
                return;
            }
            
            Object operator = operatorClass.getMethod("fromLocalPlayer", LocalPlayer.class).invoke(null, player);
            
            // Only call if state changed
            if (pressed != lastAimState) {
                operatorClass.getMethod("aim", boolean.class).invoke(operator, pressed);
                lastAimState = pressed;
                System.out.println("[TACZ Debug] Aim state changed to: " + pressed);
            }
            
        } catch (Exception e) {
            lastAimState = false;
            e.printStackTrace();
        }
    }
    
    public static void handleZoomButton(boolean pressed) {
        try {
            if (!pressed) return;
            
            LocalPlayer player = Minecraft.getInstance().player;
            if (player == null) return;
            
            Class<?> operatorClass = Class.forName("com.tacz.guns.api.client.gameplay.IClientPlayerGunOperator");
            Object operator = operatorClass.getMethod("fromLocalPlayer", LocalPlayer.class).invoke(null, player);
            
            boolean isAiming = (boolean) operatorClass.getMethod("isAim").invoke(operator);
            if (!isAiming) {
                System.out.println("[TACZ Debug] Cannot zoom - not aiming");
                return;
            }
            
            Class<?> packetClass = Class.forName("com.tacz.guns.network.message.ClientMessagePlayerZoom");
            Object packet = packetClass.getField("INSTANCE").get(null);
            
            Class<?> packetDist = Class.forName("net.neoforged.neoforge.network.PacketDistributor");
            packetDist.getMethod("sendToServer", Object.class).invoke(null, packet);
            
            System.out.println("[TACZ Debug] Zoom packet sent");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void handleShootButton(boolean pressed) {
        try {
            if (!pressed) return;
            
            LocalPlayer player = Minecraft.getInstance().player;
            if (player == null) return;
            
            Class<?> operatorClass = Class.forName("com.tacz.guns.api.client.gameplay.IClientPlayerGunOperator");
            Object operator = operatorClass.getMethod("fromLocalPlayer", LocalPlayer.class).invoke(null, player);
            
            Object result = operatorClass.getMethod("shoot").invoke(operator);
            System.out.println("[TACZ Debug] Shoot result: " + result);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void handleInteractButton(boolean pressed) {
        try {
            if (!pressed) return;
            
            LocalPlayer player = Minecraft.getInstance().player;
            if (player == null) return;
            
            Class<?> iGun = Class.forName("com.tacz.guns.api.item.IGun");
            boolean holdingGun = (boolean) iGun.getMethod("mainHandHoldGun", net.minecraft.world.entity.player.Player.class).invoke(null, player);
            if (!holdingGun) return;
            
            Minecraft mc = Minecraft.getInstance();
            Object hitResult = mc.hitResult;
            if (hitResult == null) return;
            
            Class<?> blockHitClass = Class.forName("net.minecraft.world.phys.BlockHitResult");
            Class<?> entityHitClass = Class.forName("net.minecraft.world.phys.EntityHitResult");
            
            if (blockHitClass.isInstance(hitResult) || entityHitClass.isInstance(hitResult)) {
                Class<?> clientServices = Class.forName("com.mrcrayfish.controllable.platform.ClientServices");
                Object clientInstance = clientServices.getField("CLIENT").get(null);
                clientServices.getMethod("startUseItem", Minecraft.class).invoke(clientInstance, mc);
                System.out.println("[TACZ Debug] Interact triggered");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}