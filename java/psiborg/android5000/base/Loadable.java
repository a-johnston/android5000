package psiborg.android5000.base;

public abstract class Loadable {
    public String name = this.getClass().getSimpleName();
    public String tag = "";

    private boolean loaded;

    protected void load() {}
    protected void unload() {}

    public synchronized boolean isLoaded() {
        return loaded;
    }

    public synchronized final void loadAsset() {
        if (!loaded) {
            load();
            loaded = true;
        }
    }

    public synchronized final void unloadAsset() {
        if (loaded) {
            unload();
            loaded = false;
        }
    }
}
