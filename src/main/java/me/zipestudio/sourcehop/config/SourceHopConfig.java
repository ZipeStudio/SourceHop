package me.zipestudio.sourcehop.config;

import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import dev.isxander.yacl3.platform.YACLPlatform;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SourceHopConfig {

    public static final ConfigClassHandler<SourceHopConfig> GSON = ConfigClassHandler.createBuilder(SourceHopConfig.class)
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(YACLPlatform.getConfigDir().resolve("sourcehop.json"))
                    .build())
            .build();

    @SerialEntry
    private boolean enableStrafing = true; // Enable strafing, hence also bunnyhop.

    @SerialEntry
    private boolean enableManualJump = false; // Cancels autojumping while holding down the spacebar.

    @SerialEntry
    private boolean enableCrouchJump = false; // Gives you a little extra jump height when crouching.



    @SerialEntry
    private boolean enableSpeedometer = false;

    @SerialEntry
    private boolean enableJesusMode = false;

    @SerialEntry
    private boolean enableNoFallDamage = false;



    @SerialEntry
    private float friction = 0.5F; //Ground friction.
    @SerialEntry
    private float accelerate = 0.1F; //Ground acceleration.
    @SerialEntry
    private float airAccelerate = 0.2F; //Air acceleration.
    @SerialEntry
    private float maxAirSpeed = 0.08F; //Maximum speed you can move in air without influence. Also determines how fast you gain bhop speed.
    @SerialEntry
    private float maxSpeedMul = 2.2F; //How much to multiply default game's movementSpeed by.
    @SerialEntry
    private float crouchJumpPower = 1.1F; //How much to multiply default game's jump power by.

    @SerialEntry
    private float gravity = 0.08F;
}