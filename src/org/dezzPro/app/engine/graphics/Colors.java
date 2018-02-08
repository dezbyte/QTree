package org.dezzPro.app.engine.graphics;

import java.awt.*;

public enum Colors {

    SPRING(0x00ff7f),
    ORANGE(0xff7f50),
    BLUE(0x00bfff),
    CRIMSON(0xed143d),
    DEEP_PINK(0xff1493);

    protected int hex;

    Colors(int hex)
    {
        this.hex = hex;
    }

    public Color color()
    {
        return new Color(this.hex);
    }

}
