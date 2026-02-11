package com.mrcrayfish.controllable.client.binding.handlers.impl;

import com.mrcrayfish.controllable.client.binding.handlers.OnPressAndReleaseHandler;
import com.mrcrayfish.controllable.integration.TaczSupport;
import com.mrcrayfish.controllable.client.binding.handlers.action.context.Context;

import java.util.Optional;

/**
 * Author: MrCrayfish
 */
public class TaczAimHandler extends OnPressAndReleaseHandler
{
    @Override
    public Optional<Runnable> createPressedHandler(Context context) {
        return Optional.of(() -> TaczSupport.handleAimButton(true));
    }
    
    @Override
    public boolean handleReleased(Context context) {
        TaczSupport.handleAimButton(false);
        return true;
    }
}