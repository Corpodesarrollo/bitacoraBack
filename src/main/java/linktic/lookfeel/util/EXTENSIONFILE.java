package linktic.lookfeel.util;

public enum EXTENSIONFILE {
    JPG("jpg", ".jpg"),
    JPGM("jpg", ".JPG"),
    PNG("png", ".png"),
    PNGM("png", ".PNG"),
    BPM("bpm", ".bpm"),
    BPMM("bpm", ".BPM"),
    GIF("gif", ".gif");
    private final String key;
    private final String value;
    EXTENSIONFILE(String key, String value) {
        this.key = key;
        this.value = value;
    }
    public String getKey() {
        return key;
    }
    public String getValue() {
        return value;
    }
}