package me.shakiba.preferd.cmd;

import java.util.prefs.Preferences;

import me.shakiba.preferd.ExecutionError;
import me.shakiba.preferd.NodeKeyValue;
import me.shakiba.preferd.Options;
import me.shakiba.preferd.UsageException;



public class Export extends List {

    @Override
    public void execute(Options opts) throws Exception {
	NodeKeyValue path = new NodeKeyValue(opts);
	if (!path.isNode()) {
	    throw new UsageException();
	}
	if (!path.nodeExists()) {
	    throw new ExecutionError("Node not found: " + path.getNode());
	}
	Preferences node = path.extractNode();
	if (opts.is("prop")) {

	    printNode(node, (opts.is("r") ? Mode.recursive : Mode.none));
	} else {
	    node.exportSubtree(System.out);
	}
    }

    protected void onNode(Preferences node) throws Exception {
	System.out.println("# " + formatNode(node));
    }

    @Override
    protected String formatKey(Preferences node, String key, String value) {
	return key + "=" + value;
    }
}