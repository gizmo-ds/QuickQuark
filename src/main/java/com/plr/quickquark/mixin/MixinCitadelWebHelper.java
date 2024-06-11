package com.plr.quickquark.mixin;

import com.plr.quickquark.QuickQuark;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Mixin(com.github.alexthe666.citadel.web.WebHelper.class)
public class MixinCitadelWebHelper {
    @Inject(method = "getURLContents", at = @At("HEAD"), cancellable = true, remap = false)
    private static void inject$getURLContents(@Nullable String urlString, @Nullable String backupFileLoc, CallbackInfoReturnable<BufferedReader> cir) {
        if (!QuickQuark.disableFetching.get()) return;
        if (backupFileLoc != null) {
            try {
                cir.setReturnValue(new BufferedReader(new InputStreamReader(Objects.requireNonNull(MixinCitadelWebHelper.class.getClassLoader().getResourceAsStream(backupFileLoc)), StandardCharsets.UTF_8)));
            } catch (Exception e) {
                cir.setReturnValue(null);
            }
        } else cir.setReturnValue(null);
    }
}
