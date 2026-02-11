package com.mrcrayfish.controllable.integration;

import java.lang.reflect.Method;

public class TaczSupport {
    private static Method onAimControllerPress;
    private static Method semiShootController;
    private static Method onZoomControllerPress;

    static {
        try {
            Class<?> aimKey = Class.forName("com.tacz.guns.client.input.AimKey");
            onAimControllerPress = aimKey.getMethod("onAimControllerPress", boolean.class);

            Class<?> shootKey = Class.forName("com.tacz.guns.client.input.ShootKey");
            semiShootController = shootKey.getMethod("semiShootController", boolean.class);

            Class<?> zoomKey = Class.forName("com.tacz.guns.client.input.ZoomKey");
            onZoomControllerPress = zoomKey.getMethod("onZoomControllerPress", boolean.class);
        } catch (Exception e) {
            // TacZ not loaded or methods not found
        }
    }

    public static void handleZoomButton(boolean pressed) {
        // Call TacZ's zoom method via reflection
        try {
            Class<?> zoomKey = Class.forName("com.tacz.guns.client.input.ZoomKey");
            Method method = zoomKey.getMethod("onZoomControllerPress", boolean.class);
            method.invoke(null, pressed);
        } catch (Exception e) {
            // Handle
        }
    }
}