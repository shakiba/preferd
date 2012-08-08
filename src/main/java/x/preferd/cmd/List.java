package x.preferd.cmd;

import java.util.prefs.Preferences;

import x.preferd.NodeKeyValue;
import x.preferd.Options;


public class List extends AbstractCommand {

    @Override
    public void execute(Options opts) throws Exception {
	Mode mode = Mode.recursive_sum;
	if (opts.is("r")) {
	    mode = Mode.recursive;
	}
	NodeKeyValue path = new NodeKeyValue(opts);
	if (!path.isNode()) {
	    if (!opts.is("s")) {
		printNode(Preferences.userRoot(), mode);
	    }
	    if (!opts.is("u")) {
		printNode(Preferences.systemRoot(), mode);
	    }
	    return;
	}
	if (!path.getRoot().nodeExists(path.getNode())) {
	    return;
	}
	Preferences node = path.getRoot().node(path.getNode());
	if (path.isKey()) {
	    printKey(node, path.getKey());
	} else {
	    printNode(node, mode);
	}
    }

    protected final void printNode(Preferences node, Mode mode)
	    throws Exception {
	if (node == null) {
	    return;
	}
	String[] keys = {};
	if (node.parent() != null) {
	    keys = node.keys();
	}
	String[] children = node.childrenNames();
	if (children.length == 0 && keys.length == 0) {
	    onNode(node);
	    // System.out.println(formatNode(node, true));
	    return;
	}

	if (keys.length > 0) {
	    onNode(node);
	    if (mode == Mode.sum) {
		// System.out.println(formatNode(node, true));
	    } else {
		for (String key : keys) {
		    printKey(node, key);
		}
		System.out.println();
	    }
	}

	if (mode == Mode.recursive_sum) {
	    mode = Mode.sum;
	} else if (mode == Mode.none) {
	    return;
	}
	// child nodes
	for (String child : children) {
	    printNode(node.node(child), mode);
	}
    }

    protected final void printKey(Preferences node, String key)
	    throws Exception {
	String value = node.get(key, null);
	if (value == null) {
	    return;
	}
	System.out.println(formatKey(node, key, value));
    }

    protected void onNode(Preferences node) throws Exception {
	System.out.println("# " + formatNode(node));
    }

    protected String formatNode(Preferences node) throws Exception {
	boolean u = node.isUserNode();
	String us = (u ? "u" : "s");
	String usrsys = (u ? "usr" : "sys");
	String name = (u ? this.user : this.space);

	return String.format("%s %s %-2d -%s %s", usrsys, name,
		node.keys().length, us, node.absolutePath());
    }

    protected String formatKey(Preferences node, String key, String value)
	    throws Exception {
	// key = (!key.contains(" ") ? key : "\"" + key + "\"");
	// key = (key.length() != 0 ? key : "\"\"");
	// value = (!value.contains(" ") ? value : "\"" + value + "\"");
	// value = (value.length() != 0 ? value : "\"\"");
	// return prefix(node.isUserNode()) + node.absolutePath() + " " + key
	// + " " + value;
	return key + " = " + value;
    }

    enum Mode {

	/**
	 * Print this node and childrens key-values.
	 */
	recursive,

	/**
	 * Print this node key-values but children sum.
	 */
	recursive_sum,

	/**
	 * print this node and and childrens sum.
	 */
	sum,

	/**
	 * Print only this node keys-values.
	 */
	none
    }

}