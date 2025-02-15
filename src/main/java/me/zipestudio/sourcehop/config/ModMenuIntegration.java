package me.zipestudio.sourcehop.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.gui.controllers.string.number.*;
import net.minecraft.text.Text;

public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> shConfig().generateScreen(parent);
    }

    public static YetAnotherConfigLib shConfig() {
        return YetAnotherConfigLib.create(SHConfig.GSON, (def, config, builder) -> builder
                .title(Text.translatable("text.autoconfig.sourcehop.title"))
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable("text.autoconfig.sourcehop.category.general"))
                        .option(
                                Option.<Boolean>createBuilder()
                                        .name(Text.translatable("text.autoconfig.sourcehop.option.general.enableStrafing"))
                                        .description(OptionDescription.of(Text.translatable("text.autoconfig.sourcehop.option.general.enableStrafing.desc")))
                                        .binding(def.isEnableStrafing(), config::isEnableStrafing, config::setEnableStrafing)
                                        .controller(opt -> BooleanControllerBuilder.create(opt).onOffFormatter())
                                        .build()
                        )
                        .option(
                                Option.<Boolean>createBuilder()
                                        .name(Text.translatable("text.autoconfig.sourcehop.option.general.manualJump"))
                                        .description(OptionDescription.of(Text.translatable("text.autoconfig.sourcehop.option.general.manualJump.desc")))
                                        .binding(def.isManualJump(), config::isManualJump, config::setManualJump)
                                        .controller(opt -> BooleanControllerBuilder.create(opt).onOffFormatter())
                                        .build()
                        )
                        .option(
                                Option.<Boolean>createBuilder()
                                        .name(Text.translatable("text.autoconfig.sourcehop.option.general.crouchJump"))
                                        .description(OptionDescription.of(Text.translatable("text.autoconfig.sourcehop.option.general.crouchJump.desc")))
                                        .binding(def.isCrouchJump(), config::isCrouchJump, config::setCrouchJump)
                                        .controller(opt -> BooleanControllerBuilder.create(opt).onOffFormatter())
                                        .build()
                        )
                        .option(
                                Option.<Boolean>createBuilder()
                                        .name(Text.translatable("text.autoconfig.sourcehop.option.general.jesusMode"))
                                        .description(OptionDescription.of(Text.translatable("text.autoconfig.sourcehop.option.general.jesusMode.desc")))
                                        .binding(def.isJesusMode(), config::isJesusMode, config::setJesusMode)
                                        .controller(opt -> BooleanControllerBuilder.create(opt).onOffFormatter())
                                        .build()
                        )
                        .build())
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable("text.autoconfig.sourcehop.category.properties"))
                        .option(
                                Option.<Float>createBuilder()
                                        .name(Text.translatable("text.autoconfig.sourcehop.option.properties.friction"))
                                        .description(OptionDescription.of(Text.translatable("text.autoconfig.sourcehop.option.properties.friction.desc")))
                                        .binding(def.getFriction(), config::getFriction, config::setFriction)
                                        .customController(FloatFieldController::new)
                                        .build()
                        )
                        .option(
                                Option.<Float>createBuilder()
                                        .name(Text.translatable("text.autoconfig.sourcehop.option.properties.accelerate"))
                                        .description(OptionDescription.of(Text.translatable("text.autoconfig.sourcehop.option.properties.accelerate.desc")))
                                        .binding(def.getAccelerate(), config::getAccelerate, config::setAccelerate)
                                        .customController(FloatFieldController::new)
                                        .build()
                        )
                        .option(
                                Option.<Float>createBuilder()
                                        .name(Text.translatable("text.autoconfig.sourcehop.option.properties.airaccelerate"))
                                        .description(OptionDescription.of(Text.translatable("text.autoconfig.sourcehop.option.properties.airaccelerate.desc")))
                                        .binding(def.getAirAccelerate(), config::getAirAccelerate, config::setAirAccelerate)
                                        .customController(FloatFieldController::new)
                                        .build()
                        )
                        .option(
                                Option.<Float>createBuilder()
                                        .name(Text.translatable("text.autoconfig.sourcehop.option.properties.maxairspeed"))
                                        .description(OptionDescription.of(Text.translatable("text.autoconfig.sourcehop.option.properties.maxairspeed.desc")))
                                        .binding(def.getMaxAirSpeed(), config::getMaxAirSpeed, config::setMaxAirSpeed)
                                        .customController(FloatFieldController::new)
                                        .build()
                        )
                        .option(
                                Option.<Float>createBuilder()
                                        .name(Text.translatable("text.autoconfig.sourcehop.option.properties.maxSpeedMul"))
                                        .description(OptionDescription.of(Text.translatable("text.autoconfig.sourcehop.option.properties.maxSpeedMul.desc")))
                                        .binding(def.getMaxSpeedMul(), config::getMaxSpeedMul, config::setMaxSpeedMul)
                                        .customController(FloatFieldController::new)
                                        .build()
                        )
                        .option(
                                Option.<Float>createBuilder()
                                        .name(Text.translatable("text.autoconfig.sourcehop.option.properties.crouchjumppower"))
                                        .description(OptionDescription.of(Text.translatable("text.autoconfig.sourcehop.option.properties.crouchjumppower.desc")))
                                        .binding(def.getCrouchJumpPower(), config::getCrouchJumpPower, config::setCrouchJumpPower)
                                        .customController(FloatFieldController::new)
                                        .build()
                        )
                        .option(
                                Option.<Double>createBuilder()
                                        .name(Text.translatable("text.autoconfig.sourcehop.option.properties.gravity"))
                                        .description(OptionDescription.of(Text.translatable("text.autoconfig.sourcehop.option.properties.gravity.desc")))
                                        .binding(def.getGravity(), config::getGravity, config::setGravity)
                                        .customController(DoubleFieldController::new)
                                        .build()
                        )
                        .build()
                )
        );
    }
}
