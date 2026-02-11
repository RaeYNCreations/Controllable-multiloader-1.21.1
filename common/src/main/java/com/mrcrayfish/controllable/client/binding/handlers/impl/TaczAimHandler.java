package com.mrcrayfish.controllable.client.binding.handlers.impl;

import com.mrcrayfish.controllable.client.binding.handlers.TickingHandler;
import com.mrcrayfish.controllable.client.binding.handlers.action.BindingReleased;
import com.mrcrayfish.controllable.client.binding.handlers.action.context.Context;
import com.mrcrayfish.controllable.integration.TaczSupport;

import java.util.Optional;

public class TaczAimHandler extends TickingHandler implements BindingReleased {
    
    @Override
    public TickPhase phase() {
        return TickPhase.START_CLIENT;
    }

    @Override
    public void handleTick(Context context) {
        System.out.println("[TaczAimHandler] handleTick called");
        TaczSupport.handleAimButton(true);
    }

    @Override
    public Optional<Runnable> createPressedHandler(Context context) {
        System.out.println("[TaczAimHandler] createPressedHandler called");
        return Optional.of(() -> {
            System.out.println("[TaczAimHandler] Press action running");
            TaczSupport.handleAimButton(true);
        });
    }

    @Override
    public boolean handleReleased(Context context) {
        System.out.println("[TaczAimHandler] handleReleased called");
        TaczSupport.handleAimButton(false);
        return true;
    }
}