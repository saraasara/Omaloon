package omaloon.content;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.util.*;
import mindustry.entities.*;
import mindustry.graphics.*;
import omaloon.entities.bullet.*;
import omaloon.graphics.*;

import static arc.graphics.g2d.Draw.*;

public class OlFx {
    public static final Rand rand = new Rand();
    public static final Vec2 vec = new Vec2();
    public static Effect

    bigExplosionStone = new Effect(80f, e -> {
        Angles.randLenVectors(e.id, 22, e.fin() * 50f, (x, y) -> {
            float elevation = Interp.bounceIn.apply(e.fout() - 0.3f) * (Mathf.randomSeed((int) Angles.angle(x, y), 30f, 60f));

            Draw.z(Layer.power + 0.1f);
            Draw.color(Pal.shadow);
            Fill.circle(e.x + x, e.y + y, 12f);

            Draw.z(Layer.power + 0.2f);
            Draw.color(e.color);
            Fill.circle(e.x + x, e.y + y + elevation, 12f);
        });

    }),

    carborundumCraft = new Effect(60f, e -> {
        rand.setSeed(e.id);
        Draw.color(Color.valueOf("7545D5").mul(1.5f));
        Angles.randLenVectors(e.id, 10, 8 * e.finpow(), (x, y) -> {
            vec.trns(Mathf.angle(x, y), 8f).add(x + e.x, y + e.y);
            float rad = (3 + rand.range(2));
            Drawf.light(vec.x, vec.y, (rad + 8f) * e.fout(), Color.valueOf("7545D5"), 0.3f);
            Fill.circle(vec.x, vec.y, rad * e.fout());
        });

        if (e.time <= 5)Effect.shake(0.5f, 5f, e.x, e.y);
    }),

    explosionStone = new Effect(60f, e -> {
        Angles.randLenVectors(e.id, 12, e.fin() * 50f, (x, y) -> {
            float elevation = Interp.bounceIn.apply(e.fout() - 0.3f) * (Mathf.randomSeed((int) Angles.angle(x, y), 30f, 60f));

            Draw.z(Layer.power + 0.1f);
            Draw.color(Pal.shadow);
            Fill.circle(e.x + x, e.y + y, 12f);

            Draw.z(Layer.power + 0.2f);
            Draw.color(e.color);
            Fill.circle(e.x + x, e.y + y + elevation, 12f);
        });

    }),

    fellStone = new Effect(120f, e -> {
        if(!(e.data instanceof HailStoneBulletType.HailStoneData data)) return;

        Tmp.v2.trns(Mathf.randomSeed(e.id) * 360, data.fallTime/2 + Mathf.randomSeed(e.id + 1) * data.fallTime);
        float scl = Interp.bounceIn.apply(e.fout() - 0.3f);
        float rot = Tmp.v2.angle();
        float x = e.x + (Tmp.v2.x * e.finpow()), y = e.y + (Tmp.v2.y * e.finpow());

        Draw.z(Layer.power + 0.1f);
        Drawm.shadow(data.region, x, y, rot, Math.min(e.fout(), Pal.shadow.a));

        Draw.z(Layer.power + 0.2f);
        Draw.color(e.color);
        Draw.alpha(e.fout());
        Draw.rect(data.region, x, y + (scl * data.fallTime/2), rot);
    }),

    hammerHit = new Effect(80f, e -> {
        color(e.color, Color.gray, e.fin());
        alpha(0.6f);
        Draw.z(Layer.block);

        rand.setSeed(e.id);
        for(int i = 0; i < 3; i++) {
            float len = rand.random(6f), rot = rand.range(40f) + e.rotation;

            e.scaled(e.lifetime * rand.random(0.3f, 1f), e2 -> {
                vec.trns(rot, len * e2.finpow());

                Fill.square(e2.x + vec.x, e2.y + vec.y, 1.5f * e2.fslope() + 0.2f, 45);
            });
        }
    }),

    staticStone = new Effect(250f, e -> {
        if(!(e.data instanceof HailStoneBulletType.HailStoneData data)) return;

        Draw.z(Layer.power + 0.1f);
        Draw.color(e.color);
        Draw.alpha(e.fout());
        Draw.rect(data.region, e.x, e.y, Mathf.randomSeed(e.id) * 360);
    });
}
