package me.shakiba.preferd;

import java.util.List;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import me.shakiba.preferd.Options.Opt;


public class NodeKeyValue {
    private String node;

    private String key;

    private String value;

    private Preferences root;

    public NodeKeyValue(Options opts) {
        this(opts, true);
    }

    public NodeKeyValue(Options opts, boolean nodeRequired) {
        List<Opt> list = opts.getList();
        int i;
        for (i = 0; i < list.size(); i++) {
            if (readNode(list.get(i))) {
                if (list.size() > ++i && readKey(list.get(i))) {
                    if (list.size() > ++i && readValue(list.get(i))) {
                    }
                }
                return;
            }
        }
        if (!isNode() && !nodeRequired) {
            for (i = 0; i < list.size(); i++) {
                if (readKey(list.get(i))) {
                    if (list.size() > ++i && readValue(list.get(i))) {
                    }
                    return;
                }
            }
        }
    }

    public boolean readNode(Opt opt) {
        if (opt.is("s")) {
            root = Preferences.systemRoot();
            node = opt.getValue();
            opt.used();
            return true;

        } else if (opt.is("u")) {
            root = Preferences.userRoot();
            node = opt.getValue();
            opt.used();
            return true;

        } else {
            return false;
        }
    }

    public boolean readKey(Opt opt) {
        if (opt.is("k")) {
            key = opt.getValue();
            opt.used();
            return true;
        }
        return false;
    }

    public boolean readValue(Opt opt) {
        if (opt.is("v")) {
            value = opt.getValue();
            opt.used();
            return true;
        }
        return false;
    }

    public boolean isNode() {
        return node != null;
    }

    public boolean isKey() {
        return key != null;
    }

    public boolean isValue() {
        return value != null;
    }

    public String getNode() {
        return node;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    /**
     * @return System or User root
     */
    public Preferences getRoot() {
        return root;
    }

    public Preferences node() {
        return getRoot().node(getNode());
    }

    @Override
    public String toString() {
        return String.format("(%s %s %s)", node, key, value);
    }

    public boolean nodeExists() throws BackingStoreException {
        return getRoot().nodeExists(getNode());
    }

    public Preferences extractNode() {
        if ("/".equals(getNode())) {
            return root;
        }
        return getRoot().node(getNode());
    }
}