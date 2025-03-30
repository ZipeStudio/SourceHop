package me.zipestudio.sourcehop.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.gui.controllers.string.number.*;
import me.zipestudio.sourcehop.SHServer;
import net.minecraft.text.Text;

public class ModMenuIntegration implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> config().generateScreen(parent);
    }

    public static YetAnotherConfigLib config() {
        return YetAnotherConfigLib.create(SourceHopConfig.GSON, (def, config, builder) -> builder
                .title(Text.literal(SHServer.MOD_NAME))

                .category(ConfigCategory.createBuilder()

                        .name(Text.translatable("sourcehop.text.category.general"))

                        .group(OptionGroup.createBuilder()
                                .option(
                                        Option.<Boolean>createBuilder()
                                                .name(Text.translatable("sourcehop.text.option.general.enableStrafing"))
                                                .description(OptionDescription.of(Text.translatable("sourcehop.text.option.general.enableStrafing.desc")))
                                                .binding(def.isEnableStrafing(), config::isEnableStrafing, config::setEnableStrafing)
                                                .controller(opt -> BooleanControllerBuilder.create(opt).onOffFormatter())
                                                .build()
                                )
                                .option(
                                        Option.<Boolean>createBuilder()
                                                .name(Text.translatable("sourcehop.text.option.general.enableManualJump"))
                                                .description(OptionDescription.of(Text.translatable("sourcehop.text.option.general.enableManualJump.desc")))
                                                .binding(def.isEnableManualJump(), config::isEnableManualJump, config::setEnableManualJump)
                                                .controller(opt -> BooleanControllerBuilder.create(opt).onOffFormatter())
                                                .build()
                                )
                                .option(
                                        Option.<Boolean>createBuilder()
                                                .name(Text.translatable("sourcehop.text.option.general.enableCrouchJump"))
                                                .description(OptionDescription.of(Text.translatable("sourcehop.text.option.general.enableCrouchJump.desc")))
                                                .binding(def.isEnableCrouchJump(), config::isEnableCrouchJump, config::setEnableCrouchJump)
                                                .controller(opt -> BooleanControllerBuilder.create(opt).onOffFormatter())
                                                .build()
                                )
                                .build()
                        )

                        .group(OptionGroup.createBuilder()
                                .option(
                                        Option.<Boolean>createBuilder()
                                                .name(Text.translatable("sourcehop.text.option.general.enableSpeedometer"))
                                                .description(OptionDescription.of(Text.translatable("sourcehop.text.option.general.enableSpeedometer.desc")))
                                                .binding(def.isEnableSpeedometer(), config::isEnableSpeedometer, config::setEnableSpeedometer)
                                                .controller(opt -> BooleanControllerBuilder.create(opt).onOffFormatter())
                                                .build()
                                )
                                .option(
                                        Option.<Boolean>createBuilder()
                                                .name(Text.translatable("sourcehop.text.option.general.enableJesusMode"))
                                                .description(OptionDescription.of(Text.translatable("sourcehop.text.option.general.enableJesusMode.desc")))
                                                .binding(def.isEnableJesusMode(), config::isEnableJesusMode, config::setEnableJesusMode)
                                                .controller(opt -> BooleanControllerBuilder.create(opt).onOffFormatter())
                                                .build()
                                )
                                .option(
                                        Option.<Boolean>createBuilder()
                                                .name(Text.translatable("sourcehop.text.option.general.enableNoFallDamage"))
                                                .description(OptionDescription.of(Text.translatable("sourcehop.text.option.general.enableNoFallDamage.desc")))
                                                .binding(def.isEnableNoFallDamage(), config::isEnableNoFallDamage, config::setEnableNoFallDamage)
                                                .controller(opt -> BooleanControllerBuilder.create(opt).onOffFormatter())
                                                .build()
                                )
                                .build()
                        )
                        .build()
                )

                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable("sourcehop.text.category.properties"))
                        .option(
                                Option.<Float>createBuilder()
                                        .name(Text.translatable("sourcehop.text.option.properties.friction"))
                                        .description(OptionDescription.of(Text.translatable("sourcehop.text.option.properties.friction.desc")))
                                        .binding(def.getFriction(), config::getFriction, config::setFriction)
                                        .customController(FloatFieldController::new)
                                        .build()
                        )
                        .option(
                                Option.<Float>createBuilder()
                                        .name(Text.translatable("sourcehop.text.option.properties.accelerate"))
                                        .description(OptionDescription.of(Text.translatable("sourcehop.text.option.properties.accelerate.desc")))
                                        .binding(def.getAccelerate(), config::getAccelerate, config::setAccelerate)
                                        .customController(FloatFieldController::new)
                                        .build()
                        )
                        .option(
                                Option.<Float>createBuilder()
                                        .name(Text.translatable("sourcehop.text.option.properties.airaccelerate"))
                                        .description(OptionDescription.of(Text.translatable("sourcehop.text.option.properties.airaccelerate.desc")))
                                        .binding(def.getAirAccelerate(), config::getAirAccelerate, config::setAirAccelerate)
                                        .customController(FloatFieldController::new)
                                        .build()
                        )
                        .option(
                                Option.<Float>createBuilder()
                                        .name(Text.translatable("sourcehop.text.option.properties.maxairspeed"))
                                        .description(OptionDescription.of(Text.translatable("sourcehop.text.option.properties.maxairspeed.desc")))
                                        .binding(def.getMaxAirSpeed(), config::getMaxAirSpeed, config::setMaxAirSpeed)
                                        .customController(FloatFieldController::new)
                                        .build()
                        )
                        .option(
                                Option.<Float>createBuilder()
                                        .name(Text.translatable("sourcehop.text.option.properties.maxSpeedMul"))
                                        .description(OptionDescription.of(Text.translatable("sourcehop.text.option.properties.maxSpeedMul.desc")))
                                        .binding(def.getMaxSpeedMul(), config::getMaxSpeedMul, config::setMaxSpeedMul)
                                        .customController(FloatFieldController::new)
                                        .build()
                        )
                        .option(
                                Option.<Float>createBuilder()
                                        .name(Text.translatable("sourcehop.text.option.properties.crouchjumppower"))
                                        .description(OptionDescription.of(Text.translatable("sourcehop.text.option.properties.crouchjumppower.desc")))
                                        .binding(def.getCrouchJumpPower(), config::getCrouchJumpPower, config::setCrouchJumpPower)
                                        .customController(FloatFieldController::new)
                                        .build()
                        )
                        .option(
                                Option.<Float>createBuilder()
                                        .name(Text.translatable("sourcehop.text.option.properties.gravity"))
                                        .description(OptionDescription.of(Text.translatable("sourcehop.text.option.properties.gravity.desc")))
                                        .binding(def.getGravity(), config::getGravity, config::setGravity)
                                        .customController(FloatFieldController::new)
                                        .build()
                        )
                        .build()
                )
        );
    }
}