package psiborg.android5000.base;

public abstract class Loadable {
    public String name, tag;
    private boolean loaded, loading;
    public boolean isLoaded() {
        return loaded;
    }
    public boolean isLoading() {
        return loading && !loaded;
    }
    public boolean isUnloaded() {
        return loaded && !loading;
    }
    protected abstract void loadAsset();
    protected abstract void unloadAsset();
    public final void load() {
        if (!loaded) {
            loading = true;
            loadAsset();
            loaded = true;
        }
    }
    public final void unload() {
        if (loaded) {
            loading = false;
            unloadAsset();
            loaded = false;
        }
    }
}
