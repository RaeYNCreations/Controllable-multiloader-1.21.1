package com.mrcrayfish.controllable.client.binding.handlers.impl;

import com.mrcrayfish.controllable.client.binding.handlers.TickingHandler;
import com.mrcrayfish.controllable.client.binding.handlers.action.BindingReleased;
import com.mrcrayfish.controllable.client.binding.handlers.action.context.Context;
import com.mrcrayfish.controllable.integration.TaczSupport;

import java.util.Optional;

public class TaczShootHandler extends TickingHandler implements BindingReleased {
    
    @Override
    public TickPhase phase() {
        return TickPhase.START_CLIENT;
    }

    @Override
    public void handleTick(Context context) {
        // Continuously shoot while button is held
        TaczSupport.handleShootButton(true);
    }

    @Override
    public Optional<Runnable> createPressedHandler(Context context) {
        return Optional.of(() -> {
            TaczSupport.handleShootButton(true);
        });
    }

    @Override
    public boolean handleReleased(Context context) {
        TaczSupport.handleShootButton(false);
        return true;
    }
}