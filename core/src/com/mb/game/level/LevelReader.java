package com.mb.game.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

/**
 * Created by Markus on 03.02.2018.
 */
public class LevelReader {

    public static Level readLevel(String levelName) {

        XmlReader reader = new XmlReader();
        Element levelTag = reader.parse(Gdx.files.internal(levelName + ".xml"));
        return parseLevel(levelTag);
    }

    private static Level parseLevel(Element levelTag) {
        Level level = new Level();

        level.setLevelStructure(parseLevelStructure(levelTag.getChildByName("structure")));

        return level;
    }

    private static LevelStructure parseLevelStructure(Element structureTag) {
        LevelStructure levelStructure = new LevelStructure();

        String spriteSheetPath = structureTag.getAttribute("spriteSheet");
        levelStructure.setSpriteSheet(new Pixmap(Gdx.files.internal(spriteSheetPath)));

        int tileSize = readIntegerAttribute("tileSize", structureTag);
        levelStructure.setTileSize(tileSize);

        int width = readIntegerAttribute("width", structureTag);
        levelStructure.setWidth(width);

        int height = readIntegerAttribute("height", structureTag);
        levelStructure.setHeight(height);

        parseTiles(structureTag, levelStructure);

        return levelStructure;
    }

    private static void parseTiles(Element structureTag, LevelStructure levelStructure) {
        for(int childId = 0; childId < structureTag.getChildCount(); childId++) {
            parseTile(structureTag.getChild(childId), levelStructure);
        }
    }

    private static void parseTile(Element tileTag, LevelStructure levelStructure) {
        int x = readIntegerChild("x", tileTag);
        int y = readIntegerChild("y", tileTag);
        int spriteIndex = readIntegerChild("spriteIndex", tileTag);

        levelStructure.addTile(x, y, spriteIndex);
    }

    private static int readIntegerChild(String name, Element tag) {
        return Integer.valueOf(tag.getChildByName(name).getText());
    }

    private static int readIntegerAttribute(String name, Element tag) {
        return Integer.valueOf(tag.getAttribute(name));
    }
}
