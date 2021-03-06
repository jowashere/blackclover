package com.github.jowashere.blackclover.particles.light;

import com.github.jowashere.blackclover.particles.StartupCommonParticle;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.Locale;

public class LightParticleData implements IParticleData
{
    public LightParticleData(Color tint, double diameter) {
        this.tint = tint;
        this.diameter = constrainDiameterToValidRange(diameter);
    }

    public Color getTint() {
        return tint;
    }

    /**
     * @return get diameter of particle in metres
     */
    public double getDiameter() {
        return diameter;
    }

    @Nonnull
    @Override
    public ParticleType<LightParticleData> getType() {
        return StartupCommonParticle.lightParticleType;
    }

    // write the particle information to a PacketBuffer, ready for transmission to a client
    @Override
    public void writeToNetwork(PacketBuffer buf) {
        buf.writeInt(tint.getRed());
        buf.writeInt(tint.getGreen());
        buf.writeInt(tint.getBlue());
        buf.writeDouble(diameter);
    }

    // used for debugging I think; prints the data in human-readable format
    @Nonnull
    @Override
    public String writeToString() {
        return String.format(Locale.ROOT, "%s %.2f %i %i %i",
                this.getType().getRegistryName(), diameter, tint.getRed(), tint.getGreen(), tint.getBlue());
    }

    private static double constrainDiameterToValidRange(double diameter) {
        final double MIN_DIAMETER = 0.05;
        final double MAX_DIAMETER = 1.0;
        return MathHelper.clamp(diameter, MIN_DIAMETER, MAX_DIAMETER);
    }

    private Color tint;
    private double diameter;

    // --------- these remaining methods are used to serialize the Particle Data.
    //  I'm not yet sure what the Codec is used for, given that the DESERIALIZER already deserialises using read.
    //  Perhaps it will be used to replace the manual read methods in the future.

    //  The CODEC is a convenience to make it much easier to serialise and deserialise your objects.
    //  Using the builder below, you construct a serialiser and deserialiser in one go, using lambda functions.
    //  eg for the FlameParticleData CODEC:
    //  a) In order to serialise it, it reads the 'tint' member variable (type: INT) and the 'diameter' member variable (type: DOUBLE)
    //  b) In order to deserialise it, call the matching constructor FlameParticleData(INT, DOUBLE)

    public static final Codec<LightParticleData> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.INT.fieldOf("tint").forGetter(d -> d.tint.getRGB()),
                    Codec.DOUBLE.fieldOf("diameter").forGetter(d -> d.diameter)
            ).apply(instance, LightParticleData::new)
    );

    private LightParticleData(int tintRGB, double diameter) {
        this.tint = new Color(tintRGB);
        this.diameter = constrainDiameterToValidRange(diameter);
    }

    // The DESERIALIZER is used to construct FlameParticleData from either command line parameters or from a network packet

    public static final IDeserializer<LightParticleData> DESERIALIZER = new IDeserializer<LightParticleData>() {

        // parse the parameters for this particle from a /particle command
        @Nonnull
        @Override
        public LightParticleData fromCommand(ParticleType<LightParticleData> type, com.mojang.brigadier.StringReader reader) throws CommandSyntaxException {
            reader.expect(' ');
            double diameter = constrainDiameterToValidRange(reader.readDouble());

            final int MIN_COLOUR = 0;
            final int MAX_COLOUR = 255;
            reader.expect(' ');
            int red = MathHelper.clamp(reader.readInt(), MIN_COLOUR, MAX_COLOUR);
            reader.expect(' ');
            int green = MathHelper.clamp(reader.readInt(), MIN_COLOUR, MAX_COLOUR);
            reader.expect(' ');
            int blue = MathHelper.clamp(reader.readInt(), MIN_COLOUR, MAX_COLOUR);
            Color color = new Color(red, green, blue);

            return new LightParticleData(color, diameter);
        }

        // read the particle information from a PacketBuffer after the client has received it from the server
        @Override
        public LightParticleData fromNetwork(@Nonnull ParticleType<LightParticleData> type, PacketBuffer buf) {
            // warning! never trust the data read in from a packet buffer.

            final int MIN_COLOUR = 0;
            final int MAX_COLOUR = 255;
            int red = MathHelper.clamp(buf.readInt(), MIN_COLOUR, MAX_COLOUR);
            int green = MathHelper.clamp(buf.readInt(), MIN_COLOUR, MAX_COLOUR);
            int blue = MathHelper.clamp(buf.readInt(), MIN_COLOUR, MAX_COLOUR);
            Color color = new Color(red, green, blue);

            double diameter = constrainDiameterToValidRange(buf.readDouble());

            return new LightParticleData(color, diameter);
        }
    };
}
