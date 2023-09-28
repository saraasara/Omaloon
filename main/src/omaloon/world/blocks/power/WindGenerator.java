package omaloon.world.blocks.power;

import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import arc.util.io.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.input.*;
import mindustry.world.*;
import mindustry.world.blocks.power.*;
import mindustry.world.meta.*;

import static mindustry.Vars.*;

public class WindGenerator extends PowerGenerator{
    public int spacing = 3;
    public float boostWeather = 0.25f;
    public float rotateSpeed = 1.0f;

    public WindGenerator(String name){
        super(name);
        flags = EnumSet.of();
        envEnabled = Env.any;
    }

    @Override
    public void setStats(){
        super.setStats();

        stats.remove(generationType);
        stats.add(generationType, powerProduction * 60.0f, StatUnit.powerSecond);
    }

    @Override
    public void drawOverlay(float x, float y, int rotation){
        if(spacing < 1) return;
        Drawf.dashSquare(Pal.remove, x, y, (spacing + size / 2f + 3f) * tilesize);
    }

    @Override
    public boolean canPlaceOn(Tile tile, Team team, int rotation){
        if(spacing < 1) return true;
        int off = 1 - size % 2;
        for(int x = tile.x - spacing + off; x <= tile.x + spacing; x++){
            for(int y = tile.y - spacing + off; y <= tile.y + spacing; y++){
                Tile t = world.tile(x, y);
                if(t != null && t.block() instanceof WindGenerator s && (s == this || s.intersectsSpacing(t.build.tile, tile))) return false;
            }
        }
        return true;
    }

    public boolean intersectsSpacing(int sx, int sy, int ox, int oy, int ext){ //TODO untested with larger than 1x1
        if(spacing < 1) return true;
        int off = 1 - size % 2;
        return ox >= sx - spacing + off - ext && ox <= sx + spacing + ext &&
                oy >= sy - spacing + off - ext && oy <= sy + spacing + ext;
    }

    public boolean intersectsSpacing(Tile self, Tile other){
        return intersectsSpacing(self.x, self.y, other.x, other.y, 0);
    }

    @Override
    public void changePlacementPath(Seq<Point2> points, int rotation){
        if(spacing >= 1) Placement.calculateNodes(points, this, rotation, (point, other) -> intersectsSpacing(point.x, point.y, other.x, other.y, 1));
    }

    public class WindGeneratorBuild extends GeneratorBuild{
        public float boost = 0.0f;
        @Override
        public void updateTile(){
            if(enabled){
                boost = Mathf.lerpDelta(boost, !Groups.weather.isEmpty() ? 1.1f : 0.0f, 0.05f);
                productionEfficiency = 1 + (boostWeather * boost);
            }
        }

        public float baseRotation(){
            float time = Time.time / 4.0f;
            float offset = this.id() * 10.0f;
            return offset + time + Mathf.lerp(0, 360, rotateSpeed);
        }

        @Override
        public void write(Writes write){
            super.write(write);
            write.f(boost);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);
            boost = read.f();
        }
    }
}