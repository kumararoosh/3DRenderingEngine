package core.modules.terrain;

import core.utils.Util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TerrainConfig {
    private float scaleY;
    private float scaleXZ;

    private int[] lod_range = new int[8];
    private int[] lod_morphing_area = new int[8];

    public void loadFile(String file) {
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(" ");
                tokens = Util.removeEmptyStrings(tokens);

                if(tokens.length == 0)
                    continue;

                if (tokens[0].equals("scaleY")) {
                    setScaleY(Float.parseFloat(tokens[1]));
                } else if (tokens[0].equals("scaleXZ")) {
                    setScaleXZ(Float.parseFloat(tokens[1]));
                } else if (tokens[0].equals("#lod_ranges")) {
                    for (int i = 0; i < 8; i++) {
                        line = reader.readLine();
                        tokens = line.split(" ");
                        tokens = Util.removeEmptyStrings(tokens);
                        if (tokens[0].equals("lod" + (i + 1) + "_range")) {
                            if (Integer.parseInt(tokens[1]) == 0) {
                                lod_range[i] = 0;
                                lod_morphing_area[i] = 0;
                            } else {
                                setLodRange(i, Integer.parseInt(tokens[1]));
                            }
                        }
                    }
                }
            }
            reader.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private int updateMorphingArea(int lod){
        return (int) ((scaleXZ / TerrainQuadtree.getRootNodes()) / (Math.pow(2,lod)));
    }

    private void setLodRange(int index, int lod_range){
        this.lod_range[index] = lod_range;
        lod_morphing_area[index] = lod_range - updateMorphingArea(index + 1);
    }

    public float getScaleY() {
        return scaleY;
    }

    public void setScaleY(float scaleY) {
        this.scaleY = scaleY;
    }

    public float getScaleXZ() {
        return scaleXZ;
    }

    public void setScaleXZ(float scaleXZ) {
        this.scaleXZ = scaleXZ;
    }

    public int[] getLod_range() {
        return lod_range;
    }

    public void setLod_range(int[] lod_range) {
        this.lod_range = lod_range;
    }

    public int[] getLod_morphing_area() {
        return lod_morphing_area;
    }

    public void setLod_morphing_area(int[] lod_morphing_area) {
        this.lod_morphing_area = lod_morphing_area;
    }
}
