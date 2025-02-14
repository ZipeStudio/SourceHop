package me.zipestudio.sourcehop.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.gui.controllers.string.number.DoubleFieldController;
import dev.isxander.yacl3.gui.controllers.string.number.FloatFieldController;
import net.minecraft.text.Text;

public class ModMenuIntegration implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> shConfig().generateScreen(parent);
    }

    public static YetAnotherConfigLib shConfig(){
        return YetAnotherConfigLib.create(SHConfig.GSON, (def, config, builder) -> builder
                .title(Text.translatable("text.autoconfig.sourcehop.title"))
                .category(
                        ConfigCategory.createBuilder()
                                .name(Text.translatable("text.autoconfig.sourcehop.category.general"))
                                .tooltip(Text.literal("General, man"))

                                .option(
                                        Option.<Boolean>createBuilder()
                                                .name(Text.translatable("text.autoconfig.sourcehop.option.general.enableStrafing"))
                                                .description(OptionDescription.of(Text.literal("Enable strafing, hence also bunnyhop")))
                                                .binding(
                                                        def.isEnableStrafing(),
                                                        config::isEnableStrafing,
                                                        config::setEnableStrafing
                                                )
                                                .controller(
                                                        opt -> BooleanControllerBuilder.create(opt).onOffFormatter()
                                                )
                                                .build()
                                )

                                .option(
                                        Option.<Boolean>createBuilder()
                                                .name(Text.translatable("text.autoconfig.sourcehop.option.general.manualJump"))
                                                .description(OptionDescription.of(Text.literal("Cancels autojumping while holding down the spacebar")))
                                                .binding(
                                                        def.isManualJump(),
                                                        config::isManualJump,
                                                        config::setManualJump)
                                                .controller(opt -> BooleanControllerBuilder.create(opt).onOffFormatter())
                                                .build()
                                )

                                .option(
                                        Option.<Boolean>createBuilder()
                                                .name(Text.translatable("text.autoconfig.sourcehop.option.general.crouchJump"))
                                                .description(OptionDescription.of(Text.literal("Gives you a little extra jump height when crouching")))
                                                .binding(
                                                        def.isCrouchJump(),
                                                        config::isCrouchJump,
                                                        config::setCrouchJump)
                                                .controller(opt -> BooleanControllerBuilder.create(opt).onOffFormatter())
                                                .build()
                                )
                                .option(
                                        Option.<Boolean>createBuilder()
                                                .name(Text.translatable("text.autoconfig.sourcehop.option.general.compatibilityMode"))
                                                .description(OptionDescription.of(Text.literal("Enables movement mechanics only for players")))
                                                .binding(
                                                        def.isCompatibilityMode(),
                                                        config::isCompatibilityMode,
                                                        config::setCompatibilityMode)
                                                .controller(opt -> BooleanControllerBuilder.create(opt).onOffFormatter())
                                                .build()
                                )
                                .option(
                                        Option.<Boolean>createBuilder()
                                                .name(Text.translatable("text.autoconfig.sourcehop.option.general.jesusMode"))
                                                .description(OptionDescription.of(Text.literal("Jesus Mode (walking on Water & lava)")))
                                                .binding(
                                                        def.isJesusMode(),
                                                        config::isJesusMode,
                                                        config::setJesusMode)
                                                .controller(opt -> BooleanControllerBuilder.create(opt).onOffFormatter())
                                                .build()
                                )
                                .build()
                )

                .category(
                        ConfigCategory.createBuilder()
                                .name(Text.translatable("text.autoconfig.sourcehop.category.properties"))
                                .tooltip(Text.literal("SH Config, man"))

                                .option(
                                        Option.<Float>createBuilder()
                                                .name(Text.translatable("text.autoconfig.sourcehop.option.properties.friction"))
                                                .description(OptionDescription.of(Text.literal("Ground friction")))
                                                .binding(
                                                        def.getFriction(),
                                                        config::getFriction,
                                                        config::setFriction)
                                                .customController(FloatFieldController::new)
                                                .build()
                                )

                                .option(
                                        Option.<Float>createBuilder()
                                                .name(Text.translatable("text.autoconfig.sourcehop.option.properties.accelerate"))
                                                .description(OptionDescription.of(Text.literal("Ground acceleration")))
                                                .binding(
                                                        def.getAccelerate(),
                                                        config::getAccelerate,
                                                        config::setAccelerate)
                                                .customController(FloatFieldController::new)
                                                .build()
                                )

                                .option(
                                        Option.<Float>createBuilder()
                                                .name(Text.translatable("text.autoconfig.sourcehop.option.properties.airaccelerate"))
                                                .description(OptionDescription.of(Text.literal("Air acceleration")))
                                                .binding(
                                                        def.getAirAccelerate(),
                                                        config::getAirAccelerate,
                                                        config::setAirAccelerate)
                                                .customController(FloatFieldController::new)
                                                .build()
                                )

                                .option(
                                        Option.<Float>createBuilder()
                                                .name(Text.translatable("text.autoconfig.sourcehop.option.properties.maxairspeed"))
                                                .description(OptionDescription.of(Text.literal("Maximum speed you can move in air without influence. Also determines how fast you gain bhop speed")))
                                                .binding(
                                                        def.getMaxAirSpeed(),
                                                        config::getMaxAirSpeed,
                                                        config::setMaxAirSpeed)
                                                .customController(FloatFieldController::new)
                                                .build()
                                )

                                .option(
                                        Option.<Float>createBuilder()
                                                .name(Text.translatable("text.autoconfig.sourcehop.option.properties.maxSpeedMul"))
                                                .description(OptionDescription.of(Text.literal("How much to multiply default game's movementSpeed by")))
                                                .binding(
                                                        def.getMaxSpeedMul(),
                                                        config::getMaxSpeedMul,
                                                        config::setMaxSpeedMul)
                                                .customController(FloatFieldController::new)
                                                .build()
                                )

                                .option(
                                        Option.<Float>createBuilder()
                                                .name(Text.translatable("text.autoconfig.sourcehop.option.properties.crouchjumppower"))
                                                .description(OptionDescription.of(Text.literal("How much to multiply default game's jump power by")))
                                                .binding(
                                                        def.getCrouchJumpPower(),
                                                        config::getCrouchJumpPower,
                                                        config::setCrouchJumpPower)
                                                .customController(FloatFieldController::new)
                                                .build()
                                )
                                .option(
                                        Option.<Double>createBuilder()
                                                .name(Text.translatable("text.autoconfig.sourcehop.option.properties.gravity"))
                                                .binding(
                                                        def.getGravity(),
                                                        config::getGravity,
                                                        config::setGravity)
                                                .customController(DoubleFieldController::new)
                                                .build()
                                )
                                .build()
                )
        );
    }
}
