package com.mrcrayfish.controllable.client.gui.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mrcrayfish.controllable.Config;
import com.mrcrayfish.controllable.client.binding.ButtonBinding;
import com.mrcrayfish.controllable.client.settings.ButtonIcons;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.CommonComponents;

/**
 * Author: MrCrayfish
 */
public class ButtonBindingButton extends Button
{
    private final ButtonBinding binding;
    private final ButtonOnPress onPress;

    public ButtonBindingButton(int x, int y, ButtonBinding binding, ButtonOnPress onPress)
    {
        super(x, y, 40, 20, CommonComponents.EMPTY, btn -> {}, DEFAULT_NARRATION);
        this.binding = binding;
        this.onPress = onPress;
    }

    public ButtonBinding getBinding()
    {
        return this.binding;
    }

    @Override
    public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks)
    {
        super.renderWidget(graphics, mouseX, mouseY, partialTicks);
        
        if(this.binding.isUnbound())
            return;
            
        if(this.binding.isMultiButton())
        {
            // For multi-button bindings, show the first button with a "+" indicator
            int primaryButton = this.binding.getButton();
            int texU = primaryButton * 13;
            int texV = Config.CLIENT.options.controllerIcons.get().ordinal() * 13;
            int size = 13;
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            
            // Draw the first button icon slightly offset to the left
            graphics.blit(ButtonIcons.TEXTURE, this.getX() + (this.width - size) / 2 - 5, this.getY() + 3, texU, texV, size, size, ButtonIcons.TEXTURE_WIDTH, ButtonIcons.TEXTURE_HEIGHT);
            
            // Draw a "+" indicator
            graphics.drawString(Minecraft.getInstance().font, "+", this.getX() + (this.width) / 2 + 4, this.getY() + 6, 0xFFFFFFFF, false);
            
            // Draw count of additional buttons
            int additionalCount = this.binding.getButtons().size() - 1;
            graphics.drawString(Minecraft.getInstance().font, String.valueOf(additionalCount), this.getX() + (this.width) / 2 + 10, this.getY() + 6, 0xFFFFFFFF, false);
        }
        else
        {
            // Single button binding - original behavior
            int texU = this.binding.getButton() * 13;
            int texV = Config.CLIENT.options.controllerIcons.get().ordinal() * 13;
            int size = 13;
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            graphics.blit(ButtonIcons.TEXTURE, this.getX() + (this.width - size) / 2 + 1, this.getY() + 3, texU, texV, size, size, ButtonIcons.TEXTURE_WIDTH, ButtonIcons.TEXTURE_HEIGHT);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button)
    {
        if(this.active && this.visible && this.clicked(mouseX, mouseY))
        {
            if(this.onPress.onPress(button))
            {
                this.playDownSound(Minecraft.getInstance().getSoundManager());
            }
            return true;
        }
        return false;
    }

    public interface ButtonOnPress
    {
        boolean onPress(int button);
    }
}
