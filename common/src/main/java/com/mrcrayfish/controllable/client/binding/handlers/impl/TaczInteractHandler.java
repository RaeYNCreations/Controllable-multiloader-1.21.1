package com.mrcrayfish.controllable.client.binding.handlers.impl;

import com.mrcrayfish.controllable.client.binding.handlers.OnPressHandler;
import com.mrcrayfish.controllable.client.binding.handlers.action.context.Context;
import com.mrcrayfish.controllable.integration.TaczSupport;

import java.util.Optional;

public class TaczInteractHandler extends OnPressHandler {
    
    @Override
    public Optional<Runnable> createPressedHandler(Context context) {
        System.out.println("[TaczInteractHandler] createPressedHandler called"); // DEBUG
        return Optional.of(() -> {
            System.out.println("[TaczInteractHandler] Press action running"); // DEBUG
            TaczSupport.handleInteractButton(true);
        });
    }
}